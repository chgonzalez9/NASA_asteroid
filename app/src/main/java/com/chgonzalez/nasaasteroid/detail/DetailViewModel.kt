package com.chgonzalez.nasaasteroid.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chgonzalez.nasaasteroid.network.AsteroidProperty

class DetailViewModel(asteroidProperty: AsteroidProperty): ViewModel() {

    private val _asteroidDetail = MutableLiveData<AsteroidProperty>()
    val asteroidDetail: LiveData<AsteroidProperty>
        get() = _asteroidDetail

    init {
        _asteroidDetail.value = asteroidProperty
    }
}