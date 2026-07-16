package com.example.trl.processing

object OCRStabilityDetector {

    private var lastText = ""

    private var stableCount = 0

    private const val REQUIRED_STABILITY = 3

    fun shouldAccept(
        text: String
    ): Boolean {

        if (text.isBlank()) {
            stableCount = 0
            lastText = ""
            return false
        }

        if (
            text.equals(
                lastText,
                ignoreCase = true
            )
        ) {

            stableCount++

        } else {

            lastText = text

            stableCount = 1
        }

        return stableCount >=
                REQUIRED_STABILITY
    }

    fun reset() {

        stableCount = 0

        lastText = ""
    }

    fun getStableCount(): Int {

        return stableCount
    }
}