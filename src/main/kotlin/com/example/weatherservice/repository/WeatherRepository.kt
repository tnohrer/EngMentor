package com.example.weatherservice.repository

import com.example.weatherservice.controller.WeatherResponse
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Repository
class WeatherRepository {

    // Moved to application properties
    private val apiKey = "ENTER YOUR KEY"
    private val weatherBaseUrl = "https://api.openweathermap.org/data/3.0/onecall"
    private val geocodingBaseUrl = "http://api.openweathermap.org/geo/1.0/direct"

    private val webClient = WebClient.builder().build()

    /**
     * Response:
     * Mono: {
     *   hourly: [
     *     {}, {}
     *   ]
     * }
     * Remove mono and use suspend for co-routines
     */
    fun getHourlyWeather(latitude: Float, longitude: Float): Mono<List<WeatherResponse.Hourly>> {
        // Retrieve the data from the API.
        val responseMono = webClient.get()
            .uri("$weatherBaseUrl?lat=$latitude&lon=$longitude&appid=$apiKey")
            .retrieve()
            .bodyToMono(WeatherResponse::class.java)

        return responseMono.map { it.hourly }
    }

    fun getCoordinates(city: String): Mono<Coordinate> {
        return webClient.get()
            .uri("$geocodingBaseUrl?q=$city&limit=1&appid=$apiKey")
            .retrieve()
            .bodyToMono(Array<GeocodingResponse>::class.java)
            .handle<Coordinate> { responseArray, sink ->
                val response = responseArray.firstOrNull()
                if (response != null) {
                    sink.next(Coordinate(response.lat, response.lon))
                } else {
                    sink.error(RuntimeException("City not found"))
                }
            }
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
