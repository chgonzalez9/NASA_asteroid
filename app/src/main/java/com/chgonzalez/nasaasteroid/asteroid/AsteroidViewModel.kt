package com.chgonzalez.nasaasteroid.asteroid

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chgonzalez.nasaasteroid.network.*
import com.chgonzalez.nasaasteroid.util.Constants
import kotlinx.coroutines.launch
import org.json.JSONObject

class AsteroidViewModel : ViewModel() {

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay>
        get() = _imageOfDay

    private val _asteroid = MutableLiveData<List<AsteroidProperty>>()
    val asteroid: LiveData<List<AsteroidProperty>>
        get() = _asteroid

    private val _navigateToDetails = MutableLiveData<AsteroidProperty>()
    val navigateToDetails: LiveData<AsteroidProperty>
        get() = _navigateToDetails

    init {
        getAsteroidList()
        getImageOfDay()
    }


    private fun getAsteroidList() {
        viewModelScope.launch {
            try {
                val jsonResult = NasaApi.retrofitService.getAsteroid(
                    Constants.API_KEY
                )
                _asteroid.value = parseAsteroidsJsonResult(JSONObject(jsonResult))
            } catch (e: Exception) {
                e.printStackTrace()
                _asteroid.value = ArrayList()
            }
        }
    }

    private fun getImageOfDay() {
        viewModelScope.launch {
            try {
                _imageOfDay.value = PictureApi.retrofitService.getPictureOfDay(Constants.API_KEY)
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