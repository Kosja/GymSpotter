package com.example.gymspotter.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gymspotter.ExercisesActivity
import com.example.gymspotter.R
import com.example.gymspotter.CategoriesResult


class CategoriesAdapter(private val categoryList: ArrayList<CategoriesResult>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    // Returns the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(categoryList[position])
    }

    // Get the size of the list
    override fun getItemCount(): Int {
        return categoryList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(category: CategoriesResult) {
            val categoryName = itemView.findViewById(R.id.categoryName) as TextView
            categoryName.text = category.name

            // On click opens new activity with given data
            itemView.setOnClickListener {
                category.id?.let { it ->
                    var intent = Intent(itemView.context, ExercisesActivity::class.java)
                    intent.putExtra("ID", it.toString())
                    itemView.context.startActivity(intent)

                }
            }
        }
    }

}