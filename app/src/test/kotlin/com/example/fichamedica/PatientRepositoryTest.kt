package com.example.fichamedica

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PatientRepositoryTest {
    @Test
    fun `add and list patients`() {
        val repo = PatientRepository(":memory:")
        val added = repo.add(Patient(nome = "João", idade = 30, historico = "Nenhum"))
        val all = repo.list()
        assertEquals(1, all.size)
        assertEquals(added.copy(id = all.first().id), all.first())
    }
}
