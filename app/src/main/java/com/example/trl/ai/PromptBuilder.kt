package com.example.trl.ai

object PromptBuilder {

    fun buildPrompt(
        question: String
    ): String {

        val lower =
            question.lowercase()

        val isTrueFalse =
            lower.contains("true") &&
                    lower.contains("false")

        return if (isTrueFalse) {

            """
You are an expert at solving True/False questions.

Reply ONLY with:

A
or
B

Where:
A = True
B = False

Do NOT explain.
Return exactly one capital letter.

Question:
$question
            """.trimIndent()

        } else {

            """
You are an expert at solving multiple-choice questions.

The OCR has already been cleaned.

Your task:

1. Read the question.
2. Determine the correct answer.
3. Reply with ONLY ONE LETTER.

Allowed outputs:

A
B
C
D

If OCR is incomplete:
return X

DO NOT explain.
DO NOT use punctuation.
DO NOT write words.

Question:
$question
            """.trimIndent()
        }
    }
}