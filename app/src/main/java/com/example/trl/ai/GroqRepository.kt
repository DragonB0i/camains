package com.example.trl.ai

import android.util.Log
import com.example.trl.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class GroqRepository {

    private val client = OkHttpClient()

    suspend fun getAnswer(question: String): String {

        return withContext(Dispatchers.IO) {

            try {

                Log.d("AIQuizScanner", "========================")
                Log.d("AIQuizScanner", "Starting Groq Request")
                Log.d("AIQuizScanner", "Question:")
                Log.d("AIQuizScanner", question)

                val prompt = PromptBuilder.buildPrompt(question)

                Log.d("AIQuizScanner", "Prompt:")
                Log.d("AIQuizScanner", prompt)

                val json = JSONObject().apply {

                    put("model", "llama-3.3-70b-versatile")

                    put(
                        "messages",
                        JSONArray().put(
                            JSONObject()
                                .put("role", "user")
                                .put("content", prompt)
                        )
                    )

                    put("temperature", 0)

                    put("max_tokens", 5)
                }

                Log.d("AIQuizScanner", "Request JSON:")
                Log.d("AIQuizScanner", json.toString())

                val body = json.toString()
                    .toRequestBody("application/json".toMediaType())

                val request = Request.Builder()
                    .url("https://api.groq.com/openai/v1/chat/completions")
                    .addHeader(
                        "Authorization",
                        "Bearer ${BuildConfig.GROQ_API_KEY}"
                    )
                    .addHeader(
                        "Content-Type",
                        "application/json"
                    )
                    .post(body)
                    .build()

                Log.d("AIQuizScanner", "Sending HTTP request...")

                val response = client.newCall(request).execute()

                Log.d(
                    "AIQuizScanner",
                    "HTTP Code = ${response.code}"
                )

                val responseBody = response.body?.string() ?: ""

                Log.d(
                    "AIQuizScanner",
                    "Response Body:"
                )
                Log.d(
                    "AIQuizScanner",
                    responseBody
                )

                if (!response.isSuccessful) {

                    Log.e(
                        "AIQuizScanner",
                        "Groq request failed."
                    )

                    return@withContext "?"
                }

                val content =
                    JSONObject(responseBody)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")

                Log.d(
                    "AIQuizScanner",
                    "Raw AI Response = [$content]"
                )

                val parsed =
                    AnswerParser.parse(content)

                Log.d(
                    "AIQuizScanner",
                    "Parsed Answer = [$parsed]"
                )

                return@withContext parsed

            } catch (e: Exception) {

                Log.e(
                    "AIQuizScanner",
                    "Groq Exception",
                    e
                )

                return@withContext "?"
            }
        }
    }
}