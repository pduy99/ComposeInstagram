package com.helios.composeinstagram.navigation

sealed class AppDestination(val name: String) {
    object Signup : AppDestination("signup")
    object SignIn : AppDestination("login")
}
