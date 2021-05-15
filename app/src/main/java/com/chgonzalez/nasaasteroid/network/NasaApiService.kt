package com.chgonzalez.nasaasteroid.network

import android.annotation.SuppressLint
import com.chgonzalez.nasaasteroid.util.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
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

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<AsteroidProperty> {
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")

    val asteroidList = ArrayList<AsteroidProperty>()

    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
    for (formattedDate in nextSevenDaysFormattedDates) {
        val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)

        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                    .getJSONObject("kilometers").getDouble("estimated_diameter_max")

            val closeApproachData = asteroidJson
                    .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                    .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                    .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                    .getBoolean("is_potentially_hazardous_asteroid")

            val asteroid = AsteroidProperty(id, codename, formattedDate, absoluteMagnitude,
                    estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous)
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

@SuppressLint("WeekBasedYear")
private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}
