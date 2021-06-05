package com.chgonzalez.nasaasteroid.network

import com.chgonzalez.nasaasteroid.domain.PictureOfDay
import com.chgonzalez.nasaasteroid.util.Constants
import com.chgonzalez.nasaasteroid.util.NEXT_SEVEN_DAYS
import com.chgonzalez.nasaasteroid.util.TODAY_DATE
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

enum class DateFilters(val string: String) {
    ALL_WEEK(NEXT_SEVEN_DAYS), TODAY(TODAY_DATE), SAVED(
        NEXT_SEVEN_DAYS
    )
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val client: OkHttpClient = OkHttpClient().newBuilder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .client(client)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface AsteroidApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroid(
            @Query("start_date") start_date: String,
            @Query("api_key") apiKey: String
    ): String
}

interface PictureApiService {

    @GET("planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String
    ): PictureOfDay

}

object NasaApi {

    val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }

}

object PictureApi {

    val retrofitService: PictureApiService by lazy { retrofit.create(PictureApiService::class.java) }
}