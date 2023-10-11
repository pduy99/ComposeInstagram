package com.helios.composeinstagram.common.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.helios.composeinstagram.common.state.PasswordState

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    passwordState: PasswordState = remember { PasswordState() }
) {
    val showPassword = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = passwordState.text,
        onValueChange = {
            passwordState.text = it
            passwordState.enableShowErrors() },
        label = {
            Text(text = "Password", style = MaterialTheme.typography.bodyMedium)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                passwordState.onFocusChange(it.isFocused)
                if (!it.isFocused) {
                    passwordState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = passwordState.showErrors(),
        supportingText = {
            passwordState.getError()?.let {
                Text(text = it)
            }
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = "hide password")
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "show password")
                }
            }
        },
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}
