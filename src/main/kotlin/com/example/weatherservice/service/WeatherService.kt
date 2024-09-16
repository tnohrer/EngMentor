package com.example.weatherservice.service

import com.example.weatherservice.model.WeatherModel
import com.example.weatherservice.repository.WeatherRepository
import org.springframework.stereotype.Service

@Service
class WeatherService(
    private val weatherRepository: WeatherRepository
) {

    suspend fun getWeathers(city: String): List<WeatherModel> {
        val coordinates = weatherRepository.getCoordinates(city)
        val hourlyWeathers = weatherRepository.getHourlyWeather(coordinates.latitude, coordinates.longitude)
        return hourlyWeathers.map { hourly ->
            WeatherModel(
                temperature = hourly.temp,
                humidity = hourly.humidity,
                description = hourly.weather.firstOrNull()?.description ?: "No description"
            )
        }
    }
}