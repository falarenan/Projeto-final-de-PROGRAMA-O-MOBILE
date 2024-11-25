package com.example.academiaapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.CalendarView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //botao add novo exercicio
        val btnAddWorkout: Button = findViewById(R.id.btnAddWorkout)
        btnAddWorkout.setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            startActivity(intent)
        }

        //botao visualizar exercicios
        val btnViewWorkouts: Button = findViewById(R.id.btnViewWorkouts)
        btnViewWorkouts.setOnClickListener {
            val intent = Intent(this, ExerciseListActivity::class.java)
            startActivity(intent)
        }
    }
}