package com.example.fichamedica

/**
 * Aplicação simples de linha de comando para cadastro de pacientes.
 */
fun main() {
    val repo = PatientRepository()
    println("=== Sistema de Ficha Médica ===")
    while (true) {
        println("1 - Cadastrar paciente")
        println("2 - Listar pacientes")
        println("0 - Sair")
        print("> ")
        when (readln().trim()) {
            "1" -> cadastrar(repo)
            "2" -> listar(repo)
            "0" -> return
        }
    }
}

private fun cadastrar(repo: PatientRepository) {
    print("Nome: ")
    val nome = readln()
    print("Idade: ")
    val idade = readln().toIntOrNull() ?: 0
    print("Histórico: ")
    val historico = readln().ifBlank { null }
    val paciente = repo.add(Patient(nome = nome, idade = idade, historico = historico))
    println("Paciente cadastrado com id ${paciente.id}")
}

private fun listar(repo: PatientRepository) {
    val pacientes = repo.list()
    if (pacientes.isEmpty()) {
        println("Nenhum paciente cadastrado.")
    } else {
        pacientes.forEach { p ->
            println("${p.id}: ${p.nome} (${p.idade} anos) - ${p.historico ?: "sem histórico"}")
        }
    }
}
