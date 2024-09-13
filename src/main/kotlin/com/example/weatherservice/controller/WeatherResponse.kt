package com.example.weatherservice.controller

data class WeatherResponse(
    val current: Current,
    val hourly: List<Hourly>
) {
    data class Current(
        val temp: Float,
        val humidity: Float,
        val weather: List<Weather>
    )

    data class Hourly(
        val temp: Float,
        val humidity: Float,
        val weather: List<Weather>
    )

    data class Weather(
        val description: String
    )
}