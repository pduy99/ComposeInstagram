package com.helios.composeinstagram.common.state

import java.util.regex.Pattern

private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"

class EmailState(email: String? = null) : TextFieldState(
    validator = ::isEmailValid, errorFor = ::emailValidationError
) {
    init {
        email?.let {
            text = it
        }
    }
}

private fun emailValidationError(email: String): String {
    return "Invalid email format: $email"
}

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
}

internal val EmailStateSaver = textFieldStateSaver(EmailState())