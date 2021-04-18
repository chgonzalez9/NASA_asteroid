package com.chgonzalez.nasaasteroid.network

import com.squareup.moshi.Json

data class PictureOfDay(
    @Json(name = "media_type")
    val mediaType: String,
    val tittle: String,
    val url: String)