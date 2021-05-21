package com.example.gymspotter

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gymspotter.models.ExerciseImageResult
import com.example.gymspotter.models.ExerciseImages
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import kotlin.concurrent.thread

class ExerciseActivity : AppCompatActivity() {
    private var userFavorites = arrayListOf<String>()
    lateinit var tinyDB : TinyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        tinyDB = TinyDB(applicationContext)
        val exerciseNameText = findViewById<TextView>(R.id.exerciseNameText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)
        val view = findViewById<ImageView>(R.id.exerciseImage)
        var favoriteButton: Button = findViewById(R.id.favoriteButton)

        val intent = intent
        val description = intent.getStringExtra("description")
        val exerciseName = intent.getStringExtra("exerciseName")
        val exerciseBase = intent.getStringExtra("exerciseBase")
        val removeTags = "<[^>]*>".toRegex()
        // Adds scrolling to textview
        descriptionText.movementMethod = ScrollingMovementMethod()
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

        var id = intent.getStringExtra("ID")

        userFavorites = tinyDB.getListString("userFavorites")
        // Changes button text depending if exercise is added to favorites
        favoriteButton.text = if(userFavorites.contains(id.toString())) "Remove" else "Favorite"

    }

    fun favoriteButton(view: View) {
        var id = intent.getStringExtra("ID")
        // Fetch array from shared preferences using helper class
        userFavorites = tinyDB.getListString("userFavorites")
        // Check if exercise is in userfavorites
        if(userFavorites.contains(id.toString())) {
            // Remove exercise from userFavorites
            userFavorites = userFavorites.filter { it != id.toString() } as ArrayList<String>
            Toast.makeText(this@ExerciseActivity, "Removing exercise from favorites", Toast.LENGTH_SHORT).show()
        } else {
            // Add exercise to userFavorites
            userFavorites.add(id.toString())
            Toast.makeText(this@ExerciseActivity, "Exercise added to favorites", Toast.LENGTH_LONG).show()
        }
        // Add edited array back to shared preferences
        tinyDB.putListString("userFavorites", userFavorites)
        // close activity and return to previous
        this.finish()
    }
}