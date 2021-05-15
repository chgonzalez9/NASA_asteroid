package com.chgonzalez.nasaasteroid.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.databinding.AsteroidListItemBinding
import com.chgonzalez.nasaasteroid.network.AsteroidProperty

class AsteroidAdapter : ListAdapter<AsteroidProperty, AsteroidAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: AsteroidListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AsteroidProperty) {
            binding.asteroidListItems = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = AsteroidListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(view)
            }
        }

    }

}

class DiffCallBack : DiffUtil.ItemCallback<AsteroidProperty>() {
    override fun areItemsTheSame(oldItem: AsteroidProperty, newItem: AsteroidProperty): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AsteroidProperty, newItem: AsteroidProperty): Boolean {
        return oldItem == newItem
    }

}
