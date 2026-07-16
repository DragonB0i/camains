package com.example.trl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun ScanLine() {

    val screenHeight =
        LocalConfiguration.current
            .screenHeightDp.toFloat()

    val transition =
        rememberInfiniteTransition()

    val y by transition.animateFloat(

        initialValue = 0f,

        targetValue = screenHeight,

        animationSpec =
            infiniteRepeatable(
                tween(
                    durationMillis = 2500,
                    easing = LinearEasing
                )
            )
    )

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .offset(
                    y = y.dp
                )
                .height(3.dp)
                .background(
                    Color.Cyan.copy(
                        alpha = 0.45f
                    )
                )
    )
}