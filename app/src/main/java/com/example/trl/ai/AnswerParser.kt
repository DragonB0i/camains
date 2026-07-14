package com.example.trl.ai

object AnswerParser {

    fun parse(response: String): String {

        val cleaned = response
            .uppercase()
            .trim()

        // Exact response
        if (cleaned == "A" ||
            cleaned == "B" ||
            cleaned == "C" ||
            cleaned == "D") {
            return cleaned
        }

        // Standalone option
        Regex("\\b[A-D]\\b")
            .find(cleaned)
            ?.let {
                return it.value
            }

        // Any occurrence
        Regex("[A-D]")
            .find(cleaned)
            ?.let {
                return it.value
            }

        return "?"
    }
}