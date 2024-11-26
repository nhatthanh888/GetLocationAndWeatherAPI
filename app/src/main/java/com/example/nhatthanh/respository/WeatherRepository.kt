package com.example.nhatthanh.respository

import com.example.nhatthanh.WeatherApiService
import com.example.nhatthanh.WeatherRepo
import com.example.nhatthanh.model.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: WeatherApiService) :
    WeatherRepo {
    override suspend fun getCurrentWeather(
        lat: Double,
        long: Double,
    ): WeatherResponse {
        return apiService.getCurrentWeather(lat, long, "b6808e3f8dd5386bd6bf4ac6826b18c2")
    }

}
