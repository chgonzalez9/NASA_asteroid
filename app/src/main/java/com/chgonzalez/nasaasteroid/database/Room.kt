package com.chgonzalez.nasaasteroid.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Query("select * from databaseasteroids WHERE closeApproachDate >= :date ORDER BY closeApproachDate ASC")
    fun getAsteroids(date: String): LiveData<List<DatabaseAsteroids>>

    @Query("select * from databaseasteroids WHERE closeApproachDate = :date ORDER BY closeApproachDate ASC")
    fun getTodayAsteroids(date: String): LiveData<List<DatabaseAsteroids>>

    @Query("select * from databaseasteroids WHERE closeApproachDate BETWEEN :week AND :date ORDER BY closeApproachDate ASC")
    fun getWeekAsteroids(date: String, week: String): LiveData<List<DatabaseAsteroids>>

    @Query("select * from databaseasteroids ORDER BY closeApproachDate ASC")
    fun getSavedAsteroids(): LiveData<List<DatabaseAsteroids>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroids)
}

@Database(entities = [DatabaseAsteroids::class], version = 1)
abstract class AsteroidsDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidsDatabase

fun getDatabase(context: Context): AsteroidsDatabase {
    synchronized(AsteroidsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidsDatabase::class.java,
                    "asteroids"
            ).build()
        }
    }
    return INSTANCE
}