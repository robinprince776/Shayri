package com.example.data.model

data class Poem(
    val id: String,
    val poetId: String,
    val poetName: String,
    val title: String,
    val text: String,
    val script: String = "Nastaliq", // Nastaliq, Devanagari, Gurmukhi, Roman
    val transliteration: String = "",
    val translation: String = "",
    val genre: String = "Ghazal", // Ghazal, Nazm, Rubai, Kafi, Doha, Couplet
    val mood: String = "Love", // Love, Heartbreak, Longing, Loneliness, Nostalgia, Rebellion, Hope, Philosophy, Self-Respect, Healing
    val radif: String = "",
    val qafiya: String = "",
    val beher: String = "",
    val difficultWords: Map<String, String> = emptyMap(), // Farhang: word to meaning
    val tashreeh: String = "", // Detailed explanation & historical context
    val source: String = "",
    val rightsInfo: String = "Public domain / Authorized cultural archival",
    val isVerified: Boolean = true
)
