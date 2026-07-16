package com.example.trl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trl.camera.CameraPreview
import com.example.trl.camera.CameraPermission
import com.example.trl.ui.components.*
import com.example.trl.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {

    val vm: HomeViewModel = viewModel()

    val question by vm.recognizedText
    val answer by vm.aiAnswer
    val confidence by vm.confidence

    val modelsUsed by vm.modelsUsed
    val judgeUsed by vm.judgeUsed

    val frozen by vm.isFrozen

    val ocrStatus by vm.ocrStatus
    val aiStatus by vm.aiStatus

    CameraPermission {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            /*
             * CYBER GRID
             */

            CyberBackground()

            /*
             * CAMERA
             */

            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onTextDetected = {
                    vm.updateText(it)
                }
            )

            /*
             * OCR VISUALIZATION
             */

            HeatmapOverlay()

            FocusOverlay()

            /*
             * SCAN LINE
             */

            ScanLine()

            /*
             * DARK FILTER
             */

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Color.Black.copy(
                            alpha = 0.22f
                        )
                    )
            )

            /*
             * STATUS BAR
             */

            StatusBar(
                ocr = ocrStatus,
                ai = aiStatus
            )

            /*
             * CENTER HUD
             */

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    AnswerOverlay(
                        answer = answer,
                        confidence = confidence
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                20.dp
                            )
                    )

                    ConfidenceRing(
                        confidence
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                20.dp
                            )
                    )

                    ZoomIndicator()

                    Spacer(
                        modifier =
                            Modifier.height(
                                20.dp
                            )
                    )

                    FreezeBadge(
                        frozen
                    )
                }
            }

            /*
             * BOTTOM TERMINAL
             */

            Box(
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(16.dp)
                    .fillMaxWidth(
                        0.95f
                    )
            ) {

                BottomPanel(
                    question = question,
                    answer = answer,
                    confidence = confidence,
                    modelsUsed = modelsUsed,
                    judgeUsed = judgeUsed,
                    frozen = frozen
                )
            }
        }
    }
}