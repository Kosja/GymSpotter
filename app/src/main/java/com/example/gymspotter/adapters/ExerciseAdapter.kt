package com.example.gymspotter.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.ExerciseActivity
import com.example.gymspotter.R
import com.example.gymspotter.ExercisesResult

class ExerciseAdapter(private val userList: ArrayList<ExercisesResult>) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    // Returns the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    // Get the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(exercise: ExercisesResult) {
            val exerciseName = itemView.findViewById(R.id.categoryName) as TextView
            exerciseName.text = exercise.name
            // On click opens new activity with given data
            itemView.setOnClickListener {
                    var intent = Intent(itemView.context, ExerciseActivity::class.java)
                    intent.putExtra("exerciseName", exercise.name)
                    intent.putExtra("description", exercise.description)
                    intent.putExtra("exerciseBase", exercise.exerciseBase.toString())
                    intent.putExtra("ID", exercise.id.toString())
                    itemView.context.startActivity(intent)

            }
        }
    }
}
