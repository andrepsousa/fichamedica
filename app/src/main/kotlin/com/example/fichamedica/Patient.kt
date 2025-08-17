package com.example.fichamedica

/**
 * Representa um paciente cadastrado no sistema.
 */
data class Patient(
    val id: Int? = null,
    val nome: String,
    val idade: Int,
    val historico: String?
)
