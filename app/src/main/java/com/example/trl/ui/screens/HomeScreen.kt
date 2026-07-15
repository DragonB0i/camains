package com.example.trl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trl.camera.CameraPermission
import com.example.trl.camera.CameraPreview
import com.example.trl.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {

    val vm: HomeViewModel = viewModel()

    val question by vm.recognizedText
    val answer by vm.aiAnswer
    val confidence by vm.confidence
    val frozen by vm.isFrozen
    val ocrStatus by vm.ocrStatus
    val aiStatus by vm.aiStatus

    CameraPermission {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onTextDetected = {
                    vm.updateText(it)
                }
            )

            /*
             * TOP STATUS
             */

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "OCR : $ocrStatus",
                    color = Color.White
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "AI : $aiStatus",
                    color = Color.White
                )
            }

            /*
             * HUGE ANSWER OVERLAY
             */

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.55f)
                        )
                        .padding(
                            horizontal = 40.dp,
                            vertical = 20.dp
                        )
                ) {

                    Text(
                        text = answer,
                        fontSize = 100.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (answer) {

                            "A" -> Color.Green

                            "B" -> Color.Blue

                            "C" -> Color(0xFFFFA500)

                            "D" -> Color.Red

                            else -> Color.White
                        }
                    )
                }
            }

            /*
             * BOTTOM PANEL
             */

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "QUESTION",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = question
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "Confidence : $confidence%"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = if (frozen) {
                            "FREEZE MODE"
                        } else {
                            "LIVE OCR"
                        },
                        color = if (frozen) {
                            Color.Red
                        } else {
                            Color.Green
                        }
                    )
                }
            }
        }
    }

}
