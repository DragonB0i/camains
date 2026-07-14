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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trl.camera.CameraPermission
import com.example.trl.camera.CameraPreview
import com.example.trl.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {

    val vm: HomeViewModel = viewModel()

    val text by vm.recognizedText
    val answer by vm.aiAnswer
    val loading by vm.isLoading

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

            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text("OCR")
                    Spacer(Modifier.height(8.dp))
                    Text(text)

                    Spacer(Modifier.height(20.dp))

                    Text("AI Answer")
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = answer,
                        color = Color.Green
                    )

                    Spacer(Modifier.height(20.dp))

                    if (loading) {
                        Text("Thinking...")
                    }

                }

            }

        }

    }

}