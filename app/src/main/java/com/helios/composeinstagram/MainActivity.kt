package com.helios.composeinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.helios.composeinstagram.navigation.InstagramNavHost
import com.helios.composeinstagram.presentation.signin.SignInScreen
import com.helios.composeinstagram.presentation.signup.SignUpScreen
import com.helios.composeinstagram.presentation.theme.ComposeInstagramTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeInstagramTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ComposedInstagramApp()
                }
            }
        }
    }
}

@Composable
fun ComposedInstagramApp(navController: NavHostController = rememberNavController()) {
    Scaffold {innerPadding ->
        InstagramNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
