package com.example.trl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.trl.ui.theme.*

@Composable
fun BottomPanel(
    question: String,
    answer: String,
    confidence: Int,
    modelsUsed: Int,
    judgeUsed: Boolean,
    frozen: Boolean
) {

    NeonCard {

        Text(
            text = "QUESTION",
            color = NeonBlue,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = question,
            color = TextPrimary
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "ANSWER :: $answer",
            color = NeonGreen
        )

        Text(
            text =
                "CONFIDENCE :: $confidence%"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text =
                "MODELS USED :: $modelsUsed",
            color = NeonBlue
        )

        Text(
            text =
                "JUDGE USED :: $judgeUsed",
            color = NeonPurple
        )

        Spacer(
            modifier = Modifier.height(10.dp)
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
                    NeonGreen
        )
    }
}