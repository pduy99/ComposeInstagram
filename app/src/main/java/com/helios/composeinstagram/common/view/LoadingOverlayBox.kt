package com.helios.composeinstagram.common.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingOverlayBox(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    loadingText: String = "",
    content: @Composable () -> Unit
) {
    val updatedContent by rememberUpdatedState(content)
    val progressIndicator = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        updatedContent()
        if (isLoading) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(Color.Gray.copy(alpha = 0.5f))

            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = loadingText)
            }
        }
    }

    LaunchedEffect(key1 = isLoading) {
        progressIndicator.value = isLoading
    }
}