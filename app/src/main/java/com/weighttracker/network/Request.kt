package com.weighttracker.network

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

sealed interface RemoteCall<out E, out D> {
    object Loading : RemoteCall<Nothing, Nothing>
    data class Error<out E>(val error: E) : RemoteCall<E, Nothing>
    data class Ok<out D>(val data: D) : RemoteCall<Nothing, D>
}

fun <E, D1, D2> RemoteCall<E, D1>.mapSuccess(
    transform: (D1) -> D2
): RemoteCall<E, D2> = when (this) {
    is RemoteCall.Error -> this
    is RemoteCall.Loading -> this
    is RemoteCall.Ok -> RemoteCall.Ok(
        data = transform(this.data)
    )
}

fun <E, E2, D> RemoteCall<E, D>.mapError(
    transform: (E) -> E2
): RemoteCall<E2, D> = when (this) {
    is RemoteCall.Error -> RemoteCall.Error(
        error = transform(this.error)
    )
    is RemoteCall.Loading -> this
    is RemoteCall.Ok -> this
}

sealed interface NetworkError {
    object Generic : NetworkError
}

abstract class SimpleRequest<in Input, out R> : Request<Input, NetworkError, R>() {
    override suspend fun handleError(
        response: HttpResponse?
    ): NetworkError = NetworkError.Generic
}

abstract class Request<in Input, out E, out R> {
    protected abstract suspend fun request(input: Input): Either<HttpResponse?, R>
    protected abstract suspend fun handleError(response: HttpResponse?): E

    private val requestTriggerFlow = MutableSharedFlow<Unit>(
        replay = 1,
    )

    init {
        requestTriggerFlow.tryEmit(Unit)
    }

    fun flow(input: Input): Flow<RemoteCall<E, R>> = channelFlow {
        requestTriggerFlow.collectLatest {
            send(RemoteCall.Loading)
            send(
                when (val response = request(input)) {
                    is Either.Left -> RemoteCall.Error(handleError(response.value))
                    is Either.Right -> RemoteCall.Ok(response.value)
                }
            )
        }
    }

    suspend fun retry() {
        requestTriggerFlow.emit(Unit)
    }
}

suspend inline fun <reified R> httpRequest(
    httpClient: HttpClient = ktorClient(),
    crossinline request: suspend HttpClient.() -> HttpResponse
): Either<HttpResponse?, R> = withContext(Dispatchers.IO) {
    try {
        val response = request(httpClient)
        if (response.status.isSuccess()) {
            response.body<R>().right()
        } else response.left()
    } catch (ignored: Exception) {
        null.left()
    }
}