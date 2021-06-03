package com.chgonzalez.nasaasteroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.chgonzalez.nasaasteroid.database.AsteroidsDatabase
import com.chgonzalez.nasaasteroid.database.asDomainModel
import com.chgonzalez.nasaasteroid.network.AsteroidProperty
import com.chgonzalez.nasaasteroid.network.NasaApi
import com.chgonzalez.nasaasteroid.network.asDatabaseModel
import com.chgonzalez.nasaasteroid.network.parseAsteroidsJsonResult
import com.chgonzalez.nasaasteroid.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidsDatabase) {

    val asteroids: LiveData<List<AsteroidProperty>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidResult = parseAsteroidsJsonResult(
                    JSONObject(
                        NasaApi.retrofitService.getAsteroid(Constants.API_KEY)
                    )
                )
                database.asteroidDao.insertAll(*asteroidResult.asDatabaseModel())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}