package com.weighttracker.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

fun ktorClient(): HttpClient = HttpClient {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("http", message)
            }
        }
        level = LogLevel.ALL
    }

    install(HttpTimeout) {
        requestTimeoutMillis = 10_000
        connectTimeoutMillis = 10_000
    }

    install(ContentNegotiation) {
        gson(
            contentType = ContentType.Any
        )
    }
}