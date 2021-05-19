package com.example.gymspotter

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.adapters.ExerciseAdapter
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import kotlin.concurrent.thread

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
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
                ExercisesJSON::class.java
            )
            var list = myObject.exercisesResults
            var tinyDB: TinyDB = TinyDB(applicationContext)
            var favoritesList = tinyDB.getListString("userFavorites")
            list = list.filter { it.id.toString() in favoritesList } as ArrayList<ExercisesResult>
            setupRecyclerView(list)

        }
    }
    // Add items to RecyclerView
    private fun setupRecyclerView(list: ArrayList<ExercisesResult>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = ExerciseAdapter(list)
        val progressBar: ProgressBar = findViewById(R.id.loading)

        runOnUiThread {
            progressBar.visibility = View.GONE
            recyclerView.adapter = adapter
        }

    }
}