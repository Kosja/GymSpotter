package com.example.gymspotter

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.adapters.ExerciseAdapter
import com.example.gymspotter.models.Exercises
import com.example.gymspotter.models.ExercisesResult
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import kotlin.concurrent.thread

class FavoritesActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var tinyDB: TinyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        tinyDB = TinyDB(applicationContext)
        recyclerView = findViewById(R.id.recyclerView)
        // Layoutmanager to set items in one column
        recyclerView.layoutManager = GridLayoutManager(this, 1)
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        thread() {
            val mp = ObjectMapper()
            val myObject = mp.readValue(
                URL("https://wger.de/api/v2/exercise/?language=2&limit=300"),
                Exercises::class.java
            )
            var list = myObject.exercisesResults
            var favoritesList = tinyDB.getListString("userFavorites")
            list = list.filter { it.id.toString() in favoritesList } as ArrayList<ExercisesResult>
            setupRecyclerView(list)

        }
    }
    // Add items to RecyclerView
    private fun setupRecyclerView(list: ArrayList<ExercisesResult>) {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.loading)
        val adapter = ExerciseAdapter(list)

        runOnUiThread {
            progressBar.visibility = View.GONE
            recyclerView.adapter = adapter
        }

    }
}