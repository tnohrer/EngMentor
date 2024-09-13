package com.example.weatherservice.controller

import com.example.weatherservice.model.WeatherModel
import com.example.weatherservice.service.WeatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class WeatherController(
    private val weatherService: WeatherService
) {

    /**
     * Example: http://localhost:8080/weathers
     * With City param: http://localhost:8080/weathers?city=Dallas
     */
    @GetMapping("/weathers")
    fun getWeathers(@RequestParam city: String): Mono<List<WeatherModel>> {
        return weatherService.getWeathers(city)
    }
}