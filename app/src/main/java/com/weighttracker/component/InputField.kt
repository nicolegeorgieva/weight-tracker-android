package com.weighttracker.component

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun closeKeyboardImeAction(): KeyboardActionScope.(ImeAction) -> Unit {
    val keyboardController = LocalSoftwareKeyboardController.current
    return {
        keyboardController?.hide()
    }
}

@Composable
fun InputField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: KeyboardActionScope.(ImeAction) -> Unit = closeKeyboardImeAction(),
    onValueChange: (String) -> Unit,
) {
    var textField by remember {
        // move the cursor at the end of the text
        val selection = TextRange(value.length)
        mutableStateOf(TextFieldValue(value, selection))
    }
    LaunchedEffect(value) {
        if (value != textField.text) {
            delay(50) // fix race condition
            textField = TextFieldValue(
                value, TextRange(value.length)
            )
        }
    }
    OutlinedTextField(
        modifier = modifier,
        value = textField,
        onValueChange = { newValue ->
            // new value different than the current one
            if (newValue.text != textField.text) {
                onValueChange(newValue.text)
            }
            textField = newValue
        },
        placeholder = {
            Text(
                text = placeholder,
            )
        },
        enabled = enabled,
        readOnly = readOnly,
        isError = isError,
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            autoCorrect = true,
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        visualTransformation = visualTransformation,
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction(ImeAction.Done)
            },
            onGo = {
                onImeAction(ImeAction.Go)
            },
            onNext = {
                onImeAction(ImeAction.Next)
            },
            onPrevious = {
                onImeAction(ImeAction.Previous)
            },
            onSearch = {
                onImeAction(ImeAction.Search)
            },
            onSend = {
                onImeAction(ImeAction.Send)
            },
        )
    )
}

