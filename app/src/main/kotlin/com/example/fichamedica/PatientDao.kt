package com.example.fichamedica

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert
    suspend fun insert(patient: Patient)

    @Query("SELECT * FROM Patient")
    fun getAll(): Flow<List<Patient>>
}
