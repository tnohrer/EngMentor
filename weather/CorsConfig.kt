package com.example.weatherservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import java.util.*

@Configuration
class CorsConfig {

    val logger: Logger = LoggerFactory.getLogger(CorsConfig::class.java)

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        logger.info("Loading CORS WebFilter")

        val corsConfig = CorsConfiguration()
        //corsConfig.applyPermitDefaultValues()
        corsConfig.addAllowedOriginPattern("null")
        corsConfig.addAllowedOrigin("http://localhost:63342") // Specified front-end origin
//       corsConfig.maxAge = 8000
        corsConfig.addAllowedHeader("*")
        corsConfig.addAllowedMethod("*")
        corsConfig.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return CorsWebFilter(source)
    }
}