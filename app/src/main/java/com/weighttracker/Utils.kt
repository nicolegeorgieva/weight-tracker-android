package com.weighttracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun browser(): UriHandler = LocalUriHandler.current

/**
 * @return 2 decimals after "." if they make sense (e.g. "50.0" -> 50; "49.45" -> "49.45")
 */
fun formatNumber(number: Double): String {
    return DecimalFormat("###,###.##").format(number)
}

fun String.toUUID(): UUID = UUID.fromString(this)

fun String.toUUIDOrNull(): UUID? = try {
    UUID.fromString(this)
} catch (e: Exception) {
    null
}

fun String?.isNotEmpty(): Boolean = !isNullOrEmpty()

fun String?.isNotBlank(): Boolean = !isNullOrBlank()

fun Instant.toLocal(): LocalDateTime =
    LocalDateTime.ofInstant(this, ZoneId.systemDefault())

fun LocalDateTime.toUtc(): Instant = toInstant(
    ZoneId.systemDefault().rules.getOffset(this)
)

fun LocalDateTime.toEpochMilli(): Long =
    toUtc().toEpochMilli()

fun LocalDateTime.toEpochSeconds() =
    toUtc().epochSecond

fun LocalDateTime.format(pattern: String): String =
    this.format(DateTimeFormatter.ofPattern(pattern))

fun LocalDate.format(pattern: String): String =
    this.format(DateTimeFormatter.ofPattern(pattern))

/**
 * @return list of flows -> flow of list
 */
inline fun <reified T> combineList(flows: List<Flow<T>>): Flow<List<T>> =
    if (flows.isEmpty()) flowOf(emptyList()) else combine(flows, Array<T>::toList)

inline fun <reified T, reified R> combineSafe(
    flows: List<Flow<T>>,
    ifEmpty: R,
    crossinline transform: suspend (List<T>) -> R,
): Flow<R> = if (flows.isEmpty()) flowOf(ifEmpty) else
    combine(flows) { res -> transform(res.toList()) }

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <reified T> Flow<Flow<T>>.flattenLatest(): Flow<T> =
    flatMapLatest { it }


// region combine (more than 5)
@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
    )
}

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
    )
}

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, T8, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8) -> R
): Flow<R> = combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
    )
}

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9) -> R
): Flow<R> = combine(
    flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9
) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
        args[8] as T9,
    )
}

@Suppress("UNCHECKED_CAST")
fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    transform: suspend (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10) -> R
): Flow<R> = combine(
    flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9, flow10
) { args: Array<*> ->
    transform(
        args[0] as T1,
        args[1] as T2,
        args[2] as T3,
        args[3] as T4,
        args[4] as T5,
        args[5] as T6,
        args[6] as T7,
        args[7] as T8,
        args[8] as T9,
        args[9] as T10,
    )
}

// endregion