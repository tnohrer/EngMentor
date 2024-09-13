package com.example.weatherservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class WeatherServiceApplication

fun main(args: Array<String>) {
	runApplication<WeatherServiceApplication>(*args)
}

@RestController
class HelloController {

	@GetMapping("/hello")
	fun hello() = "Hello World!"
}
