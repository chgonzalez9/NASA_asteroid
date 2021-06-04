package com.chgonzalez.nasaasteroid.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    @Json(name = "url")
    val url: String
) : Parcelable