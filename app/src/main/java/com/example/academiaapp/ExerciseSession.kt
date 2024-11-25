package com.example.academiaapp

data class ExerciseSession(
    var id: Long = 0,
    var workoutId: Long,
    var exerciseId: Long,
    var date: String
)