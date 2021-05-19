package com.example.gymspotter

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import kotlin.concurrent.thread


class ExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val exerciseNameText = findViewById<TextView>(R.id.exerciseNameText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)
        val view = findViewById<ImageView>(R.id.exerciseImage)

        val intent = intent
        val description = intent.getStringExtra("description")
        val exerciseName = intent.getStringExtra("exerciseName")
        val exerciseBase = intent.getStringExtra("exerciseBase")
        val removeTags = "<[^>]*>".toRegex()

        thread {
            try {
                val mp = ObjectMapper()
                val myObject = mp.readValue(
                    URL("https://wger.de/api/v2/exerciseimage/?format=json&limit=120&exercise_base=$exerciseBase"),
                    ExerciseImages::class.java
                )
                val category = myObject?.exerciseImageResults as ArrayList<ExerciseImageResult>
                var image1Url = category[0].toString()

                runOnUiThread {
                    Glide.with(applicationContext).load(image1Url).into(view)
                }
            } catch (Exception: Exception) {
                Log.d("Exception", "No image")
            }
        }

        exerciseNameText.text = exerciseName
        // Remove HTML tags before adding description to textview
        descriptionText.text = description?.replace(removeTags, "")
    }
}