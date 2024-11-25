package com.example.academiaapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExerciseDetailActivity : AppCompatActivity() {

    private var estaRodando = false
    private var tempoDecorrido: Long = 0
    private lateinit var manipulador: Handler
    private lateinit var tvCronometro: TextView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)

        // layout
        val tvExerciseName: TextView = findViewById(R.id.tvExerciseName)
        val tvSets: TextView = findViewById(R.id.tvSets)
        val tvReps: TextView = findViewById(R.id.tvReps)

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // volta para tela anterior no final
        }

        tvCronometro = findViewById(R.id.tvTimer)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        val btnPausar: Button = findViewById(R.id.btnPausar)
        val btnReiniciar: Button = findViewById(R.id.btnReiniciar)

        manipulador = Handler(Looper.getMainLooper())

        btnIniciar.setOnClickListener {
            if (!estaRodando) {
                estaRodando = true
                iniciarCronometro()
            }
        }

        btnPausar.setOnClickListener {
            estaRodando = false
        }

        btnReiniciar.setOnClickListener {
            estaRodando = false
            tempoDecorrido = 0
            atualizarDisplayCronometro(0)
        }

        // pegando id do exerc
        val exerciseId = intent.getLongExtra("exerciseId", -1)
        if (exerciseId == -1L) {
            tvExerciseName.text = "Erro: ID inválido."
            return
        }

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM exercises WHERE id = ?", arrayOf(exerciseId.toString()))
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val sets = cursor.getInt(cursor.getColumnIndex("sets"))
            val reps = cursor.getInt(cursor.getColumnIndex("reps"))

            // colocando dados na textview
            tvExerciseName.text = name
            tvSets.text = "Séries: $sets"
            tvReps.text = "Repetições: $reps"
        }

        cursor.close()
    }
    private fun iniciarCronometro() {
        val tempoInicial = System.currentTimeMillis() - tempoDecorrido
        manipulador.post(object : Runnable {
            override fun run() {
                if (estaRodando) {
                    tempoDecorrido = System.currentTimeMillis() - tempoInicial
                    atualizarDisplayCronometro(tempoDecorrido)
                    manipulador.postDelayed(this, 1000)
                }
            }
        })
    }

    private fun atualizarDisplayCronometro(tempo: Long) {
        val segundos = (tempo / 1000) % 60
        val minutos = (tempo / 1000) / 60 % 60
        val horas = (tempo / 1000) / 3600
        tvCronometro.text = String.format("%02d:%02d:%02d", horas, minutos, segundos)
    }
}