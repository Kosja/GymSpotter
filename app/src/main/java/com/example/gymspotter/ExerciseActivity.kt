package com.example.gymspotter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import kotlin.concurrent.thread

class ExerciseActivity : AppCompatActivity() {
    private var userFavorites = arrayListOf<String>()
    lateinit var favoriteButton: Button
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
        favoriteButton.text = "remove"
        exerciseNameText.text = exerciseName
        // Remove HTML tags before adding description to textview
        descriptionText.text = description?.replace(removeTags, "")

        var id = intent.getStringExtra("ID")

        userFavorites = tinyDB.getListString("userFavorites")
        favoriteButton.text = if(userFavorites.contains(id.toString())) "Remove" else "Favorite"

    }

    fun Favorite(view: View) {
        var id = intent.getStringExtra("ID")

        userFavorites = tinyDB.getListString("userFavorites")
        if(userFavorites.contains(id.toString())) {
            userFavorites = userFavorites.filter { it != id.toString() } as ArrayList<String>
            Toast.makeText(this@ExerciseActivity, "Removing exercise from favorites", Toast.LENGTH_SHORT).show()
        } else {
            userFavorites.add(id.toString())
            Toast.makeText(this@ExerciseActivity, "Exercise added to favorites", Toast.LENGTH_LONG).show()
        }
        tinyDB.putListString("userFavorites", userFavorites)
        this.finish()
    }
}