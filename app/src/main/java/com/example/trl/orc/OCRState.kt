package com.example.trl.ocr

import android.graphics.Rect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object OCRState {

    val boxes =
        mutableStateListOf<Rect>()

    val zoomText =
        mutableStateOf("1.0x")

    val confidenceColor =
        mutableStateOf(0)

    var zoomLevel = 0f

    fun update(
        newBoxes: List<Rect>
    ) {

        boxes.clear()

        boxes.addAll(
            newBoxes
        )

        val largest =
            newBoxes.maxByOrNull {

                it.width() *
                        it.height()
            }

        when {

            largest == null -> {

                zoomText.value =
                    "1.0x"

                confidenceColor.value =
                    0

                zoomLevel = 0f
            }

            largest.width() < 250 -> {

                zoomText.value =
                    "3.0x"

                confidenceColor.value =
                    3

                zoomLevel = 1f
            }

            largest.width() < 500 -> {

                zoomText.value =
                    "2.0x"

                confidenceColor.value =
                    2

                zoomLevel = 0.6f
            }

            else -> {

                zoomText.value =
                    "1.0x"

                confidenceColor.value =
                    1

                zoomLevel = 0f
            }
        }
    }
}