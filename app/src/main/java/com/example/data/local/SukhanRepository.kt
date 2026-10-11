package com.example.data.local

import kotlinx.coroutines.flow.Flow

class SukhanRepository(
    private val dao: SukhanDao,
    val preferences: PreferencesManager
) {
    // Drafts
    val allDrafts: Flow<List<DraftEntity>> = dao.getAllDrafts()
    val favoriteDrafts: Flow<List<DraftEntity>> = dao.getFavoriteDrafts()
    val latestDraft: Flow<DraftEntity?> = dao.getLatestDraft()
    val draftCount: Flow<Int> = dao.getDraftCount()
    val totalWordsWritten: Flow<Int> = dao.getTotalWordsWritten()
    val savedVerseCount: Flow<Int> = dao.getSavedVerseCount()

    fun getDraftById(id: Long): Flow<DraftEntity?> = dao.getDraftById(id)
    suspend fun getDraftByIdOnce(id: Long): DraftEntity? = dao.getDraftByIdOnce(id)
    fun searchDrafts(query: String): Flow<List<DraftEntity>> = dao.searchDrafts(query)

    suspend fun saveDraft(draft: DraftEntity): Long {
        val wordCount = draft.content.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
        val charCount = draft.content.length
        val updatedDraft = draft.copy(
            wordCount = wordCount,
            characterCount = charCount,
            updatedTimestamp = System.currentTimeMillis()
        )
        return if (draft.id == 0L) {
            val newId = dao.insertDraft(updatedDraft)
            dao.insertSnapshot(
                DraftSnapshotEntity(
                    draftId = newId,
                    title = updatedDraft.title,
                    content = updatedDraft.content,
                    note = "Initial draft creation"
                )
            )
            newId
        } else {
            dao.updateDraft(updatedDraft)
            dao.insertSnapshot(
                DraftSnapshotEntity(
                    draftId = updatedDraft.id,
                    title = updatedDraft.title,
                    content = updatedDraft.content,
                    note = "Saved at ${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}"
                )
            )
            draft.id
        }
    }

    suspend fun deleteDraft(id: Long) = dao.deleteDraftById(id)

    suspend fun toggleFavoriteDraft(id: Long, currentFav: Boolean) =
        dao.setFavorite(id, !currentFav)

    // Snapshots
    fun getSnapshotsForDraft(draftId: Long): Flow<List<DraftSnapshotEntity>> =
        dao.getSnapshotsForDraft(draftId)

    // Saved Verses / Bookmarks
    val allSavedVerses: Flow<List<SavedVerseEntity>> = dao.getAllSavedVerses()

    fun isVerseSaved(text: String): Flow<Boolean> = dao.isVerseSaved(text)

    suspend fun saveVerse(verse: SavedVerseEntity): Long = dao.insertSavedVerse(verse)

    suspend fun deleteSavedVerse(id: Long) = dao.deleteSavedVerseById(id)

    suspend fun deleteSavedVerseByText(text: String) = dao.deleteSavedVerseByText(text)
}
