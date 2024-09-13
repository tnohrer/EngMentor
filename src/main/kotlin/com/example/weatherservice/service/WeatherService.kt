package com.example.weatherservice.service

import com.example.weatherservice.model.WeatherModel
import com.example.weatherservice.repository.WeatherRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class WeatherService(
    private val weatherRepository: WeatherRepository
) {

    fun getWeathers(city: String): Mono<List<WeatherModel>> {
        return weatherRepository.getCoordinates(city)
            .flatMap { coordinates ->
                weatherRepository.getHourlyWeather(coordinates.latitude, coordinates.longitude)
            }
            .map { hourlyWeatherList ->
                hourlyWeatherList.map { hourly ->
                    WeatherModel(
                        temperature = hourly.temp,
                        humidity = hourly.humidity,
                        description = hourly.weather.firstOrNull()?.description ?: "No description"
                    )
                }
            }
    }
}