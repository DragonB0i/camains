package com.example.trl.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trl.ai.GroqRepository
import com.example.trl.ai.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = GroqRepository()

    private val _recognizedText =
        mutableStateOf("Waiting for question...")

    val recognizedText: State<String> =
        _recognizedText

    private val _aiAnswer =
        mutableStateOf("?")

    val aiAnswer: State<String> =
        _aiAnswer

    private val _confidence =
        mutableStateOf(0)

    val confidence: State<Int> =
        _confidence

    private val _isLoading =
        mutableStateOf(false)

    val isLoading: State<Boolean> =
        _isLoading

    private val _ocrStatus =
        mutableStateOf("ACTIVE")

    val ocrStatus: State<String> =
        _ocrStatus

    private val _aiStatus =
        mutableStateOf("IDLE")

    val aiStatus: State<String> =
        _aiStatus

    private val _isFrozen =
        mutableStateOf(false)

    val isFrozen: State<Boolean> =
        _isFrozen

    private var debounceJob: Job? = null

    private var lastQuestionHash = 0

    private var isRequestRunning = false

    fun updateText(question: String) {

        if (_isFrozen.value) {
            Logger.d("Freeze Mode Active")
            return
        }

        if (question == "SKIP") {
            Logger.d("Non-MCQ ignored")
            return
        }

        _recognizedText.value = question

        val normalized =
            normalize(question)

        if (normalized.isBlank()) {
            return
        }

        debounceJob?.cancel()

        debounceJob = viewModelScope.launch {

            delay(800)

            val hash =
                normalized.hashCode()

            if (hash == lastQuestionHash) {

                Logger.d(
                    "Duplicate Question"
                )

                return@launch
            }

            lastQuestionHash = hash

            askGroq(question)
        }
    }

    private suspend fun askGroq(
        question: String
    ) {

        if (isRequestRunning) {

            Logger.d(
                "Groq already running"
            )

            return
        }

        try {

            isRequestRunning = true

            _isLoading.value = true

            _aiStatus.value =
                "THINKING..."

            Logger.d(
                "======================"
            )

            Logger.d(
                "Sending to Groq"
            )

            Logger.d(question)

            val answer =
                repository.getAnswer(
                    question
                )

            Logger.d(
                "Groq Answer = $answer"
            )

            _aiAnswer.value =
                answer

            _confidence.value =
                calculateConfidence(
                    question
                )

            _aiStatus.value =
                "DONE"

            activateFreezeMode()
        }

        catch (e: Exception) {

            Logger.e(
                "Groq Error",
                e
            )

            _aiAnswer.value = "?"

            _confidence.value = 0

            _aiStatus.value =
                "ERROR"
        }

        finally {

            isRequestRunning =
                false

            _isLoading.value =
                false
        }
    }

    private fun activateFreezeMode() {

        _isFrozen.value = true

        _ocrStatus.value =
            "FROZEN"

        viewModelScope.launch {

            delay(5000)

            _isFrozen.value =
                false

            _ocrStatus.value =
                "ACTIVE"
        }
    }

    private fun calculateConfidence(
        text: String
    ): Int {

        var score = 40

        val optionCount =
            Regex(
                "[A-D][.)]",
                RegexOption.IGNORE_CASE
            )
                .findAll(text)
                .count()

        score += optionCount * 10

        if (text.contains("?")) {
            score += 15
        }

        if (text.length > 60) {
            score += 15
        }

        return score.coerceIn(
            0,
            100
        )
    }

    private fun normalize(
        text: String
    ): String {

        return text
            .lowercase()
            .replace(
                Regex("[^a-z0-9 ]"),
                ""
            )
            .replace(
                Regex("\\s+"),
                " "
            )
            .trim()
    }
}