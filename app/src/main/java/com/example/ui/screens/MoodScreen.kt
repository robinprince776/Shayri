package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.MoodsCatalog
import com.example.data.catalog.PoetsCatalog
import com.example.data.local.SavedVerseEntity
import com.example.data.local.SukhanRepository
import com.example.data.model.Poem
import com.example.ui.components.GlassCard
import com.example.ui.components.LuxuryGradientCard
import com.example.ui.components.PoetVerseCard
import com.example.ui.components.SukhanTopBar
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MoodScreen(
    initialMoodId: String = "love",
    repository: SukhanRepository,
    onBackClick: () -> Unit,
    onNavigateToWriteWithPrompt: (prompt: String) -> Unit,
    onOpenCardStudio: (Poem) -> Unit
) {
    val scope = rememberCoroutineScope()
    var selectedMoodId by remember { mutableStateOf(initialMoodId) }
    val selectedMood = remember(selectedMoodId) {
        MoodsCatalog.getMoodById(selectedMoodId) ?: MoodsCatalog.moods.first()
    }

    val matchingPoems = remember(selectedMoodId) {
        PoetsCatalog.poems.filter { it.mood.equals(selectedMood.name, ignoreCase = true) }
    }

    val savedVerses by repository.allSavedVerses.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("mood_screen")
    ) {
        SukhanTopBar(
            title = "Mood Discovery",
            subtitle = selectedMood.name,
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(6.dp)) }

            // Mood Selector Carousel
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(MoodsCatalog.moods) { mood ->
                        val isSelected = mood.id == selectedMoodId
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) SukhanBurgundyDark else SukhanSurfaceElevated)
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) SukhanGold else SukhanBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedMoodId = mood.id }
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Column {
                                Text(
                                    text = mood.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = if (isSelected) SukhanGold else SukhanTextPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = mood.nativeName,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SukhanGoldLight,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Selected Mood Essence Card
            item {
                LuxuryGradientCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedMood.name,
                                style = MaterialTheme.typography.displayMedium.copy(
                                    color = SukhanGold,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = SukhanSerif
                                )
                            )
                            Text(
                                text = selectedMood.nativeName,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = SukhanGoldLight,
                                    fontFamily = SukhanSerif
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = selectedMood.description,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SukhanTextPrimary,
                                lineHeight = 24.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Curated Vocabulary / Keywords
                        Text(
                            text = "CURATED VOCABULARY & IMAGERY:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            selectedMood.keywords.forEach { kw ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SukhanBurgundyDark)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = kw,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SukhanGoldLight,
                                            fontSize = 12.sp,
                                            fontFamily = SukhanSerif
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Writing Prompt for this mood
            item {
                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = SukhanGold.copy(alpha = 0.5f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = SukhanGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Prompt for ${selectedMood.name}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = SukhanGold,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "“${selectedMood.writingPrompt}”",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SukhanTextPrimary,
                                fontFamily = SukhanSerif,
                                lineHeight = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { onNavigateToWriteWithPrompt(selectedMood.writingPrompt) },
                            colors = ButtonDefaults.buttonColors(containerColor = SukhanGold, contentColor = SukhanBackground),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Create, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Write Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            // Recommended Verified Works
            item {
                Text(
                    text = "VERIFIED MASTERWORKS FOR THIS MOOD (${matchingPoems.size})",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SukhanGold,
                        letterSpacing = 1.2.sp
                    )
                )
            }

            if (matchingPoems.isEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                            Text("Explore related works across our Poets Library.", color = SukhanTextSecondary)
                        }
                    }
                }
            } else {
                items(matchingPoems, key = { it.id }) { poem ->
                    val isSaved = savedVerses.any { it.verseText == poem.text }
                    PoetVerseCard(
                        poem = poem,
                        isSaved = isSaved,
                        onToggleSave = {
                            scope.launch {
                                if (isSaved) {
                                    repository.deleteSavedVerseByText(poem.text)
                                } else {
                                    repository.saveVerse(
                                        SavedVerseEntity(
                                            poetId = poem.poetId,
                                            poetName = poem.poetName,
                                            verseText = poem.text,
                                            transliteration = poem.transliteration,
                                            translation = poem.translation,
                                            mood = poem.mood,
                                            source = poem.source
                                        )
                                    )
                                }
                            }
                        },
                        onOpenCardStudio = onOpenCardStudio
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}
