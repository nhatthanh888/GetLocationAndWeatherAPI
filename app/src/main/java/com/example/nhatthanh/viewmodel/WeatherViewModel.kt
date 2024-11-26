package com.example.nhatthanh.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nhatthanh.model.WeatherResponse
import com.example.nhatthanh.respository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val _weatherResponse = mutableStateOf<WeatherResponse?>(null)
    val weatherResponse: State<WeatherResponse?> = _weatherResponse

    fun getCurrentWeather(lat: Double, long: Double) {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getCurrentWeather(lat, long)
                _weatherResponse.value = response
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather: ${e.message}")
            }
        }
    }

}