package com.example.trl.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.trl.ocr.OCRState

@Composable
fun ZoomIndicator() {

    val zoom =
        OCRState.zoomText.value

    val color =
        when (OCRState.confidenceColor.value) {

            3 -> Color.Red

            2 -> Color.Yellow

            else -> Color.Cyan
        }

    Text(
        text = "ZOOM :: $zoom",
        color = color,
        fontWeight = FontWeight.Bold
    )
}