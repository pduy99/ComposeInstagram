package com.helios.composeinstagram.common.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorFor: (String) -> String = { "" }
) {
    var text: String by mutableStateOf("")
    var wasEverFocus: Boolean by mutableStateOf(false)
    private var isFocus: Boolean by mutableStateOf(false)
    private var displayErrors: Boolean by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocus = focused
        if (focused) wasEverFocus = true
    }

    fun enableShowErrors() {
        // Only show errors if the text field ever focus
        if (wasEverFocus) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getError(): String? {
        return if (showErrors()) {
            errorFor(text)
        } else {
            null
        }
    }
}

fun textFieldStateSaver(state: TextFieldState) = listSaver<TextFieldState, Any>(
    save = { listOf(it.text, it.wasEverFocus) },
    restore = {
        state.apply {
            text = it[0] as String
            wasEverFocus = it[1] as Boolean
        }
    }
)