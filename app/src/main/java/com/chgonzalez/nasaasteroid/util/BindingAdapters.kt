package com.chgonzalez.nasaasteroid.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.network.AsteroidProperty
import com.chgonzalez.nasaasteroid.network.PictureOfDay
import com.squareup.picasso.Picasso

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<AsteroidProperty>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}

@BindingAdapter("pictureData")
fun bindPictureRecyclerView(recyclerView: RecyclerView, data: List<PictureOfDay>?) {
    val adapter = recyclerView.adapter as PictureAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImageOfDay(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get()
                .load(imgUri)
                .into(imageView)
    }

}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
