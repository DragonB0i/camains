package com.example.trl.ocr

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.trl.ai.Logger
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalGetImage::class)
class OCRAnalyzer(
    private val onQuestionDetected: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val processor = OCRProcessor()

    private val duplicateFilter = DuplicateFilter()

    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy.image

        if (mediaImage == null) {
            imageProxy.close()
            return
        }

        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        processor.process(image) { raw ->

            try {

                Logger.d("==============================")
                Logger.d("RAW OCR")
                Logger.d(raw)

                val cleaned = OCRCleaner.clean(raw)

                Logger.d("------------------------------")
                Logger.d("CLEANED OCR")
                Logger.d(cleaned)

                if (cleaned.isBlank()) {

                    Logger.d("Blank OCR")

                    return@process
                }

                if (!duplicateFilter.shouldProcess(cleaned)) {

                    Logger.d("Duplicate OCR")

                    return@process
                }

                Logger.d("Sending OCR to ViewModel")

                onQuestionDetected(cleaned)

            } finally {

                imageProxy.close()

            }
        }
    }
}