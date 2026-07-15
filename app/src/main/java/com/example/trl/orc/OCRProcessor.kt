package com.example.trl.ocr

import android.graphics.Rect
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OCRProcessor {

    private val recognizer =
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )

    private val TOP_CUTOFF = 0.20f
    private val BOTTOM_CUTOFF = 0.80f

    fun process(
        image: InputImage,
        onSuccess: (String) -> Unit
    ) {

        recognizer.process(image)

            .addOnSuccessListener { result ->

                logBoundingBoxes(result)

                val filteredText =
                    applyRegionFilter(
                        result,
                        image.height
                    )

                onSuccess(filteredText)
            }

            .addOnFailureListener {

                Log.e(
                    "AIQuizScanner",
                    "OCR Failed",
                    it
                )

                onSuccess("")
            }
    }

    private fun applyRegionFilter(
        result: Text,
        imageHeight: Int
    ): String {

        val top =
            (imageHeight * TOP_CUTOFF).toInt()

        val bottom =
            (imageHeight * BOTTOM_CUTOFF).toInt()

        Log.d(
            "AIQuizScanner",
            "======== REGION FILTER ========"
        )

        Log.d(
            "AIQuizScanner",
            "TOP    = $top"
        )

        Log.d(
            "AIQuizScanner",
            "BOTTOM = $bottom"
        )

        val keptBlocks =
            mutableListOf<String>()

        result.textBlocks.forEach { block ->

            val rect =
                block.boundingBox

            if (rect == null)
                return@forEach

            val centerY =
                (rect.top + rect.bottom) / 2

            if (
                centerY >= top &&
                centerY <= bottom
            ) {

                Log.d(
                    "AIQuizScanner",
                    "KEEPING BLOCK"
                )

                Log.d(
                    "AIQuizScanner",
                    block.text
                )

                keptBlocks.add(
                    block.text
                )

            } else {

                Log.d(
                    "AIQuizScanner",
                    "REMOVING BLOCK"
                )

                Log.d(
                    "AIQuizScanner",
                    block.text
                )
            }
        }

        Log.d(
            "AIQuizScanner",
            "==============================="
        )

        return keptBlocks
            .joinToString("\n")
    }

    private fun logBoundingBoxes(
        result: Text
    ) {

        Log.d(
            "AIQuizScanner",
            "========== BOUNDING BOXES =========="
        )

        result.textBlocks.forEachIndexed { blockIndex, block ->

            Log.d(
                "AIQuizScanner",
                "BLOCK #$blockIndex"
            )

            Log.d(
                "AIQuizScanner",
                "TEXT : ${block.text}"
            )

            logRect(
                block.boundingBox
            )

            block.lines.forEachIndexed { lineIndex, line ->

                Log.d(
                    "AIQuizScanner",
                    "LINE #$lineIndex"
                )

                Log.d(
                    "AIQuizScanner",
                    line.text
                )

                logRect(
                    line.boundingBox
                )

                line.elements.forEachIndexed { elementIndex, element ->

                    Log.d(
                        "AIQuizScanner",
                        "ELEMENT #$elementIndex"
                    )

                    Log.d(
                        "AIQuizScanner",
                        element.text
                    )

                    logRect(
                        element.boundingBox
                    )
                }
            }
        }

        Log.d(
            "AIQuizScanner",
            "===================================="
        )
    }

    private fun logRect(
        rect: Rect?
    ) {

        if (rect == null) {

            Log.d(
                "AIQuizScanner",
                "BoundingBox = NULL"
            )

            return
        }

        Log.d(
            "AIQuizScanner",
            "Rect(" +
                    "${rect.left}, " +
                    "${rect.top}, " +
                    "${rect.right}, " +
                    "${rect.bottom})"
        )
    }
}