package com.helios.composeinstagram.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.helios.composeinstagram.presentation.home.HomeScreen
import com.helios.composeinstagram.presentation.signin.SignInScreen
import com.helios.composeinstagram.presentation.signup.SignUpScreen

@Composable
fun InstagramNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = AppDestination.SignIn.name,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }) {
        composable(route = AppDestination.SignIn.name) {
            SignInScreen(
                onNavigateToSignUpScreen = {
                    navController.navigate(AppDestination.Signup.name) {
                        popUpTo(0)
                    }
                },
                onSignInSuccess = {
                    navController.navigate(AppDestination.Home.name) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(route = AppDestination.Signup.name) {
            SignUpScreen(onNavigateToSignInScreen = {
                navController.navigate(AppDestination.SignIn.name) {
                    popUpTo(0)
                }
            })
        }
        composable(route = AppDestination.Home.name) {
            HomeScreen()
        }
    }
}
