package com.example.trl.ocr

import android.graphics.Rect
import com.example.trl.camera.AutoZoomManager
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OCRProcessor {

    private val recognizer =
        TextRecognition.getClient(
            TextRecognizerOptions
                .DEFAULT_OPTIONS
        )

    private val TOP_CUTOFF =
        0.20f

    private val BOTTOM_CUTOFF =
        0.80f

    fun process(

        image: InputImage,

        onSuccess:
            (
            String,
            List<Rect>
        ) -> Unit

    ) {

        recognizer
            .process(image)

            .addOnSuccessListener {

                    result ->

                val filteredText =
                    applyRegionFilter(
                        result,
                        image.height
                    )

                val boxes =
                    result.textBlocks
                        .mapNotNull {
                            it.boundingBox
                        }

                OCRState.update(
                    boxes
                )

                AutoZoomManager
                    .update()

                onSuccess(
                    filteredText,
                    boxes
                )
            }

            .addOnFailureListener {

                onSuccess(
                    "",
                    emptyList()
                )
            }
    }

    private fun applyRegionFilter(
        result: Text,
        imageHeight: Int
    ): String {

        val top =
            (
                    imageHeight *
                            TOP_CUTOFF
                    ).toInt()

        val bottom =
            (
                    imageHeight *
                            BOTTOM_CUTOFF
                    ).toInt()

        val kept =
            mutableListOf<String>()

        result.textBlocks
            .forEach {

                val rect =
                    it.boundingBox
                        ?: return@forEach

                val center =
                    (
                            rect.top +
                                    rect.bottom
                            ) / 2

                if (

                    center >= top &&
                    center <= bottom

                ) {

                    kept.add(
                        it.text
                    )
                }
            }

        return kept
            .joinToString(
                "\n"
            )
    }
}