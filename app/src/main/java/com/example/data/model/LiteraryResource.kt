package com.example.data.model

data class LiteraryResource(
    val id: String,
    val category: String, // "Ghazal Structure", "Poetic Meter & Beher", "Vocabulary & Farhang", "Metaphors & Imagery", "Songwriting & Structures", "Literary Devices", "Exercises"
    val title: String,
    val subtitle: String,
    val explanation: String,
    val examples: List<String> = emptyList(),
    val keyRules: List<String> = emptyList(),
    val practicalTip: String = "",
    val downloadableTemplate: String = ""
)

data class WritingTemplate(
    val id: String,
    val title: String,
    val genre: String,
    val description: String,
    val structureGuide: String,
    val starterContent: String
)

data class MoodCategory(
    val id: String,
    val name: String,
    val nativeName: String, // Urdu/Hindi term e.g. "Ishq", "Firaaq"
    val description: String,
    val tagColorHex: Long,
    val keywords: List<String>,
    val writingPrompt: String
)

data class DailySher(
    val id: String,
    val poetName: String,
    val poetId: String,
    val originalText: String,
    val script: String = "Nastaliq",
    val transliteration: String,
    val translation: String,
    val mood: String,
    val context: String,
    val source: String
)
