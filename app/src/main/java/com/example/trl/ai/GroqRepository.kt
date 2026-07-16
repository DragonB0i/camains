package com.example.trl.ai

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

    suspend fun getAnswer(
        question: String
    ): AIResult {

        return withContext(Dispatchers.IO) {

            try {

                Logger.d("========== AI VOTING ==========")

                //--------------------------------------------------
                // MODEL 1
                //--------------------------------------------------

                val llama = askModel(
                    question,
                    "llama-3.3-70b-versatile"
                )

                Logger.d("Llama -> $llama")

                //--------------------------------------------------
                // MODEL 2
                //--------------------------------------------------

                val deepSeek = askModel(
                    question,
                    "deepseek-r1-distill-llama-70b"
                )

                Logger.d("DeepSeek -> $deepSeek")

                //--------------------------------------------------
                // EARLY STOP
                //--------------------------------------------------

                if (
                    llama == deepSeek &&
                    llama != "?"
                ) {

                    Logger.d("Early agreement")
                    Logger.d("Skipping remaining models")

                    return@withContext AIResult(
                        answer = llama,
                        confidence = 98,
                        modelsUsed = 2,
                        judgeUsed = false
                    )
                }

                //--------------------------------------------------
                // MODEL 3
                //--------------------------------------------------

                val gemma = askModel(
                    question,
                    "gemma2-9b-it"
                )

                Logger.d("Gemma -> $gemma")

                //--------------------------------------------------
                // Majority Vote
                //--------------------------------------------------

                val votes = listOf(
                    llama,
                    deepSeek,
                    gemma
                )

                val counts =
                    votes.groupingBy { it }
                        .eachCount()

                val winner =
                    counts.maxByOrNull {
                        it.value
                    }!!

                //--------------------------------------------------
                // 2/3 Majority
                //--------------------------------------------------

                if (
                    winner.value >= 2 &&
                    winner.key != "?"
                ) {

                    Logger.d(
                        "Majority Winner -> ${winner.key}"
                    )

                    return@withContext AIResult(

                        answer = winner.key,

                        confidence = 90,

                        modelsUsed = 3,

                        judgeUsed = false
                    )
                }

                //--------------------------------------------------
                // Judge Model
                //--------------------------------------------------

                Logger.d("Calling Judge Model")

                val judge = askModel(

                    question,

                    "qwen/qwen3-32b"

                )

                Logger.d("Judge -> $judge")

                return@withContext AIResult(

                    answer = judge,

                    confidence = 75,

                    modelsUsed = 4,

                    judgeUsed = true

                )

            }

            catch (e: Exception) {

                Logger.e(
                    "Voting Error",
                    e
                )

                AIResult(

                    answer = "?",

                    confidence = 0,

                    modelsUsed = 0,

                    judgeUsed = false

                )
            }
        }
    }

    private fun askModel(

        question: String,

        model: String

    ): String {

        return try {

            val prompt =
                PromptBuilder.buildPrompt(question)

            val json =
                JSONObject().apply {

                    put(
                        "model",
                        model
                    )

                    put(

                        "messages",

                        JSONArray().put(

                            JSONObject()

                                .put(
                                    "role",
                                    "user"
                                )

                                .put(
                                    "content",
                                    prompt
                                )

                        )

                    )

                    put(
                        "temperature",
                        0
                    )

                    put(
                        "max_tokens",
                        5
                    )
                }

            val body =
                json.toString()
                    .toRequestBody(
                        "application/json"
                            .toMediaType()
                    )

            val request =
                Request.Builder()

                    .url(
                        "https://api.groq.com/openai/v1/chat/completions"
                    )

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

            val response =
                client.newCall(request)
                    .execute()

            if (!response.isSuccessful)
                return "?"

            val bodyText =
                response.body?.string()
                    ?: ""

            val content =
                JSONObject(bodyText)

                    .getJSONArray("choices")

                    .getJSONObject(0)

                    .getJSONObject("message")

                    .getString("content")

            return AnswerParser.parse(content)

        }

        catch (e: Exception) {

            Logger.e(
                model,
                e
            )

            return "?"
        }
    }
}