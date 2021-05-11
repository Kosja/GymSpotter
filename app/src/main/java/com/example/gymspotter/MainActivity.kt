package com.example.gymspotter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var workoutName: EditText
    lateinit var addWorkoutBtn: Button
    lateinit var workoutLayout: LinearLayout
    var workouts = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.addWorkoutBtn = findViewById(R.id.addWorkoutBtn)
        this.workoutLayout = findViewById(R.id.workoutLayout)
        this.workoutName = findViewById(R.id.workoutName)
        addView()
    }

    fun addView() {
        for (i in workouts.indices) {
            val workoutList = TextView(this)
            workoutList.textSize = 20f
            workoutList.text = workouts[i]
            workoutLayout.addView(workoutList)
        }

    }
    fun addWorkout(view: View) {
        val workoutList = TextView(this)
        workoutList.textSize = 20f
        val givenName = workoutName.text
        workouts.add(givenName.toString())
        workoutList.text = givenName
        workoutLayout.addView(workoutList)

    }

    fun showExercise(view: View) {
        val intent = Intent(this, ViewExercises::class.java)
        startActivity(intent)
    }
}
