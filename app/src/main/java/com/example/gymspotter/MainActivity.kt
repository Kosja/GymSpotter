package com.example.gymspotter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.adapters.CategoriesAdapter
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URL
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
            val myObject = mp.readValue(URL("https://wger.de/api/v2/exercisecategory/"),  Categories::class.java)

            val category = myObject.categoriesResults as ArrayList<CategoriesResult>
            val adapter = CategoriesAdapter(category)

            runOnUiThread {
                recyclerView.adapter = adapter
            }
        }
    }
}