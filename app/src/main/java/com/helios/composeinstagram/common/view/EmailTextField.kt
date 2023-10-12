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
import com.helios.composeinstagram.common.state.EmailState

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailState: EmailState = remember { EmailState() },
) {

    OutlinedTextField(
        value = emailState.text,
        onValueChange = { emailState.text = it },
        label = {
            Text(text = "Email", style = MaterialTheme.typography.bodyMedium)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                emailState.onFocusChange(it.isFocused)
                if (!it.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = emailState.showErrors(),
        supportingText = {
            if (emailState.showErrors()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = emailState.getError() ?: "Unknown Error",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (emailState.showErrors())
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "error icon",
                    tint = MaterialTheme.colorScheme.error
                )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}