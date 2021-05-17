package com.example.gymspotter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.adapters.CategoriesAdapter
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Layoutmanager to set items in two columns
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        thread() {
            val mp = ObjectMapper()
            val myObject = mp.readValue(getUrl("https://wger.de/api/v2/exercisecategory/"),  Categories::class.java)

            val category = myObject.results as ArrayList<Result>
            val adapter = CategoriesAdapter(category)

            runOnUiThread {
                recyclerView.adapter = adapter
            }
        }
    }

    private fun getUrl(url: String) : String? {
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