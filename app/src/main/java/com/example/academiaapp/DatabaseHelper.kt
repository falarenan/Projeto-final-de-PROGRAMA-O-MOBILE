package com.example.academiaapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "academy.db"
        const val DATABASE_VERSION = 1

        const val TABLE_WORKOUT = "workouts"
        const val TABLE_EXERCISE = "exercises"
        const val TABLE_EXERCISE_SESSION = "exercise_sessions"

        const val COLUMN_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createWorkoutTable = """
            CREATE TABLE $TABLE_WORKOUT (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                description TEXT
            )
        """
        db.execSQL(createWorkoutTable)

        val createExerciseTable = """
            CREATE TABLE $TABLE_EXERCISE (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                sets INTEGER,
                reps INTEGER
            )
        """
        db.execSQL(createExerciseTable)

        val createSessionTable = """
            CREATE TABLE $TABLE_EXERCISE_SESSION (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                workoutId INTEGER,
                exerciseId INTEGER,
                date TEXT,
                FOREIGN KEY(workoutId) REFERENCES $TABLE_WORKOUT($COLUMN_ID),
                FOREIGN KEY(exerciseId) REFERENCES $TABLE_EXERCISE($COLUMN_ID)
            )
        """
        db.execSQL(createSessionTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKOUT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXERCISE_SESSION")
        onCreate(db)
    }
}