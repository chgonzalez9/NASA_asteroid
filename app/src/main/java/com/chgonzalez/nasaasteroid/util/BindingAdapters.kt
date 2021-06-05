package com.chgonzalez.nasaasteroid.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chgonzalez.nasaasteroid.R
import com.chgonzalez.nasaasteroid.asteroid.PictureStatus
import com.chgonzalez.nasaasteroid.asteroid.StatusLoading
import com.chgonzalez.nasaasteroid.domain.AsteroidProperty
import com.squareup.picasso.Picasso

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<AsteroidProperty>?) {
    val adapter = recyclerView.adapter as AsteroidAdapter
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

@BindingAdapter("pictureStatus")
fun bindStatus(textView: TextView, status: PictureStatus?) {
    when (status) {
        PictureStatus.DONE -> {
            textView.visibility = View.GONE
        }
        PictureStatus.ERROR -> {
            textView.visibility = View.VISIBLE
            textView.text = textView.context.getString(R.string.pictureError)
        }
    }
}

@BindingAdapter("statusLoading")
fun bindLoading(imageView: ImageView, status: StatusLoading?) {
    when (status) {
        StatusLoading.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
        }
        StatusLoading.DONE -> {
            imageView.visibility = View.GONE
        }
        StatusLoading.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}

@BindingAdapter("pictureTitle")
fun bindPictureTitle(textView: TextView, status: PictureStatus?) {
    when (status) {
        PictureStatus.DONE -> {
            textView.visibility = View.VISIBLE
        }
        PictureStatus.ERROR -> {
            textView.visibility = View.GONE
        }
    }
}
