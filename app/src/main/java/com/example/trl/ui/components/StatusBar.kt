package com.example.trl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.trl.ui.theme.NeonBlue
import com.example.trl.ui.theme.NeonPurple

@Composable
fun StatusBar(
    ocr: String,
    ai: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "OCR :: $ocr",
            color = NeonBlue,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "AI :: $ai",
            color = NeonPurple,
            fontWeight = FontWeight.Bold
        )
    }
}