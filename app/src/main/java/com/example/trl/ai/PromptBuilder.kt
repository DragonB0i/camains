package com.example.trl.ai

object PromptBuilder {

    fun buildPrompt(question: String): String {

        return """
You are an expert at solving multiple-choice questions.

The OCR text has already been cleaned.

Your task:
1. Read the question.
2. Determine the correct answer.
3. Reply with ONLY ONE CAPITAL LETTER.

Allowed responses:
A
B
C
D

Do NOT explain.
Do NOT write words.
Do NOT write punctuation.
Return exactly one letter.

Question:
$question
        """.trimIndent()
    }
}