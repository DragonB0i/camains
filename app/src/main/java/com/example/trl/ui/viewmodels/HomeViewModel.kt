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

    // NEW
    private val _modelsUsed =
        mutableStateOf(0)

    val modelsUsed: State<Int> =
        _modelsUsed

    // NEW
    private val _judgeUsed =
        mutableStateOf(false)

    val judgeUsed: State<Boolean> =
        _judgeUsed

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

        val normalized = normalize(question)

        if (normalized.isBlank()) {
            return
        }

        debounceJob?.cancel()

        debounceJob = viewModelScope.launch {

            delay(800)

            val hash = normalized.hashCode()

            if (hash == lastQuestionHash) {

                Logger.d("Duplicate Question")

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

            Logger.d("Groq already running")

            return
        }

        try {

            isRequestRunning = true

            _isLoading.value = true

            _aiStatus.value = "THINKING..."

            Logger.d("======================")

            Logger.d("Sending to Groq")

            Logger.d(question)

            val result =
                repository.getAnswer(question)

            Logger.d(
                "Answer = ${result.answer}"
            )

            Logger.d(
                "Confidence = ${result.confidence}"
            )

            Logger.d(
                "Models Used = ${result.modelsUsed}"
            )

            Logger.d(
                "Judge Used = ${result.judgeUsed}"
            )

            _aiAnswer.value =
                result.answer

            _confidence.value =
                result.confidence

            _modelsUsed.value =
                result.modelsUsed

            _judgeUsed.value =
                result.judgeUsed

            _aiStatus.value = "DONE"

            activateFreezeMode()

        }

        catch (e: Exception) {

            Logger.e(
                "Groq Error",
                e
            )

            _aiAnswer.value = "?"

            _confidence.value = 0

            _modelsUsed.value = 0

            _judgeUsed.value = false

            _aiStatus.value = "ERROR"
        }

        finally {

            isRequestRunning = false

            _isLoading.value = false
        }
    }

    private fun activateFreezeMode() {

        _isFrozen.value = true

        _ocrStatus.value = "FROZEN"

        viewModelScope.launch {

            delay(5000)

            _isFrozen.value = false

            _ocrStatus.value = "ACTIVE"
        }
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