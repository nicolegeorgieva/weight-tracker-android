package com.weighttracker.component

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpandCollapseArrow(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit
) {
    Icon(
        if (expanded) {
            Icons.Default.KeyboardArrowUp
        } else {
            Icons.Default.KeyboardArrowDown
        },
        contentDescription = "",
        modifier = Modifier.clickable {
            onExpandChange(!expanded)
        }
    )
}