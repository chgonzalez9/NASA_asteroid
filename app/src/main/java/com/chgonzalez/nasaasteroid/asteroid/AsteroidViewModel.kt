package com.chgonzalez.nasaasteroid.asteroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chgonzalez.nasaasteroid.network.AsteroidProperty
import com.chgonzalez.nasaasteroid.network.NasaApi
import com.chgonzalez.nasaasteroid.network.PictureOfDay
import kotlinx.coroutines.launch

class AsteroidViewModel : ViewModel() {

    private val _imageOfDay = MutableLiveData<List<PictureOfDay>>()
    val imageOfDay: LiveData<List<PictureOfDay>>
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
                _asteroid.value = NasaApi.retrofitService.getAsteroid()
            } catch (e: Exception) {
                _asteroid.value = ArrayList()
            }
        }
    }

    private fun getImageOfDay() {
        viewModelScope.launch {
            try {
                _imageOfDay.value = NasaApi.retrofitService.getPictureOfDay()
            } catch (e: Exception) {
                _imageOfDay.value = ArrayList()
            }
        }
    }

}