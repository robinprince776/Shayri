package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SukhanDao {

    // --- Drafts ---
    @Query("SELECT * FROM drafts ORDER BY updatedTimestamp DESC")
    fun getAllDrafts(): Flow<List<DraftEntity>>

    @Query("SELECT * FROM drafts WHERE isFavorite = 1 ORDER BY updatedTimestamp DESC")
    fun getFavoriteDrafts(): Flow<List<DraftEntity>>

    @Query("SELECT * FROM drafts ORDER BY updatedTimestamp DESC LIMIT 1")
    fun getLatestDraft(): Flow<DraftEntity?>

    @Query("SELECT * FROM drafts WHERE id = :id LIMIT 1")
    fun getDraftById(id: Long): Flow<DraftEntity?>

    @Query("SELECT * FROM drafts WHERE id = :id LIMIT 1")
    suspend fun getDraftByIdOnce(id: Long): DraftEntity?

    @Query("""
        SELECT * FROM drafts 
        WHERE title LIKE '%' || :query || '%' 
           OR content LIKE '%' || :query || '%' 
           OR tags LIKE '%' || :query || '%' 
           OR genre LIKE '%' || :query || '%'
        ORDER BY updatedTimestamp DESC
    """)
    fun searchDrafts(query: String): Flow<List<DraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: DraftEntity): Long

    @Update
    suspend fun updateDraft(draft: DraftEntity)

    @Query("DELETE FROM drafts WHERE id = :id")
    suspend fun deleteDraftById(id: Long)

    @Query("UPDATE drafts SET isFavorite = :isFav WHERE id = :id")
    suspend fun setFavorite(id: Long, isFav: Boolean)

    @Query("SELECT COUNT(*) FROM drafts")
    fun getDraftCount(): Flow<Int>

    @Query("SELECT COALESCE(SUM(wordCount), 0) FROM drafts")
    fun getTotalWordsWritten(): Flow<Int>

    // --- Snapshots / Revisions ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnapshot(snapshot: DraftSnapshotEntity): Long

    @Query("SELECT * FROM draft_snapshots WHERE draftId = :draftId ORDER BY timestamp DESC")
    fun getSnapshotsForDraft(draftId: Long): Flow<List<DraftSnapshotEntity>>

    // --- Saved Verses / Bookmarks ---
    @Query("SELECT * FROM saved_verses ORDER BY timestamp DESC")
    fun getAllSavedVerses(): Flow<List<SavedVerseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedVerse(verse: SavedVerseEntity): Long

    @Query("DELETE FROM saved_verses WHERE id = :id")
    suspend fun deleteSavedVerseById(id: Long)

    @Query("DELETE FROM saved_verses WHERE verseText = :text")
    suspend fun deleteSavedVerseByText(text: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_verses WHERE verseText = :text LIMIT 1)")
    fun isVerseSaved(text: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM saved_verses")
    fun getSavedVerseCount(): Flow<Int>
}
