package com.example.trl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trl.ui.theme.*

@Composable
fun ConfidenceRing(
    confidence: Int
) {

    val color = when {

        confidence >= 95 ->
            NeonGreen

        confidence >= 80 ->
            NeonYellow

        else ->
            NeonRed
    }

    val animation =
        remember {
            Animatable(0f)
        }

    LaunchedEffect(
        confidence
    ) {

        animation.animateTo(
            confidence.toFloat(),
            tween(1200)
        )
    }

    Box(
        contentAlignment =
            Alignment.Center,

        modifier =
            Modifier.size(
                120.dp
            )
    ) {

        Canvas(
            modifier =
                Modifier.fillMaxSize()
        ) {

            drawArc(
                color = CardColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style =
                    Stroke(
                        width = 14f
                    )
            )

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle =
                    animation.value * 3.6f,
                useCenter = false,
                style =
                    Stroke(
                        width = 14f,
                        cap =
                            StrokeCap.Round
                    )
            )
        }

        Text(
            text =
                "${confidence}%",
            color = color,
            fontSize = 24.sp,
            fontWeight =
                FontWeight.Bold
        )
    }
}