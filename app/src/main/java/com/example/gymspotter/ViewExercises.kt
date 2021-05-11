package com.example.gymspotter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread



class ViewExercises : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_view_exercises)

        }

        override fun onResume() {
            super.onResume()

            thread() {
                val mp = ObjectMapper()
                mp.enable(SerializationFeature.INDENT_OUTPUT);
                mp.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

                val myObject: MusclesJsonObject = mp.readValue(getUrl("https://wger.de/api/v2/exercisecategory/"), MusclesJsonObject::class.java)
                val muscles: MutableList<Muscles>? = myObject.results
                val itemsAdapter: ArrayAdapter<Muscles> = ArrayAdapter(this, R.layout.item, R.id.myTextView, ArrayList<Muscles>())
                val listView: ListView = findViewById<View>(R.id.listView) as ListView

                runOnUiThread {
                    listView.adapter = itemsAdapter;

                    if (muscles != null) {
                        itemsAdapter.addAll(muscles)
                    }

                    listView.setOnItemClickListener{parent, view, position, id ->

                        Log.d("TEST", parent.getItemAtPosition(position).toString())
                        if(parent.getItemAtPosition(position).toString() == "Abs")
                        if(parent.getItemAtPosition(position).toString() == "Arms") Log.d("TEST", "Opens new activity and shows exercises for selected muscle")
                        if(parent.getItemAtPosition(position).toString() == "Back") Log.d("TEST", "Opens new activity and shows exercises for selected muscle")
                        if(parent.getItemAtPosition(position).toString() == "Calves") Log.d("TEST", "Opens new activity and shows exercises for selected muscle")
                        if(parent.getItemAtPosition(position).toString() == "Chest") Log.d("TEST", "Opens new activity and shows exercises for selected muscle")
                        if(parent.getItemAtPosition(position).toString() == "Legs") Log.d("TEST", "Opens new activity and shows exercises for selected muscle")
                        if(parent.getItemAtPosition(position).toString() == "Shoulders") Log.d("TEST", "Opens new activity and shows exercises for selected muscle")
                        // Toast.makeText(this, "You have Clicked " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }


        private fun getUrl(url: String) : String? {
            var result = ""
            val myUrl = URL(url)
            val conn = myUrl.openConnection() as HttpURLConnection
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