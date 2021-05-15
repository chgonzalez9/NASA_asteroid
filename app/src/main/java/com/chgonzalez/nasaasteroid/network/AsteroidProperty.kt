package com.chgonzalez.nasaasteroid.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AsteroidProperty(
        val id: Long,
        val name: String,
        val approachDate: String,
        val magnitude: Double,
        val diameter: Double,
        val velocity: Double,
        val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean) : Parcelable