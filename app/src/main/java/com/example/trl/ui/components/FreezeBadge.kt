package com.example.trl.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.trl.ui.theme.*

@Composable
fun FreezeBadge(
    frozen: Boolean
) {

    val transition =
        rememberInfiniteTransition()

    val alpha by transition.animateFloat(

        initialValue = 0.4f,
        targetValue = 1f,

        animationSpec =
            infiniteRepeatable(
                tween(700),
                RepeatMode.Reverse
            )
    )

    Text(

        text =
            if (frozen)
                "FREEZE MODE"
            else
                "LIVE OCR",

        color =
            if (frozen)
                NeonRed
            else
                NeonGreen,

        fontWeight =
            FontWeight.Bold,

        modifier =
            Modifier

                .graphicsLayer {

                    this.alpha =
                        if (frozen)
                            alpha
                        else
                            1f
                }

                .background(
                    CardColor,
                    RoundedCornerShape(
                        16.dp
                    )
                )

                .border(
                    2.dp,

                    if (frozen)
                        NeonRed
                    else
                        NeonGreen,

                    RoundedCornerShape(
                        16.dp
                    )
                )

                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
    )
}