package com.example.gymspotter

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.adapters.CategoriesAdapter
import com.example.gymspotter.models.Categories
import com.example.gymspotter.models.CategoriesResult
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.snackbar.Snackbar
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        // Layoutmanager to set items in two columns
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        thread() {
            val mp = ObjectMapper()
            // If there is no internet connection show snackbar with reconnect button
            if(checkConnection()) {
                val myObject = mp.readValue(
                    URL("https://wger.de/api/v2/exercisecategory/"),
                    Categories::class.java
                )

                val category = myObject.categoriesResults as ArrayList<CategoriesResult>
                runOnUiThread {
                    recyclerView.adapter = CategoriesAdapter(category)
                }
            } else {
                // Show snackbar if there is no internet connection
                val snackbar = Snackbar.make(findViewById(android.R.id.content),"No internet connection.", Snackbar.LENGTH_INDEFINITE)
                snackbar.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.design_default_color_error))
                snackbar.setAction("reconnect") {
                    // Reload view
                    finish()
                    startActivity(intent)
                }.show();
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    // Check if phone has internet connection
    fun checkConnection(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }

    fun favoritesButton(view: View) {
        var intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }
}