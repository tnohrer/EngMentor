package com.example.weatherservice.repository

import com.example.weatherservice.controller.WeatherResponse
import kotlinx.coroutines.reactor.awaitBody
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient

@Repository
class WeatherRepository {

    private val apiKey = "294453e70380ebba19aa7a1e8db77e4b"
    private val weatherBaseUrl = "https://api.openweathermap.org/data/3.0/onecall"
    private val geocodingBaseUrl = "http://api.openweathermap.org/geo/1.0/direct"

    private val webClient = WebClient.builder().build()

    suspend fun getHourlyWeather(latitude: Float, longitude: Float): List<WeatherResponse.Hourly> {
        val response = webClient.get()
            .uri("$weatherBaseUrl?lat=$latitude&lon=$longitude&appid=$apiKey")
            .retrieve()
            .awaitBody<WeatherResponse>()

        return response.hourly
    }

    suspend fun getCoordinates(city: String): Coordinate {
        val responseArray = webClient.get()
            .uri("$geocodingBaseUrl?q=$city&limit=1&appid=$apiKey")
            .retrieve()
            .awaitBody<Array<GeocodingResponse>>()

        val response = responseArray.firstOrNull()
            ?: throw RuntimeException("City not found")

        return Coordinate(response.lat, response.lon)
    }
}

data class Coordinate(
    val latitude: Float,
    val longitude: Float
)

data class GeocodingResponse(
    val name: String,
    val lat: Float,
    val lon: Float
)