package com.helios.composeinstagram.common.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import com.helios.composeinstagram.common.state.UsernameState

@Composable
fun UsernameTextField(
    modifier: Modifier = Modifier,
    usernameState: UsernameState = remember { UsernameState() },
) {

    OutlinedTextField(
        value = usernameState.text,
        onValueChange = { usernameState.text = it },
        label = {
            Text(text = "Username", style = MaterialTheme.typography.bodyMedium)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                usernameState.onFocusChange(it.isFocused)
                if (!it.isFocused) {
                    usernameState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = usernameState.showErrors(),
        supportingText = {
            if (usernameState.showErrors()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = usernameState.getError() ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (usernameState.showErrors())
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "error icon",
                    tint = MaterialTheme.colorScheme.error
                )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}