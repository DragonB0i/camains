package com.example.trl.camera

import androidx.camera.core.Camera

object CameraController {

    var camera: Camera? = null

    fun zoom(
        level: Float
    ) {

        camera
            ?.cameraControl
            ?.setLinearZoom(level)
    }
}