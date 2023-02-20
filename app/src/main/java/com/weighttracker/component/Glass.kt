package com.weighttracker.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.weighttracker.R

@Composable
fun Glass(
    filled: Boolean,
    modifier: Modifier = Modifier,
    onFilledChange: (filled: Boolean) -> Unit,
) {
    AsyncImage(
        modifier = modifier
            .size(56.dp)
            .clickable {
                onFilledChange(!filled)
            },
        model = if (filled) R.drawable.full_glass else R.drawable.empty_glass,
        contentDescription = ""
    )
}