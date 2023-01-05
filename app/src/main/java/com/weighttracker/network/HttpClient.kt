package com.weighttracker.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

inline fun <reified T> request(
    crossinline execute: suspend (HttpClient) -> HttpResponse
): Flow<T?> = flow {
    val result = try {
        val response = execute(ktorClient())

        if (response.status.isSuccess()) {
            response.body<T>()
        } else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
    emit(result)
}.flowOn(Dispatchers.IO)