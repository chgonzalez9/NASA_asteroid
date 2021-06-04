package com.chgonzalez.nasaasteroid.asteroid

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chgonzalez.nasaasteroid.database.getDatabase
import com.chgonzalez.nasaasteroid.domain.AsteroidProperty
import com.chgonzalez.nasaasteroid.domain.PictureOfDay
import com.chgonzalez.nasaasteroid.network.PictureApi
import com.chgonzalez.nasaasteroid.repository.AsteroidRepository
import com.chgonzalez.nasaasteroid.util.Constants
import kotlinx.coroutines.launch

enum class PictureStatus { DONE, ERROR }

class AsteroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay>
        get() = _imageOfDay

    private val _pictureStatus = MutableLiveData<PictureStatus>()
    val pictureStatus: LiveData<PictureStatus>
        get() = _pictureStatus

    private val _navigateToDetails = MutableLiveData<AsteroidProperty>()
    val navigateToDetails: LiveData<AsteroidProperty>
        get() = _navigateToDetails

    private val asteroidRepository = AsteroidRepository(getDatabase(application))

    init {
        getAsteroidList()
        getImageOfDay()
    }

    val asteroid = asteroidRepository.asteroids


    private fun getAsteroidList() {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroids()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getImageOfDay() {
        viewModelScope.launch {
            try {
                val picture = PictureApi.retrofitService.getPictureOfDay(Constants.API_KEY)
                if (picture.mediaType.contains("image")) {
                    _imageOfDay.value = picture
                    _pictureStatus.value = PictureStatus.DONE
                } else {
                    _pictureStatus.value = PictureStatus.ERROR
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun displayDetails(asteroidProperty: AsteroidProperty) {
        _navigateToDetails.value = asteroidProperty
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun displayDetailsComplete() {
        _navigateToDetails.value = null
    }

}