package com.weighttracker.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.gson.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun ktorClient(): HttpClient = HttpClient {
    install(Logging)

    install(ContentNegotiation) {
        gson()
    }
}

inline fun <reified T> request(
    crossinline execute: suspend (HttpClient) -> HttpResponse
): Flow<T?> {
    return flow {
        val result = try {
            execute(ktorClient()).body<T>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        emit(result)
    }
}