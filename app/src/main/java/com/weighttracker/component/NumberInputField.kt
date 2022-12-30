package com.weighttracker.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import java.text.DecimalFormat

@Composable
fun NumberInputField(
    number: Double?,
    placeholder: String,
    onValueChange: (Double) -> Unit,
) {
    InputField(
        value = if (number != null)
            DecimalFormat("###,###.##").format(number) else "",
        placeholder = placeholder,
        keyboardType = KeyboardType.Number,
        onValueChange = {
            if (it.toDoubleOrNull() != null) {
                onValueChange(it.toDouble())
            }
        }
    )
}