package com.example.trl.camera

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider

class CameraManager(
    private val context: Context
) {

    fun getCameraProvider() =
        ProcessCameraProvider.getInstance(context)

}