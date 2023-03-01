package com.weighttracker.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

fun ktorClient(): HttpClient = HttpClient {
    install(Logging)

    install(HttpTimeout) {
        requestTimeoutMillis = 1000
        connectTimeoutMillis = 1000
    }

    install(ContentNegotiation) {
        gson(
            contentType = ContentType.Any
        )
    }
}