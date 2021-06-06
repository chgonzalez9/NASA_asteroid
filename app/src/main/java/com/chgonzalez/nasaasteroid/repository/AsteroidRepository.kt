package com.chgonzalez.nasaasteroid.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.chgonzalez.nasaasteroid.database.AsteroidsDatabase
import com.chgonzalez.nasaasteroid.database.asDomainModel
import com.chgonzalez.nasaasteroid.domain.AsteroidProperty
import com.chgonzalez.nasaasteroid.network.DateFilters
import com.chgonzalez.nasaasteroid.network.NasaApi
import com.chgonzalez.nasaasteroid.network.asDatabaseModel
import com.chgonzalez.nasaasteroid.util.Constants
import com.chgonzalez.nasaasteroid.util.NEXT_SEVEN_DAYS
import com.chgonzalez.nasaasteroid.util.TODAY_DATE
import com.chgonzalez.nasaasteroid.util.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidsDatabase) {

    fun getAsteroidSelection(filters: DateFilters): LiveData<List<AsteroidProperty>> {
        return when (filters) {
            (DateFilters.TODAY) -> Transformations.map(database.asteroidDao.getTodayAsteroids(TODAY_DATE)) {
                it.asDomainModel()
            }
            (DateFilters.ALL_WEEK) -> Transformations.map(database.asteroidDao.getWeekAsteroids(TODAY_DATE, NEXT_SEVEN_DAYS)) {
                it.asDomainModel()
            }
            else -> Transformations.map(database.asteroidDao.getSavedAsteroids()) {
                it.asDomainModel()
            }
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidResult = parseAsteroidsJsonResult(
                    JSONObject(
                        NasaApi.retrofitService.getAsteroid(TODAY_DATE, Constants.API_KEY)
                    )
                )
                database.asteroidDao.insertAll(*asteroidResult.asDatabaseModel())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun deleteAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                database.asteroidDao.deleteAsteroids(TODAY_DATE)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}