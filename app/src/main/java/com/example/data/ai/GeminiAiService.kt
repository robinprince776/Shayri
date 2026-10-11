package com.example.data.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

sealed class AiResult {
    data class Success(val responseText: String, val isLiveApi: Boolean = true) : AiResult()
    data class MissingApiKey(val message: String) : AiResult()
    data class Error(val message: String) : AiResult()
}

enum class AiToolType(val title: String, val description: String) {
    NEXT_LINE("Next-Line Suggestion", "Generate a poetic Misra-e-Sani that matches your meter and rhyme"),
    RHYME_VOCAB("Rhymes & Qafiya", "Find rich rhyming words and literary vocabulary for your current line"),
    METAPHORS("Metaphor Generation", "Create evocative metaphors (Tashbih) and imagery (Isti'ara)"),
    LINE_IMPROVEMENT("Refine & Polish", "Enhance flow, musicality, and poetic diction while preserving your voice"),
    TRANSLATION("Poetic Translation", "Translate between Urdu, Hindi, Punjabi, and English poetically"),
    LITERARY_FEEDBACK("Taqti & Critique", "Receive meter analysis, emotional resonance critique, and structural notes"),
    SONG_STRUCTURE("Song Structure", "Expand verses into complete Mukhda, Antra, and Hook structures")
}

class GeminiAiService(
    private val customApiKeyProvider: () -> String = { "" }
) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun getEffectiveApiKey(): String {
        val userKey = customApiKeyProvider().trim()
        if (userKey.isNotEmpty()) return userKey
        return try {
            val field = BuildConfig::class.java.getField("GEMINI_API_KEY")
            val key = field.get(null) as? String ?: ""
            if (key.isNotBlank() && !key.contains("MY_GEMINI_API_KEY")) key else ""
        } catch (e: Throwable) {
            ""
        }
    }

    suspend fun executeAiTool(
        toolType: AiToolType,
        userPoemText: String,
        genre: String,
        language: String,
        mood: String
    ): AiResult = withContext(Dispatchers.IO) {
        val apiKey = getEffectiveApiKey()
        if (apiKey.isEmpty()) {
            return@withContext AiResult.MissingApiKey(
                "Gemini API key is not configured. Please add GEMINI_API_KEY in the AI Studio Secrets panel or enter your personal key in Sukhan Settings to enable real-time literary AI."
            )
        }

        val prompt = buildPrompt(toolType, userPoemText, genre, language, mood)

        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val payload = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)

                val genConfig = JSONObject().apply {
                    put("temperature", 0.7)
                    put("maxOutputTokens", 1024)
                }
                put("generationConfig", genConfig)
            }

            val requestBody = payload.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext AiResult.Error("API call failed (${response.code}): ${parseErrorMessage(responseBody)}")
            }

            val json = JSONObject(responseBody)
            val candidates = json.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val resultText = parts.getJSONObject(0).optString("text", "")
                    return@withContext AiResult.Success(resultText.trim(), isLiveApi = true)
                }
            }
            AiResult.Error("Empty response received from the literary model.")
        } catch (e: Exception) {
            AiResult.Error("Network error: ${e.localizedMessage ?: "Unable to connect to AI service"}")
        }
    }

    private fun parseErrorMessage(body: String): String {
        return try {
            val json = JSONObject(body)
            val err = json.optJSONObject("error")
            err?.optString("message", body) ?: body
        } catch (e: Exception) {
            body.take(150)
        }
    }

    private fun buildPrompt(
        toolType: AiToolType,
        text: String,
        genre: String,
        language: String,
        mood: String
    ): String {
        val baseContext = """
            You are a master literary scholar, poet, and Ustaad of Urdu, Hindi, Punjabi, and Hindustani poetry for the premium app SUKHAN (by Prince Thakur).
            The user is working on a $genre in $language, carrying the emotional mood of $mood.
            
            Current Draft / Lines:
            \"\"\"
            $text
            \"\"\"
            
        """.trimIndent()

        return when (toolType) {
            AiToolType.NEXT_LINE -> """
                $baseContext
                TASK: Suggest 3 distinct, beautiful next-line options (Misra-e-Sani) that naturally complete the couplet or stanza.
                - Match the rhythm, Beher (poetic meter), and Qafiya/Radif if applicable.
                - Provide options in the original script ($language) along with Roman transliteration and meaning in English.
                - Format clearly with Option 1, Option 2, Option 3.
            """.trimIndent()

            AiToolType.RHYME_VOCAB -> """
                $baseContext
                TASK: Analyze the ending words and suggest:
                1. 5 to 7 rich rhyming words (Qafiya) with their precise meanings.
                2. 4 elevated literary vocabulary words (Farhang) that fit the $mood theme and $genre.
                Include original script and Roman pronunciation.
            """.trimIndent()

            AiToolType.METAPHORS -> """
                $baseContext
                TASK: Create 4 fresh, evocative poetic metaphors (Tashbeeh and Isti'ara) specifically tailored to elevate the core emotion of this piece.
                Avoid clichés (do not just use candle/moth); invent striking, sensory images suited for modern literary sensibilities.
            """.trimIndent()

            AiToolType.LINE_IMPROVEMENT -> """
                $baseContext
                TASK: Offer gentle line improvement suggestions.
                - Point out any awkward phrasing or meter stumble without changing the author's original soul and voice.
                - Provide 2 refined variations of the weakest line with clear reasoning for the change.
            """.trimIndent()

            AiToolType.TRANSLATION -> """
                $baseContext
                TASK: Provide a poetic, sensitive translation of this text into English (or into Urdu/Hindi if the text is in English).
                Preserve the lyrical cadence and metaphorical depth rather than a mechanical literal word-for-word translation.
            """.trimIndent()

            AiToolType.LITERARY_FEEDBACK -> """
                $baseContext
                TASK: Provide expert Ustaad literary feedback:
                1. Musicality & Meter (Wazn / Beher evaluation)
                2. Emotional resonance & imagery
                3. One specific actionable suggestion to make this piece unforgettable.
            """.trimIndent()

            AiToolType.SONG_STRUCTURE -> """
                $baseContext
                TASK: Organize and expand these lyrics into a professional musical structure:
                - Mukhda (Chorus / Hook)
                - Antra 1 (Verse 1)
                - Bridge / Transition
                - Climax & Return to Mukhda
            """.trimIndent()
        }
    }
}
