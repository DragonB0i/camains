package com.example.trl.ocr

object OCRCleaner {

    private val garbage = listOf(
        "submit test",
        "search",
        "home",
        "cisco",
        "whatsapp",
        "whatsapp web",
        "scaler",
        "topics",
        "kali linux",
        "endobix",
        "logical reasoning",
        "verbal reasoning",
        "nonverbal reasoning",
        "verbal ability",
        "arithmetic aptitude",
        "data interpretation",
        "ethical hacking"
    )

    fun clean(raw: String): String {

        val lines = raw
            .lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }

        val result = mutableListOf<String>()

        var started = false

        for (line in lines) {

            val lower = line.lowercase()

            if (garbage.any { lower.contains(it) })
                continue

            if (!started) {

                if (Regex("^\\d+[.)]").containsMatchIn(line) ||
                    line.contains("?")
                ) {
                    started = true
                    result.add(line)
                }

                continue
            }

            if (Regex("^\\d+[.)]").containsMatchIn(line)
                && result.isNotEmpty()
            ) {
                break
            }

            result.add(line)
        }

        return result.joinToString("\n").trim()
    }
}