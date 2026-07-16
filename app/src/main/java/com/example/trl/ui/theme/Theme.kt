package com.example.trl.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColors =
    darkColorScheme(

        primary = NeonBlue,

        secondary = NeonPurple,

        tertiary = NeonGreen,

        background = Background,

        surface = Surface,

        onPrimary = TextPrimary,

        onBackground = TextPrimary,

        onSurface = TextPrimary
    )

@Composable
fun TrlTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(

        colorScheme = CyberColors,

        typography = Typography,

        content = content
    )
}