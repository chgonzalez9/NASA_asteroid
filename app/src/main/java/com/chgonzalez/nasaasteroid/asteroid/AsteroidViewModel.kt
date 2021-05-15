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

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response


    private val _imageOfDay = MutableLiveData<List<PictureOfDay>>()
    val imageOfDay: LiveData<List<PictureOfDay>>
        get() = _imageOfDay

    private val _asteroid = MutableLiveData<List<AsteroidProperty>>()
    val asteroid: LiveData<List<AsteroidProperty>>
        get() = _asteroid

    init {
        getAsteroidList()
    }


    private fun getAsteroidList() {
        viewModelScope.launch {
            try {
                var listResult = NasaApi.retrofitService.getPictureOfDay()
                _response.value = "Success: ${listResult.size} Mars properties retrieved"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

}