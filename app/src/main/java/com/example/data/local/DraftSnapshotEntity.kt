package com.example.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "draft_snapshots",
    foreignKeys = [
        ForeignKey(
            entity = DraftEntity::class,
            parentColumns = ["id"],
            childColumns = ["draftId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["draftId"])]
)
data class DraftSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val draftId: Long,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val note: String = "Autosaved snapshot"
)
