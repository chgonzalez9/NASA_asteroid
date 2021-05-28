package com.chgonzalez.nasaasteroid.asteroid

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

    init {
        getAsteroidList()
        getImageOfDay()
    }


    private fun getAsteroidList() {
        viewModelScope.launch {
            try {
                val jsonResult = NasaApi.retrofitService.getAsteroid(
                    Constants.API_KEY,
                    "2020-01-08",
                    "2020-01-09"
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

}