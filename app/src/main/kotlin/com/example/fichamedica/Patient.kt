package com.example.fichamedica

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Patient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val identifier: String,
    val name: String,
    val age: Int,
    val history: String
)
