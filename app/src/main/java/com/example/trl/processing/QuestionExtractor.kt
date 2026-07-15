package com.example.trl.processing

object QuestionExtractor {

    fun extract(text: String): String {

        val lines = text.lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }

        val result = mutableListOf<String>()

        var started = false
        var optionCount = 0

        for (line in lines) {

            val lower = line.lowercase()

            val garbage = listOf(
                "search",
                "submit",
                "home",
                "whatsapp",
                "cisco",
                "scaler",
                "topics",
                "linux",
                "kali",
                "facebook",
                "instagram",
                "youtube"
            )

            if (garbage.any { lower.contains(it) }) {
                continue
            }

            val looksLikeQuestion =

                line.contains("?") ||

                        line.matches(
                            Regex(
                                "^q\\d+.*",
                                RegexOption.IGNORE_CASE
                            )
                        ) ||

                        line.matches(
                            Regex(
                                "^question\\s*\\d+.*",
                                RegexOption.IGNORE_CASE
                            )
                        ) ||

                        line.matches(
                            Regex("^\\d+[.)].*")
                        ) ||

                        lower.startsWith("what") ||
                        lower.startsWith("which") ||
                        lower.startsWith("who") ||
                        lower.startsWith("where") ||
                        lower.startsWith("when") ||
                        lower.startsWith("how") ||
                        lower.startsWith("find")

            if (looksLikeQuestion) {
                started = true
            }

            if (!started) {
                continue
            }

            result.add(line)

            if (
                line.matches(
                    Regex(
                        "^[ABCD][.)]?.*",
                        RegexOption.IGNORE_CASE
                    )
                )
            ) {
                optionCount++
            }

            if (
                lower.contains("true") &&
                lower.contains("false")
            ) {
                optionCount = 2
            }

            if (optionCount >= 4) {
                break
            }
        }

        val extracted =
            result.joinToString("\n").trim()

        return if (
            extracted.length < 15
        ) {
            "SKIP"
        } else {
            extracted
        }
    }
}