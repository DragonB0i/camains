package com.example.trl.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun FocusOverlay() {

    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        val width =
            size.width * 0.80f

        val height =
            size.height * 0.30f

        val x =
            (size.width - width) / 2

        val y =
            (size.height - height) / 2

        drawRect(
            color =
                Color.Cyan.copy(
                    alpha = 0.6f
                ),

            topLeft =
                Offset(
                    x,
                    y
                ),

            size =
                Size(
                    width,
                    height
                ),

            style =
                Stroke(
                    width = 4f
                )
        )
    }
}