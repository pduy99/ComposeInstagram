package com.helios.composeinstagram.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helios.composeinstagram.presentation.signin.SignInScreen
import com.helios.composeinstagram.presentation.signup.SignUpScreen

@Composable
fun InstagramNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, modifier = modifier, startDestination = AppDestination.SignIn.name) {
        composable(route = AppDestination.SignIn.name) {
            SignInScreen()
        }
        composable(route = AppDestination.Signup.name) {
            SignUpScreen()
        }
    }
}
