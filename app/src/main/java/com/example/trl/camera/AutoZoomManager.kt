package com.example.trl.camera

import com.example.trl.ocr.OCRState

object AutoZoomManager {

    private var lastZoom = -1f

    fun update() {

        val zoom =
            OCRState.zoomLevel

        if (zoom == lastZoom) {
            return
        }

        lastZoom = zoom

        CameraController.zoom(
            zoom
        )
    }
}