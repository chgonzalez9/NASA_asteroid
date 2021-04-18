package com.chgonzalez.nasaasteroid.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.network.AsteroidProperty

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {

    var data = listOf<AsteroidProperty>()
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val asteroidName: TextView = itemView.findViewById(R.id.asteroidName)
        val asteroidDate: TextView = itemView.findViewById(R.id.asteroidDate)
        val asteroidFace: ImageView = itemView.findViewById(R.id.asteroidFace)

        fun bind(item: AsteroidProperty) {
            asteroidName.text = item.name
            asteroidDate.text = item.approachDate
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.asteroid_list_item, parent, false)

                return ViewHolder(view)
            }
        }

    }
}