package com.example.academiaapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ExerciseListActivity : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        val btnBackToMain: Button = findViewById(R.id.btnBackToMain)
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val listView: ListView = findViewById(R.id.listView)

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM exercises", null)

        // associando itens ao id
        val exerciseList = mutableListOf<Pair<Long, String>>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndex("id")) // Obtenha o ID do exercício
            val name = cursor.getString(cursor.getColumnIndex("name"))
            exerciseList.add(Pair(id, name))
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseList.map { it.second })
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedExercise = exerciseList[position]
            val intent = Intent(this, ExerciseDetailActivity::class.java)
            intent.putExtra("exerciseId", selectedExercise.first)
            startActivity(intent)
        }
    }
}