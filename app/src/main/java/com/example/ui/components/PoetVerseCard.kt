package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.data.model.Poem
import com.example.ui.theme.SukhanBackground
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanGold
import com.example.ui.theme.SukhanGoldLight
import com.example.ui.theme.SukhanSerif
import com.example.ui.theme.SukhanSurfaceElevated
import com.example.ui.theme.SukhanTextMuted
import com.example.ui.theme.SukhanTextPrimary
import com.example.ui.theme.SukhanTextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PoetVerseCard(
    poem: Poem,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onOpenCardStudio: (Poem) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showTransliteration by remember { mutableStateOf(false) }
    var showTranslation by remember { mutableStateOf(false) }
    var showFarhangDialog by remember { mutableStateOf(false) }

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("verse_card_${poem.id}"),
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header: Poet Name, Verified badge & Genre
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = poem.poetName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = SukhanGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified cultural attribution",
                        tint = SukhanGold,
                        modifier = Modifier.size(15.dp)
                    )
                }

                // Genre / Mood Chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SukhanBurgundyDark.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "${poem.genre} • ${poem.mood}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanGoldLight,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Poem Title (if distinct)
            if (poem.title.isNotBlank()) {
                Text(
                    text = poem.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SukhanTextSecondary,
                        fontSize = 13.sp,
                        fontFamily = SukhanSerif
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Poem Main Text (Script formatted)
            ScriptText(
                text = poem.text,
                script = poem.script,
                fontSize = 20.sp,
                lineHeight = 34.sp,
                color = SukhanTextPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            // Optional Transliteration
            AnimatedVisibility(visible = showTransliteration && poem.transliteration.isNotBlank()) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "Transliteration:",
                        style = MaterialTheme.typography.labelSmall.copy(color = SukhanGold)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = poem.transliteration,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SukhanTextSecondary,
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            // Optional Translation
            AnimatedVisibility(visible = showTranslation && poem.translation.isNotBlank()) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = "Translation:",
                        style = MaterialTheme.typography.labelSmall.copy(color = SukhanGold)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = poem.translation,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SukhanTextSecondary,
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Toggles: Transliteration / Translation / Farhang (Word Meanings)
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (poem.transliteration.isNotBlank()) {
                    SuggestionChip(
                        onClick = { showTransliteration = !showTransliteration },
                        label = {
                            Text(
                                if (showTransliteration) "Hide Roman" else "Roman Script",
                                fontSize = 11.sp
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (showTransliteration) SukhanGold.copy(alpha = 0.2f) else SukhanSurfaceElevated,
                            labelColor = if (showTransliteration) SukhanGold else SukhanTextSecondary
                        ),
                        border = SuggestionChipDefaults.suggestionChipBorder(
                            enabled = true,
                            borderColor = SukhanBorder
                        )
                    )
                }

                if (poem.translation.isNotBlank()) {
                    SuggestionChip(
                        onClick = { showTranslation = !showTranslation },
                        label = {
                            Text(
                                if (showTranslation) "Hide Meaning" else "English Meaning",
                                fontSize = 11.sp
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (showTranslation) SukhanGold.copy(alpha = 0.2f) else SukhanSurfaceElevated,
                            labelColor = if (showTranslation) SukhanGold else SukhanTextSecondary
                        ),
                        border = SuggestionChipDefaults.suggestionChipBorder(
                            enabled = true,
                            borderColor = SukhanBorder
                        )
                    )
                }

                if (poem.difficultWords.isNotEmpty()) {
                    SuggestionChip(
                        onClick = { showFarhangDialog = true },
                        label = {
                            Text(
                                "Farhang (${poem.difficultWords.size} Words)",
                                fontSize = 11.sp
                            )
                        },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = SukhanBurgundyDark.copy(alpha = 0.5f),
                            labelColor = SukhanGoldLight
                        ),
                        border = SuggestionChipDefaults.suggestionChipBorder(
                            enabled = true,
                            borderColor = SukhanBorder
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Footer Actions: Copy, Share, Card Studio, Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = poem.source.take(28),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SukhanTextMuted,
                        fontSize = 11.sp
                    )
                )

                Row {
                    // Copy
                    IconButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText(
                                "Sukhan Poetry",
                                "${poem.text}\n\n— ${poem.poetName}\n(via SUKHAN • Prince Thakur)"
                            )
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Copied verse to clipboard", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy verse",
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
                                    "${poem.text}\n\n— ${poem.poetName}\n\nShared via SUKHAN — A home for words, poets & untold feelings.\nMade by Prince Thakur"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share Sher via"))
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share verse",
                            tint = SukhanTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Card Studio
                    IconButton(
                        onClick = { onOpenCardStudio(poem) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Create poetry card",
                            tint = SukhanGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Bookmark / Save
                    IconButton(
                        onClick = onToggleSave,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isSaved) "Saved in Vault" else "Save to Vault",
                            tint = if (isSaved) SukhanGold else SukhanTextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }

    // Farhang (Glossary) Dialog
    if (showFarhangDialog) {
        AlertDialog(
            onDismissRequest = { showFarhangDialog = false },
            title = {
                Text(
                    text = "Farhang (Vocabulary Gloss)",
                    style = MaterialTheme.typography.titleLarge.copy(color = SukhanGold)
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Explanations of classical words in this verse:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = SukhanTextSecondary),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    poem.difficultWords.forEach { (word, meaning) ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                text = word,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = SukhanGoldLight,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = meaning,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SukhanTextPrimary
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFarhangDialog = false }) {
                    Text("Close", color = SukhanGold)
                }
            },
            containerColor = SukhanBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}
