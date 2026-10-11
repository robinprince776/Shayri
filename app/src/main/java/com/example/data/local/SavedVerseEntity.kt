package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_verses")
data class SavedVerseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val poetId: String,
    val poetName: String,
    val verseText: String,
    val script: String = "Nastaliq",
    val transliteration: String = "",
    val translation: String = "",
    val mood: String = "",
    val source: String = "",
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
