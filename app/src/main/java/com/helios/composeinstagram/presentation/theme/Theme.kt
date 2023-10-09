package com.helios.composeinstagram.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.helios.composeinstagram.presentation.themeDimenion.kt.Shapes

private val LightColors = lightColorScheme(
    primary = PurpleRed,
    secondary = Purple,
    background = LightGray,
    surface = Color.White,
    onPrimary = LightGray,
    onSecondary = LightGray,
    onBackground = Black,
    onSurface = Black,
)


private val DarkColors = darkColorScheme(
    primary = PurpleRed,
    secondary = Purple,
    background = LightGray,
    surface = Color.White,
    onPrimary = LightGray,
    onSecondary = LightGray,
    onBackground = Black,
    onSurface = Black,
)

@Composable
fun ComposeInstagramTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as Activity
            activity.window.navigationBarColor =
                colors.primary.copy(alpha = 0.08f).compositeOver(colors.surface.copy()).toArgb()
            activity.window.statusBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars =
                !darkTheme
            WindowCompat.getInsetsController(
                activity.window,
                view
            ).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}