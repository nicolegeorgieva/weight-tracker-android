package com.weighttracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Composable
fun browser(): UriHandler = LocalUriHandler.current

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