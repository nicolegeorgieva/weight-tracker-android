package com.weighttracker.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.DecimalFormat

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NumberInputField(
    number: Double?,
    placeholder: String,
    onValueChange: (Double) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    InputField(
        value = if (number != null)
            DecimalFormat("###,###.##").format(number) else "",
        placeholder = placeholder,
        keyboardType = KeyboardType.Number,
        onValueChange = {
            if (it.toDoubleOrNull() != null) {
                onValueChange(it.toDouble())
            }
        },
        imeAction = ImeAction.Done,
        onImeAction = {
            keyboardController?.hide()
        }
    )
}