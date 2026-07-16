package com.example.trl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shimmerBorder(): Modifier {

    val transition =
        rememberInfiniteTransition()

    val x by transition.animateFloat(

        initialValue = 0f,

        targetValue = 1000f,

        animationSpec =
            infiniteRepeatable(
                tween(1500)
            )
    )

    return this.border(
        2.dp,

        Brush.horizontalGradient(
            listOf(
                Color.Cyan,
                Color.Magenta,
                Color.Cyan
            ),

            startX = x,
            endX = x + 400
        ),

        RoundedCornerShape(24.dp)
    )
}