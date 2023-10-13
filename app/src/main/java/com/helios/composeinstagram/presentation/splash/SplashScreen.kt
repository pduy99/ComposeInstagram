package com.helios.composeinstagram.presentation.splash

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.helios.instagramclone.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onSignedIn: () -> Unit, onNeedSignIn: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val viewModel = hiltViewModel<SplashScreenViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val errorEvent by viewModel.errorEvent.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(key1 = uiState.isSignedIn, uiState.isNeedSignIn) {
            if (uiState.isSignedIn) {
                onSignedIn()
            } else if (uiState.isNeedSignIn) {
                onNeedSignIn()
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ig_logo),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .background(MaterialTheme.colorScheme.background)
        )

        errorEvent.getContentOrNull()?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
