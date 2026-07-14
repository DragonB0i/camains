package com.example.trl.ocr

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class OCRProcessor {

    private val recognizer =
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun process(
        image: InputImage,
        onSuccess: (String) -> Unit
    ) {

        recognizer.process(image)

            .addOnSuccessListener {

                onSuccess(it.text)

            }

            .addOnFailureListener {

                onSuccess("")

            }

    }

}