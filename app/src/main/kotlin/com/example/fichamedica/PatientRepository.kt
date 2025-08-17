package com.example.fichamedica

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

/**
 * Repositório responsável por persistir pacientes em um banco SQLite.
 */
class PatientRepository(dbPath: String = "patients.db") {
    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:$dbPath")

    init {
        connection.createStatement().use { stmt ->
            stmt.execute(
                """
                CREATE TABLE IF NOT EXISTS patient (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    idade INTEGER NOT NULL,
                    historico TEXT
                )
                """
            )
        }
    }

    fun add(patient: Patient): Patient {
        val sql = "INSERT INTO patient(nome, idade, historico) VALUES (?, ?, ?)"
        connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { ps ->
            ps.setString(1, patient.nome)
            ps.setInt(2, patient.idade)
            ps.setString(3, patient.historico)
            ps.executeUpdate()
            val generatedKeys = ps.generatedKeys
            val id = if (generatedKeys.next()) generatedKeys.getInt(1) else null
            return patient.copy(id = id)
        }
    }

    fun list(): List<Patient> {
        val patients = mutableListOf<Patient>()
        connection.createStatement().use { stmt ->
            val rs = stmt.executeQuery("SELECT id, nome, idade, historico FROM patient")
            while (rs.next()) {
                patients.add(
                    Patient(
                        id = rs.getInt("id"),
                        nome = rs.getString("nome"),
                        idade = rs.getInt("idade"),
                        historico = rs.getString("historico")
                    )
                )
            }
        }
        return patients
    }
}
