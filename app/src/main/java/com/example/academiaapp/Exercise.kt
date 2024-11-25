package com.example.academiaapp

data class Exercise(
    var id: Long = 0,
    var name: String,
    var sets: Int,
    var reps: Int
)