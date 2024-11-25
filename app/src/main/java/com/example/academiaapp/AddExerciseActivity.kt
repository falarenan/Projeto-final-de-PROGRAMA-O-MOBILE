package com.example.academiaapp

import android.content.ContentValues
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button

class AddExerciseActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtSets: EditText
    private lateinit var edtReps: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)

        edtName = findViewById(R.id.edtName)
        edtSets = findViewById(R.id.edtSets)
        edtReps = findViewById(R.id.edtReps)
        btnSave = findViewById(R.id.btnSave)
        val btnBackToMain: Button = findViewById(R.id.btnBackToMain)

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val sets = edtSets.text.toString().toInt()
            val reps = edtReps.text.toString().toInt()

            val exercise = Exercise(name = name, sets = sets, reps = reps)

            val dbHelper = DatabaseHelper(this)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put("name", exercise.name)
                put("sets", exercise.sets)
                put("reps", exercise.reps)
            }
            db.insert("exercises", null, values)

            finish()
        }

        // Botão de voltar
        btnBackToMain.setOnClickListener {
            finish()
        }
    }
}