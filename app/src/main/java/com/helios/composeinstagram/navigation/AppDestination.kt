package com.helios.composeinstagram.navigation

sealed class AppDestination(val name: String) {
    object SlashScreen : AppDestination("splash_screen")
    object Signup : AppDestination("signup")
    object SignIn : AppDestination("login")
    object Home : AppDestination("home")
}
