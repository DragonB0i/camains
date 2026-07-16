package com.example.trl.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun CyberBackground() {

    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        /*
         * BACKGROUND
         */

        drawRect(

            brush =
                Brush.verticalGradient(

                    listOf(
                        Color.Black,
                        Color(0xFF06111D),
                        Color.Black
                    )
                )
        )

        /*
         * CYBER GRID
         */

        val step = 60f

        for (
        x in 0..size.width.toInt()
                step step.toInt()
        ) {

            drawLine(

                color =
                    Color.Cyan.copy(
                        alpha = 0.08f
                    ),

                start =
                    Offset(
                        x.toFloat(),
                        0f
                    ),

                end =
                    Offset(
                        x.toFloat(),
                        size.height
                    )
            )
        }

        for (
        y in 0..size.height.toInt()
                step step.toInt()
        ) {

            drawLine(

                color =
                    Color.Cyan.copy(
                        alpha = 0.08f
                    ),

                start =
                    Offset(
                        0f,
                        y.toFloat()
                    ),

                end =
                    Offset(
                        size.width,
                        y.toFloat()
                    )
            )
        }
    }
}