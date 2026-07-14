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
    val recognizedText: State<String> = _recognizedText

    private val _aiAnswer =
        mutableStateOf("...")
    val aiAnswer: State<String> = _aiAnswer

    private val _isLoading =
        mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private var debounceJob: Job? = null

    private var lastQuestionHash = 0

    fun updateText(question: String) {

        _recognizedText.value = question

        val normalized = normalize(question)

        if (normalized.isBlank())
            return

        if (_isLoading.value) {
            Logger.d("Groq Busy")
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

    private suspend fun askGroq(question: String) {

        _isLoading.value = true

        try {

            Logger.d("==============================")
            Logger.d("Sending to Groq")
            Logger.d(question)

            val answer = repository.getAnswer(question)

            Logger.d("Groq Answer = $answer")

            _aiAnswer.value = answer

        } catch (e: Exception) {

            Logger.e("Groq Error", e)

        } finally {

            _isLoading.value = false

        }
    }

    private fun normalize(text: String): String {

        return text
            .lowercase()
            .replace(Regex("[^a-z0-9 ]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }
}