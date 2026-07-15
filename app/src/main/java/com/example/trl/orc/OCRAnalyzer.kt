package com.example.trl.ocr

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.trl.ai.Logger
import com.example.trl.processing.QuestionExtractor
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

            Logger.d("RAW OCR")
            Logger.d(raw)

            val cleaned =
                OCRCleaner.clean(raw)

            val extracted =
                QuestionExtractor.extract(cleaned)

            Logger.d("QUESTION")
            Logger.d(extracted)

            if (extracted.isBlank()) {

                Logger.d("Blank OCR")

                imageProxy.close()
                return@process
            }

            if (!duplicateFilter.shouldProcess(extracted)) {

                Logger.d("Duplicate OCR")

                imageProxy.close()
                return@process
            }

            Logger.d("Sending OCR to ViewModel")

            onQuestionDetected(extracted)

            imageProxy.close()
        }
    }
}