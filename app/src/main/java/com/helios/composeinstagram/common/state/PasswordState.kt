package com.helios.composeinstagram.common.state

class PasswordState : TextFieldState(
    validator = ::isPasswordValid,
    errorFor = ::buildPasswordValidationError
)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

/**
 * Finds the all invalid password rule for the given password.
 *
 * @return The list of [PasswordRules] that the password violates.
 * Empty list if the password is valid.
 */
private fun findViolatedPasswordRule(password: String): List<PasswordRules> {
    val violatedRules = arrayListOf<PasswordRules>()
    for (rule in PasswordRules.values()) {
        if (!rule.validator(password)) {
            violatedRules.add(rule)
        }
    }
    return violatedRules
}

private fun isPasswordValid(password: String): Boolean {
    return findViolatedPasswordRule(password).isEmpty()
}

private fun buildPasswordValidationError(password: String): String {
    return findViolatedPasswordRule(password).joinToString("\n") {
        it.errorMessage
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun passwordConfirmationError(): String {
    return "Passwords don't match"
}

private enum class PasswordRules(val validator: (String) -> Boolean, val errorMessage: String) {
    AT_LEAST_EIGHT_CHARACTERS(
        validator = { it.length >= 8 },
        errorMessage = "Password must have at least 8 characters"
    ),
    HAS_UPPERCASE_LETTER(validator = {
        it.any { c ->
            c.isUpperCase() && c.isLetter()
        }
    }, errorMessage = "Password must have at least 1 uppercase letter"),
    HAS_LOWERCASE_LETTER(validator = {
        it.any { c ->
            c.isLowerCase() && c.isLetter()
        }
    }, errorMessage = "Password must have at least 1 lower letter"),
    HAS_NUMBER(validator = {
        it.any { c ->
            c.isDigit()
        }
    }, errorMessage = "Password must have at least 1 number"),
}