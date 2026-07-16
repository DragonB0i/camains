package com.example.trl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trl.ui.theme.*

@Composable
fun AnswerOverlay(
    answer: String,
    confidence: Int
) {

    val transition =
        rememberInfiniteTransition()

    val glow by transition.animateFloat(

        initialValue = 12f,

        targetValue = 32f,

        animationSpec =

            infiniteRepeatable(

                tween(1000),

                RepeatMode.Reverse
            )
    )

    val color = when (answer) {

        "A" -> NeonGreen
        "B" -> NeonBlue
        "C" -> NeonYellow
        "D" -> NeonRed

        else -> TextPrimary
    }

    Column(

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Box(

            modifier = Modifier

                .shadow(
                    glow.dp,
                    RoundedCornerShape(
                        24.dp
                    )
                )

                .graphicsLayer {
                    alpha = 0.95f
                }

                .background(

                    CardColor.copy(
                        alpha = 0.85f
                    ),

                    RoundedCornerShape(
                        24.dp
                    )
                )

                .shimmerBorder()

                .padding(

                    horizontal = 60.dp,
                    vertical = 20.dp
                )
        ) {

            Text(

                text = answer,

                color = color,

                fontSize = 140.sp,

                fontWeight =
                    FontWeight.ExtraBold
            )
        }

        Spacer(
            Modifier.height(
                16.dp
            )
        )

        Text(

            text =
                "CONFIDENCE :: $confidence%",

            color = color,

            fontSize = 22.sp,

            fontWeight =
                FontWeight.Bold
        )
    }
}