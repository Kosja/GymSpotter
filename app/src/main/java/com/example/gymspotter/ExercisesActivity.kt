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
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

class ExercisesActivity : AppCompatActivity() {

    lateinit var filteredList: ArrayList<ResultX>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)
        val searchExercise = findViewById<EditText>(R.id.searchExercise)
        val categoryID = intent.getStringExtra("ID")
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Layoutmanager to set items in two columns
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        thread() {
            val mp = ObjectMapper()
            val myObject = mp.readValue(
                getUrl("https://wger.de/api/v2/exercise/?language=2&category=$categoryID&limit=300"),
                ExercisesJSON::class.java
            )
            val list = myObject.results as ArrayList<ResultX>

            setupRecyclerView(list)

            searchExercise.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    filteredList = ArrayList<ResultX>()
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

    private fun setupRecyclerView(list: ArrayList<ResultX>) {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val adapter = ExerciseAdapter(list)
        val progressBar: ProgressBar = findViewById(R.id.loading)

        runOnUiThread {
            progressBar.visibility = View.GONE
            recyclerView.adapter = adapter
        }

    }


    private fun getUrl(url: String): String? {
        var result = ""
        val myUrl = URL(url)
        val conn = myUrl.openConnection() as HttpsURLConnection
        var inputstream = conn.inputStream
        inputstream.use {
            var line = it.read()
            while (line != -1) {
                result += line.toChar()
                line = it.read()
            }
        }
        return result
    }
}