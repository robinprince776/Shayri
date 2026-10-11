package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.DailySherCatalog
import com.example.data.catalog.MoodsCatalog
import com.example.data.catalog.PoetsCatalog
import com.example.data.catalog.ResourcesCatalog
import com.example.data.local.DraftEntity
import com.example.data.local.SavedVerseEntity
import com.example.data.local.SukhanRepository
import com.example.data.model.DailySher
import com.example.data.model.Poem
import com.example.ui.components.GlassCard
import com.example.ui.components.LuxuryGradientCard
import com.example.ui.components.ScriptText
import com.example.ui.components.SukhanTopBar
import com.example.ui.theme.SukhanBackground
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBorderSubtle
import com.example.ui.theme.SukhanBurgundy
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanGold
import com.example.ui.theme.SukhanGoldLight
import com.example.ui.theme.SukhanSerif
import com.example.ui.theme.SukhanSurfaceElevated
import com.example.ui.theme.SukhanTextMuted
import com.example.ui.theme.SukhanTextPrimary
import com.example.ui.theme.SukhanTextSecondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun HomeScreen(
    repository: SukhanRepository,
    onNavigateToWrite: (draftId: Long?, promptText: String?) -> Unit,
    onNavigateToPoets: () -> Unit,
    onNavigateToPoetDetail: (poetId: String) -> Unit,
    onNavigateToMood: (moodId: String) -> Unit,
    onNavigateToResources: () -> Unit,
    onNavigateToCardStudio: (poemText: String, poetName: String) -> Unit
) {
    val context = LocalContext.current
    val latestDraft by repository.latestDraft.collectAsState(initial = null)
    val savedVerses by repository.allSavedVerses.collectAsState(initial = emptyList())
    val todaySher = remember { DailySherCatalog.getTodaySher() }
    val isSherSaved by repository.isVerseSaved(todaySher.originalText).collectAsState(initial = false)

    // Android TTS for pronouncing / reciting verse
    var tts: TextToSpeech? by remember { mutableStateOf(null) }
    DisposableEffect(context) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("ur") // Try Urdu or default
            }
        }
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("home_screen")
    ) {
        SukhanTopBar(
            title = "SUKHAN",
            subtitle = "A home for words, poets & untold feelings",
            actions = {
                // Quick Write Action Button
                IconButton(
                    onClick = { onNavigateToWrite(null, null) },
                    modifier = Modifier.testTag("home_quick_write_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Start Writing",
                        tint = SukhanGold
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { Spacer(modifier = Modifier.height(6.dp)) }

            // 1. Quick Action Hero Banner: Start Writing & Explore Poets
            item {
                LuxuryGradientCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "LITERARY SANCTUARY",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SukhanGold,
                                    letterSpacing = 1.5.sp
                                )
                            )
                            Text(
                                text = "सुख़न",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = SukhanGoldLight,
                                    fontFamily = SukhanSerif
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Unfold your thoughts into timeless verse.",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = SukhanTextPrimary,
                                fontFamily = SukhanSerif,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { onNavigateToWrite(null, null) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SukhanGold,
                                    contentColor = SukhanBackground
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("home_start_writing_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Start Writing", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }

                            OutlinedButton(
                                onClick = onNavigateToPoets,
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = SukhanGoldLight
                                ),
                                border = ButtonDefaults.outlinedButtonBorder.copy(
                                    brush = androidx.compose.ui.graphics.SolidColor(SukhanBorder)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("home_explore_poets_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoStories,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Explore Poets", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // 2. Continue Draft (if exists)
            item {
                latestDraft?.let { draft ->
                    Column {
                        Text(
                            text = "CONTINUE YOUR DRAFT",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onNavigateToWrite(draft.id, null) }
                                .testTag("continue_draft_card")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = draft.title.ifBlank { "Untitled Verse" },
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = SukhanTextPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = draft.content.lineSequence().firstOrNull()?.take(40) ?: "Tap to resume writing...",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SukhanTextSecondary
                                        ),
                                        maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "${draft.genre} • ${draft.wordCount} words • Saved locally",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SukhanGoldLight,
                                            fontSize = 11.sp
                                        )
                                    )
                                }

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Resume draft",
                                    tint = SukhanGold,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 3. Daily Sher (Authentic & Verified)
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SHER OF THE DAY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Text(
                            text = "Verified Classical",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanTextMuted,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("daily_sher_card"),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = todaySher.poetName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = SukhanGold,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(SukhanBurgundyDark)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = todaySher.mood,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SukhanGoldLight,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            ScriptText(
                                text = todaySher.originalText,
                                script = todaySher.script,
                                fontSize = 21.sp,
                                lineHeight = 36.sp,
                                color = SukhanTextPrimary,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = todaySher.translation,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SukhanTextSecondary,
                                    lineHeight = 22.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = todaySher.source,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SukhanTextMuted,
                                        fontSize = 11.sp
                                    )
                                )

                                Row {
                                    // Audio TTS recitation
                                    IconButton(
                                        onClick = {
                                            tts?.speak(
                                                todaySher.originalText,
                                                TextToSpeech.QUEUE_FLUSH,
                                                null,
                                                "daily_sher"
                                            )
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.VolumeUp,
                                            contentDescription = "Recite verse",
                                            tint = SukhanTextSecondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Copy
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText(
                                                "Sukhan Sher",
                                                "${todaySher.originalText}\n\n— ${todaySher.poetName}\n${todaySher.translation}"
                                            )
                                            clipboard.setPrimaryClip(clip)
                                            Toast.makeText(context, "Copied sher to clipboard", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy sher",
                                            tint = SukhanTextSecondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Share
                                    IconButton(
                                        onClick = {
                                            val sendIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(
                                                    Intent.EXTRA_TEXT,
                                                    "${todaySher.originalText}\n\n— ${todaySher.poetName}\n${todaySher.translation}\n\nShared via SUKHAN • Made by Prince Thakur"
                                                )
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(sendIntent, "Share Daily Sher"))
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share sher",
                                            tint = SukhanTextSecondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Card Studio
                                    IconButton(
                                        onClick = {
                                            onNavigateToCardStudio(todaySher.originalText, todaySher.poetName)
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Palette,
                                            contentDescription = "Open in Card Studio",
                                            tint = SukhanGold,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Bookmark
                                    IconButton(
                                        onClick = {
                                            CoroutineScope(Dispatchers.IO).launch {
                                                if (isSherSaved) {
                                                    repository.deleteSavedVerseByText(todaySher.originalText)
                                                } else {
                                                    repository.saveVerse(
                                                        SavedVerseEntity(
                                                            poetId = todaySher.poetId,
                                                            poetName = todaySher.poetName,
                                                            verseText = todaySher.originalText,
                                                            transliteration = todaySher.transliteration,
                                                            translation = todaySher.translation,
                                                            mood = todaySher.mood,
                                                            source = todaySher.source
                                                        )
                                                    )
                                                }
                                            }
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isSherSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = "Bookmark",
                                            tint = if (isSherSaved) SukhanGold else SukhanTextSecondary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Writing Prompt of the Day
            item {
                Column {
                    Text(
                        text = "WRITING PROMPT",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanGold,
                            letterSpacing = 1.2.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = SukhanGold.copy(alpha = 0.4f)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = SukhanGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Tonight's Muse",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = SukhanGold,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            val promptText = "Write about the silence that speaks louder than an unanswered letter..."
                            Text(
                                text = "“$promptText”",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = SukhanTextPrimary,
                                    fontFamily = SukhanSerif
                                )
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { onNavigateToWrite(null, promptText) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SukhanBurgundy,
                                    contentColor = SukhanTextPrimary
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.testTag("home_prompt_write_button")
                            ) {
                                Text("Write on this Prompt", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // 5. Explore by Mood (Horizontal Chips)
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "EXPLORE BY MOOD",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.2.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(MoodsCatalog.moods) { mood ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(SukhanSurfaceElevated)
                                    .border(1.dp, SukhanBorder, RoundedCornerShape(14.dp))
                                    .clickable { onNavigateToMood(mood.id) }
                                    .testTag("mood_chip_${mood.id}")
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = mood.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = SukhanTextPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = mood.nativeName,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SukhanGoldLight,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Featured Poets Showcase
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "POETS' ARCHIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Text(
                            text = "View All →",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.clickable { onNavigateToPoets() }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(PoetsCatalog.poets.take(5)) { poet ->
                            GlassCard(
                                modifier = Modifier
                                    .width(220.dp)
                                    .clickable { onNavigateToPoetDetail(poet.id) }
                                    .testTag("poet_preview_${poet.id}"),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = poet.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = SukhanGold,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Text(
                                        text = poet.originalName,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SukhanTextSecondary,
                                            fontFamily = SukhanSerif
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = poet.era,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SukhanTextMuted,
                                            fontSize = 11.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = poet.famousSher.lineSequence().firstOrNull() ?: "",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SukhanTextPrimary,
                                            fontFamily = SukhanSerif
                                        ),
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 7. Recommended Literary Resources
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LITERARY RESOURCE CENTRE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Text(
                            text = "Explore Guides →",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.clickable { onNavigateToResources() }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    ResourcesCatalog.resources.take(3).forEach { res ->
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onNavigateToResources() }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    tint = SukhanGold,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = res.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = SukhanTextPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Text(
                                        text = res.subtitle,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SukhanTextSecondary,
                                            fontSize = 12.sp
                                        ),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 8. Creator Credit Tribute Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "SUKHAN",
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = SukhanGold,
                                letterSpacing = 3.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Made with devotion by Prince Thakur",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanTextMuted,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}
