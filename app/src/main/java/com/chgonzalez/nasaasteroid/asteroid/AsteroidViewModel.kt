package com.chgonzalez.nasaasteroid.asteroid

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.chgonzalez.nasaasteroid.database.getDatabase
import com.chgonzalez.nasaasteroid.domain.AsteroidProperty
import com.chgonzalez.nasaasteroid.domain.PictureOfDay
import com.chgonzalez.nasaasteroid.network.DateFilters
import com.chgonzalez.nasaasteroid.network.PictureApi
import com.chgonzalez.nasaasteroid.repository.AsteroidRepository
import com.chgonzalez.nasaasteroid.util.Constants
import kotlinx.coroutines.launch

enum class PictureStatus { DONE, ERROR }
enum class StatusLoading { LOADING, DONE, ERROR }

class AsteroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay>
        get() = _imageOfDay

    private val _pictureStatus = MutableLiveData<PictureStatus>()
    val pictureStatus: LiveData<PictureStatus>
        get() = _pictureStatus

    private val _pictureLoading = MutableLiveData<StatusLoading>()
    val pictureLoading: LiveData<StatusLoading>
        get() = _pictureLoading

    private val _asteroidLoading = MutableLiveData<StatusLoading>()
    val asteroidLoading: LiveData<StatusLoading>
        get() = _asteroidLoading

    private val _navigateToDetails = MutableLiveData<AsteroidProperty>()
    val navigateToDetails: LiveData<AsteroidProperty>
        get() = _navigateToDetails

    private val _asteroidList = MutableLiveData<List<AsteroidProperty>>()
    val asteroidList: LiveData<List<AsteroidProperty>>
        get() = _asteroidList

    private val asteroidObserver = Observer<List<AsteroidProperty>> {
        _asteroidList.value = it
    }

    private var asteroidListLiveData: LiveData<List<AsteroidProperty>>

    private val asteroidRepository = AsteroidRepository(getDatabase(application))

    init {
        asteroidListLiveData = asteroidRepository.getAsteroidSelection(DateFilters.SAVED)
        asteroidListLiveData.observeForever(asteroidObserver)
        getAsteroidList()
        getImageOfDay()
    }

    private fun getAsteroidList() {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroids()
                _asteroidLoading.value = StatusLoading.DONE
            } catch (e: Exception) {
                _asteroidLoading.value = StatusLoading.ERROR
                e.printStackTrace()
            }
        }
    }

    private fun getImageOfDay() {
        viewModelScope.launch {
            _pictureLoading.value = StatusLoading.LOADING
            try {
                val picture = PictureApi.retrofitService.getPictureOfDay(Constants.API_KEY)
                if (picture.mediaType.contains("image")) {
                    _imageOfDay.value = picture
                    _pictureStatus.value = PictureStatus.DONE
                    _pictureLoading.value = StatusLoading.DONE
                } else {
                    _pictureStatus.value = PictureStatus.ERROR
                    _pictureLoading.value = StatusLoading.ERROR
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun navigateToDetails(asteroidProperty: AsteroidProperty) {
        _navigateToDetails.value = asteroidProperty
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun displayDetailsComplete() {
        _navigateToDetails.value = null
    }

    fun updateFilter(filter: DateFilters) {
        asteroidListLiveData = asteroidRepository.getAsteroidSelection(filter)
        asteroidListLiveData.observeForever(asteroidObserver)
    }

    override fun onCleared() {
        super.onCleared()
        asteroidListLiveData.removeObserver(asteroidObserver)
    }

}