package com.example.trl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.trl.ui.theme.CardColor
import com.example.trl.ui.theme.NeonBlue

@Composable
fun NeonCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Card(
        modifier = modifier
            .shadow(
                20.dp,
                RoundedCornerShape(24.dp)
            )
            .border(
                2.dp,
                NeonBlue,
                RoundedCornerShape(24.dp)
            )
    ) {

        Box(
            modifier = Modifier
                .background(CardColor)
                .padding(20.dp)
        ) {
            content()
        }
    }
}