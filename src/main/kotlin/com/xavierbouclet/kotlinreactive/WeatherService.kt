package com.xavierbouclet.kotlinreactive

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

class WeatherService(private val weatherProperties: WeatherProperties) {

    var client = WebClient.builder()
        .baseUrl(weatherProperties.url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()

    suspend fun getWeatherFromZipCode(zipCode: String): WeatherRoot {
        val uriSpec: WebClient.RequestHeadersSpec<*> = client.get().uri { uriBuilder ->
            uriBuilder
                .queryParam("key", weatherProperties.apiKey)
                .queryParam("q", zipCode)
                .queryParam("days", "1")
                .queryParam("aqi", "no")
                .queryParam("alerts", "no")
                .build()
        }

        return uriSpec.awaitExchange().awaitBody()
    }
}