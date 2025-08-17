package com.example.fichamedica

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dao: PatientDao
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getDatabase(this)
        dao = db.patientDao()

        val identifier = findViewById<EditText>(R.id.editIdentifier)
        val name = findViewById<EditText>(R.id.editName)
        val age = findViewById<EditText>(R.id.editAge)
        val history = findViewById<EditText>(R.id.editHistory)
        val save = findViewById<Button>(R.id.buttonSave)
        val list = findViewById<ListView>(R.id.listPatients)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList())
        list.adapter = adapter

        lifecycleScope.launch {
            dao.getAll().collect { patients ->
                adapter.clear()
                adapter.addAll(patients.map { "${it.name} (${it.age})" })
            }
        }

        save.setOnClickListener {
            val patient = Patient(
                identifier = identifier.text.toString(),
                name = name.text.toString(),
                age = age.text.toString().toIntOrNull() ?: 0,
                history = history.text.toString()
            )
            lifecycleScope.launch {
                dao.insert(patient)
            }
            identifier.text.clear()
            name.text.clear()
            age.text.clear()
            history.text.clear()
        }
    }
}
