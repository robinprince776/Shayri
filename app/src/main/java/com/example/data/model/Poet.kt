package com.example.data.model

data class Poet(
    val id: String,
    val name: String,
    val originalName: String, // Urdu / Hindi / Gurmukhi script
    val transliteration: String,
    val era: String, // e.g. "1797 – 1869 (Mughal Era)"
    val movement: String, // "Dabistan-e-Delhi", "Progressive Writers' Movement", "Classical Sufi", "Bhakti"
    val biography: String,
    val majorWorks: List<String>,
    val languages: List<String>,
    val forms: List<String>,
    val sourceAttribution: String,
    val rightsStatus: String = "Public Domain (Verified Historic)",
    val relatedPoetIds: List<String> = emptyList(),
    val famousSher: String = ""
)
