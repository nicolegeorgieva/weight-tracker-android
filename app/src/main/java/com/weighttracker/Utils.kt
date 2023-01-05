package com.weighttracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import java.text.DecimalFormat

@Composable
fun browser(): UriHandler = LocalUriHandler.current

fun formatNumber(number: Double): String {
    return DecimalFormat("###,###.##").format(number)
}