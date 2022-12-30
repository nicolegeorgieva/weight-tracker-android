package com.weighttracker.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NumberInputField(
    number: Double?,
    placeholder: String,
    onValueChange: (Double) -> Unit,
) {
    InputField(
        value = number?.toString() ?: "",
        placeholder = placeholder,
        keyboardType = KeyboardType.Number,
        onValueChange = {
            if (it.toDoubleOrNull() != null) {
                onValueChange(it.toDouble())
            }
        }
    )
}