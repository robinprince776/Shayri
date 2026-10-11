package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drafts")
data class DraftEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val language: String = "Hinglish", // Hinglish, Hindi
    val script: String = "Hindi", // Hindi, Hinglish
    val genre: String = "Shayari", // Shayari, Ghazal, Nazm, Punjabi Lyrics, Hindi Lyrics, Urdu Poetry, Couplet, Free Verse
    val mood: String = "Love", // Love, Heartbreak, Longing, Nostalgia, Rebellion, Hope, Philosophy, Healing
    val tags: String = "", // Comma-separated tags
    val folder: String = "General",
    val wordCount: Int = 0,
    val characterCount: Int = 0,
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val updatedTimestamp: Long = System.currentTimeMillis()
)
