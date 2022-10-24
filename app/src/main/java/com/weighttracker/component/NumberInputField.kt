package com.weighttracker.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NumberInputField(
    onNumberEnter: (Double?) -> Unit
) {
    var currentText by remember {
        mutableStateOf("")
    }
    TextField(
        value = currentText,
        onValueChange = {
            currentText = it
            onNumberEnter(it.toDoubleOrNull())
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}

