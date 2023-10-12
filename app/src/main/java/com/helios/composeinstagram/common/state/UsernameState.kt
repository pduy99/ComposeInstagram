package com.helios.composeinstagram.common.state

class UsernameState(initialText: String? = null) : TextFieldState(
    validator = ::isUsernameValid,
    errorFor = ::usernameValidationError
) {
    init {
        initialText?.let {
            text = it
        }
    }
}


private fun isUsernameValid(username: String): Boolean {
    return username.length >= 3
}

private fun usernameValidationError(username: String): String {
    return "Username must have at least 3 characters"
}