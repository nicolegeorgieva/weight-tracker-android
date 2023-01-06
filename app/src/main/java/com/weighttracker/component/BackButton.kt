package com.weighttracker.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.weighttracker.R
import com.weighttracker.Screens
import com.weighttracker.navigateTo

@Composable
fun BackButton(screens: Screens) {
    OutlinedButton(
        modifier = Modifier.size(48.dp),
        contentPadding = PaddingValues(0.dp),
        onClick = { navigateTo(screens) },
        border = BorderStroke(width = 1.dp, color = Color(0xFFBBB9B9)),
        shape = CircleShape
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
            contentDescription = "",
            tint = Color.Black
        )
    }
    BackHandler {
        navigateTo(screens)
    }
}