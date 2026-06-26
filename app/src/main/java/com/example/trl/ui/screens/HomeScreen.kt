package com.example.trl.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.trl.camera.CameraPermission
import com.example.trl.camera.CameraPreview

@Composable
fun HomeScreen() {

    CameraPermission {

        CameraPreview(
            modifier = Modifier.fillMaxSize()
        )

    }

}