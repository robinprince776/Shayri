package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        DraftEntity::class,
        DraftSnapshotEntity::class,
        SavedVerseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SukhanDatabase : RoomDatabase() {

    abstract fun sukhanDao(): SukhanDao

    companion object {
        @Volatile
        private var INSTANCE: SukhanDatabase? = null

        fun getDatabase(context: Context): SukhanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SukhanDatabase::class.java,
                    "sukhan_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Seed initial starter draft and initial saved sher
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        seedInitialData(database.sukhanDao())
                    }
                }
            }
        }

        private suspend fun seedInitialData(dao: SukhanDao) {
            val starterDraft = DraftEntity(
                title = "चाँद का सफ़र (Chand Ka Safar)",
                content = """रात ख़ामोश है और तारा चमकता है
दिल के सहरा में कोई याद दहकता है

हमने हर मोड़ पे देखा है वफ़ा का रस्ता
तू जो मिल जाए तो फिर कौन बहकता है""".trimIndent(),
                language = "Hindi",
                script = "Hindi",
                genre = "Ghazal",
                mood = "Longing",
                tags = "Night, Ghazal, Starter",
                folder = "Ghazals",
                wordCount = 28,
                characterCount = 135,
                isFavorite = true,
                createdTimestamp = System.currentTimeMillis(),
                updatedTimestamp = System.currentTimeMillis()
            )
            val draftId = dao.insertDraft(starterDraft)
            dao.insertSnapshot(
                DraftSnapshotEntity(
                    draftId = draftId,
                    title = starterDraft.title,
                    content = starterDraft.content,
                    note = "Initial starter verse"
                )
            )

            // Seed an initial saved sher from Ghalib
            dao.insertSavedVerse(
                SavedVerseEntity(
                    poetId = "ghalib",
                    poetName = "Mirza Ghalib",
                    verseText = "हज़ारों ख़्वाहिशें ऐसी कि हर ख़्वाहिश पे दम निकले\nबहुत निकले मिरे अरमान लेकिन फिर भी कम निकले",
                    script = "Hindi",
                    transliteration = "Hazaron khwahishen aisi ki har khwahish pe dam nikle\nBahut nikle mire arman lekin phir bhi kam nikle",
                    translation = "Thousands of desires, each one worth dying for;\nMany longings fulfilled, yet so few they still seem.",
                    mood = "Philosophy",
                    source = "Diwan-e-Ghalib (Public Domain)",
                    notes = "Ghalib ka amar sher insaani khwahishon par."
                )
            )
        }
    }
}
