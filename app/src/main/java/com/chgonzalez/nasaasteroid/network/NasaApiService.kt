package com.chgonzalez.nasaasteroid.network

import com.chgonzalez.nasaasteroid.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

interface AsteroidApiService {

    @GET("neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-07&api_key=smDyYMhYDMXDBJydeWQGp5807Bdishf4oh9GAvd9")
    suspend fun getAsteroid() : List<AsteroidProperty>

    @GET("planetary/apod?api_key=smDyYMhYDMXDBJydeWQGp5807Bdishf4oh9GAvd9")
    suspend fun getPictureOfDay() : List<PictureOfDay>

}

object NasaApi {

    val retrofitService : AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }

}

