package com.example.nhatthanh

import com.example.nhatthanh.model.WeatherResponse

interface WeatherRepo {
    suspend fun getCurrentWeather(lat: Double, long: Double): WeatherResponse
}