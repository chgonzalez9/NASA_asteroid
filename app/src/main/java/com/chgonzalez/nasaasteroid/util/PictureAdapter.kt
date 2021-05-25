package com.chgonzalez.nasaasteroid.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chgonzalez.nasaasteroid.databinding.PictureOfDayBinding
import com.chgonzalez.nasaasteroid.network.PictureOfDay

class PictureAdapter : ListAdapter<PictureOfDay, PictureAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: PictureOfDayBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PictureOfDay) {
            binding.pictureOfDayList = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = PictureOfDayBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(view)
            }
        }

    }

    class DiffCallBack : DiffUtil.ItemCallback<PictureOfDay>() {
        override fun areItemsTheSame(oldItem: PictureOfDay, newItem: PictureOfDay): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: PictureOfDay, newItem: PictureOfDay): Boolean {
            return oldItem == newItem
        }

    }
}

