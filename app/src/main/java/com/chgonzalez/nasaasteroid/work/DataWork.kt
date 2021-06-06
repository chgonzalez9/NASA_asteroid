package com.chgonzalez.nasaasteroid.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.chgonzalez.nasaasteroid.database.getDatabase
import com.chgonzalez.nasaasteroid.repository.AsteroidRepository
import retrofit2.HttpException

class DataWork(appContext: Context, params: WorkerParameters) :
        CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "DataWorker"
    }

    override suspend fun doWork(): Result {

        val repository = AsteroidRepository(getDatabase(applicationContext))

        return try {
            repository.refreshAsteroids()
            repository.deleteAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}