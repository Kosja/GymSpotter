package com.example.gymspotter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
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

class ExercisesActivity : AppCompatActivity() {
    lateinit var filteredList: ArrayList<ExercisesResult>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)
        val searchExercise = findViewById<EditText>(R.id.searchExercise)
        val categoryID = intent.getStringExtra("ID")
        findViewById<RecyclerView>(R.id.recyclerView)

        thread() {
            val mp = ObjectMapper()
            val myObject = mp.readValue(
                URL("https://wger.de/api/v2/exercise/?language=2&category=$categoryID&limit=300"),
                Exercises::class.java
            )

            val list = myObject.exercisesResults as ArrayList<ExercisesResult>
            setupRecyclerView(list)
            // Search bar filter for exercises
            searchExercise.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    filteredList = ArrayList()
                    if (p0.toString() != null) {
                        for (item in list) {
                            if (item.name.toLowerCase().contains(p0.toString().toLowerCase())) {
                                filteredList.add(item)
                            }
                        }
                        setupRecyclerView(filteredList)
                    } else {
                        setupRecyclerView(list)
                    }
                }

            })
        }

    }
    // Add items to RecyclerView
    private fun setupRecyclerView(list: ArrayList<ExercisesResult>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val progressBar: ProgressBar = findViewById(R.id.loading)

        runOnUiThread {
            progressBar.visibility = View.GONE
            recyclerView.adapter = ExerciseAdapter(list)
            // Layoutmanager to set items in one column
            recyclerView.layoutManager = GridLayoutManager(this, 1)
        }

    }
}