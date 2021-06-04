package com.chgonzalez.nasaasteroid.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chgonzalez.nasaasteroid.databinding.AsteroidListItemBinding
import com.chgonzalez.nasaasteroid.domain.AsteroidProperty

class AsteroidAdapter(val onClickListener: OnClickListener) :
    ListAdapter<AsteroidProperty, AsteroidAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }

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

    class DiffCallBack : DiffUtil.ItemCallback<AsteroidProperty>() {
        override fun areItemsTheSame(
            oldItem: AsteroidProperty,
            newItem: AsteroidProperty
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AsteroidProperty,
            newItem: AsteroidProperty
        ): Boolean {
            return oldItem == newItem
        }

    }

    class OnClickListener(val clickListener: (asteroidProperty: AsteroidProperty) -> Unit) {
        fun onClick(asteroidProperty: AsteroidProperty) = clickListener(asteroidProperty)
    }

}

