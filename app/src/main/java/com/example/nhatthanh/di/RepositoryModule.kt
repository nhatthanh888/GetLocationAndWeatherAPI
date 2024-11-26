package com.example.nhatthanh.di


import com.example.nhatthanh.WeatherRepo
import com.example.nhatthanh.respository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providerWeather(weatherRepository: WeatherRepository): WeatherRepo =
        weatherRepository
}