package com.weighttracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler

@Composable
fun browser(): UriHandler = LocalUriHandler.current