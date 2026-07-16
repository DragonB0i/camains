package com.example.trl.ocr

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.trl.ai.Logger
import com.example.trl.processing.OCRStabilityDetector
import com.example.trl.processing.QuestionExtractor
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalGetImage::class)
class OCRAnalyzer(
    private val onQuestionDetected:
        (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val processor =
        OCRProcessor()

    private val duplicateFilter =
        DuplicateFilter()

    override fun analyze(
        imageProxy: ImageProxy
    ) {

        val mediaImage =
            imageProxy.image

        if (mediaImage == null) {

            imageProxy.close()
            return
        }

        val image =
            InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

        processor.process(
            image
        ) { raw, _ ->

            val cleaned =
                OCRCleaner.clean(
                    raw
                )

            val extracted =
                QuestionExtractor.extract(
                    cleaned
                )

            if (
                extracted.isBlank()
            ) {

                OCRStabilityDetector
                    .reset()

                imageProxy.close()

                return@process
            }

            val stable =
                OCRStabilityDetector
                    .shouldAccept(
                        extracted
                    )

            if (!stable) {

                imageProxy.close()
                return@process
            }

            if (
                !duplicateFilter
                    .shouldProcess(
                        extracted
                    )
            ) {

                imageProxy.close()
                return@process
            }

            onQuestionDetected(
                extracted
            )

            OCRStabilityDetector
                .reset()

            imageProxy.close()
        }
    }
}