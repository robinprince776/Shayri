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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun PoetDetailScreen(
    poetId: String,
    repository: SukhanRepository,
    onBackClick: () -> Unit,
    onSelectRelatedPoet: (String) -> Unit,
    onOpenCardStudio: (Poem) -> Unit
) {
    val scope = rememberCoroutineScope()
    val poet = remember(poetId) { PoetsCatalog.getPoetById(poetId) }
    val poems = remember(poetId) { PoetsCatalog.getPoemsForPoet(poetId) }
    val savedVerses by repository.allSavedVerses.collectAsState(initial = emptyList())

    if (poet == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SukhanBackground)
        ) {
            SukhanTopBar(title = "Poet Not Found", onBackClick = onBackClick)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Poet profile could not be found.", color = SukhanTextSecondary)
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("poet_detail_screen")
    ) {
        SukhanTopBar(
            title = poet.name,
            subtitle = poet.transliteration,
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(6.dp)) }

            // Hero Profile Header
            item {
                LuxuryGradientCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = poet.name,
                                    style = MaterialTheme.typography.displayMedium.copy(
                                        color = SukhanGold,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = SukhanSerif
                                    )
                                )
                                Text(
                                    text = poet.originalName,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        color = SukhanGoldLight,
                                        fontFamily = SukhanSerif
                                    )
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified attribution",
                                tint = SukhanGold,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "${poet.era} • ${poet.movement}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SukhanTextSecondary,
                                fontSize = 13.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(thickness = 0.5.dp, color = SukhanBorder)
                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = poet.biography,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SukhanTextPrimary,
                                lineHeight = 26.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Major Works
                        Text(
                            text = "MAJOR WORKS:",
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
                            poet.majorWorks.forEach { work ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SukhanBurgundyDark)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = work,
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

            // Verified Works Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VERIFIED ASH'AAR & GHAZALS (${poems.size})",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanGold,
                            letterSpacing = 1.2.sp
                        )
                    )
                    Text(
                        text = poet.rightsStatus,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanTextMuted,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            // List of verified poems
            items(poems, key = { it.id }) { poem ->
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

            // Related Poets
            if (poet.relatedPoetIds.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            text = "RELATED CONTEMPORARIES & MASTERS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanGold,
                                letterSpacing = 1.2.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            poet.relatedPoetIds.forEach { relId ->
                                val relPoet = PoetsCatalog.getPoetById(relId)
                                if (relPoet != null) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(SukhanSurfaceElevated)
                                            .border(1.dp, SukhanBorder, RoundedCornerShape(12.dp))
                                            .clickable { onSelectRelatedPoet(relPoet.id) }
                                            .padding(horizontal = 14.dp, vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = relPoet.name,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                color = SukhanGoldLight,
                                                fontSize = 13.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Source Attribution Footer
            item {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = SukhanTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Attribution: ${poet.sourceAttribution}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanTextMuted,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}
