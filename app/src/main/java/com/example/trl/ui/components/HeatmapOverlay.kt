package com.example.trl.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.trl.ocr.OCRState

@Composable
fun HeatmapOverlay() {

    val boxes =
        OCRState.boxes

    Canvas(
        modifier =
            Modifier.fillMaxSize()
    ) {

        boxes.forEach {

            val color =
                when {

                    it.width() < 250 ->
                        Color.Red.copy(0.22f)

                    it.width() < 500 ->
                        Color.Yellow.copy(0.18f)

                    else ->
                        Color.Green.copy(0.15f)
                }

            drawRect(
                color = color,

                topLeft =
                    Offset(
                        it.left.toFloat(),
                        it.top.toFloat()
                    ),

                size =
                    Size(
                        it.width().toFloat(),
                        it.height().toFloat()
                    )
            )
        }
    }
}