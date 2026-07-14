package com.example.trl.ocr

internal class DuplicateFilter {

    private var lastText = ""

    fun shouldProcess(current: String): Boolean {

        if (current.isBlank()) {
            return false
        }

        val normalizedCurrent = normalize(current)
        val normalizedLast = normalize(lastText)

        if (normalizedCurrent == normalizedLast) {
            return false
        }

        lastText = current
        return true
    }

    private fun normalize(text: String): String {

        return text
            .lowercase()
            .replace(Regex("[^a-z0-9 ]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}