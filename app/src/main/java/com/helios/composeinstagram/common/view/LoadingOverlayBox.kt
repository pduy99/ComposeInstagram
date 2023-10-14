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

/**
 * Composable function that displays a loading overlay on top of its content when
 * the 'isLoading' flag is set to true. This overlay includes a grayed-out background,
 * a loading spinner, and an optional loading text.
 *
 * @param modifier The modifier for positioning and sizing the LoadingOverlayBox.
 * @param isLoading A boolean flag indicating whether the loading overlay should be shown.
 * @param loadingText The text to be displayed below the loading spinner.
 * @param content The composable content that will be displayed beneath the loading overlay.
 */
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

