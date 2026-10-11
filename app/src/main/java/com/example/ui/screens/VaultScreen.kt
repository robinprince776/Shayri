package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DraftEntity
import com.example.data.local.DraftSnapshotEntity
import com.example.data.local.SavedVerseEntity
import com.example.data.local.SukhanRepository
import com.example.ui.components.GlassCard
import com.example.ui.components.ScriptText
import com.example.ui.components.SukhanTopBar
import com.example.ui.theme.SukhanBackground
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBurgundy
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanGold
import com.example.ui.theme.SukhanGoldLight
import com.example.ui.theme.SukhanSerif
import com.example.ui.theme.SukhanSurfaceElevated
import com.example.ui.theme.SukhanTextMuted
import com.example.ui.theme.SukhanTextPrimary
import com.example.ui.theme.SukhanTextSecondary
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VaultScreen(
    repository: SukhanRepository,
    onOpenDraft: (draftId: Long) -> Unit,
    onCreateNewDraft: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedFolder by remember { mutableStateOf("All") }

    val allDrafts by repository.allDrafts.collectAsState(initial = emptyList())
    val savedVerses by repository.allSavedVerses.collectAsState(initial = emptyList())
    val draftCount by repository.draftCount.collectAsState(initial = 0)
    val totalWords by repository.totalWordsWritten.collectAsState(initial = 0)

    // Snapshots inspection state
    var inspectingDraftId by remember { mutableStateOf<Long?>(null) }
    val inspectingSnapshots by repository.getSnapshotsForDraft(inspectingDraftId ?: -1L).collectAsState(initial = emptyList())

    // Import modal dialog
    var showImportDialog by remember { mutableStateOf(false) }
    var importTextContent by remember { mutableStateOf("") }
    var importTitle by remember { mutableStateOf("") }

    val filteredDrafts = remember(allDrafts, searchQuery, selectedFolder) {
        var list = allDrafts
        if (searchQuery.isNotBlank()) {
            val q = searchQuery.trim().lowercase()
            list = list.filter {
                it.title.lowercase().contains(q) ||
                it.content.lowercase().contains(q) ||
                it.tags.lowercase().contains(q) ||
                it.genre.lowercase().contains(q)
            }
        }
        if (selectedFolder != "All") {
            list = when (selectedFolder) {
                "Favorites" -> list.filter { it.isFavorite }
                else -> list.filter { it.folder.equals(selectedFolder, ignoreCase = true) || it.genre.equals(selectedFolder, ignoreCase = true) }
            }
        }
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("vault_screen")
    ) {
        SukhanTopBar(
            title = "My Vault",
            subtitle = "$draftCount Personal Drafts • $totalWords Words written",
            actions = {
                // Import
                IconButton(
                    onClick = { showImportDialog = true },
                    modifier = Modifier.testTag("vault_import_button")
                ) {
                    Icon(imageVector = Icons.Default.UploadFile, contentDescription = "Import file", tint = SukhanGold)
                }

                // New Draft
                IconButton(
                    onClick = onCreateNewDraft,
                    modifier = Modifier.testTag("vault_new_draft_button")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "New Draft", tint = SukhanGold)
                }
            }
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = SukhanSurfaceElevated,
            contentColor = SukhanGold,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = SukhanGold
                )
            }
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("Drafts (${filteredDrafts.size})", fontSize = 13.sp) }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text("Bookmarked Verses (${savedVerses.size})", fontSize = 13.sp) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search your private vault...", color = SukhanTextMuted, fontSize = 13.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = SukhanGold)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = SukhanTextSecondary)
                        }
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SukhanGold,
                    unfocusedBorderColor = SukhanBorder,
                    focusedTextColor = SukhanTextPrimary,
                    unfocusedTextColor = SukhanTextPrimary,
                    cursorColor = SukhanGold
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vault_search_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Tab 0: Personal Drafts
            if (selectedTabIndex == 0) {
                // Folder / Tag Filter Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Favorites", "Ghazals", "Nazm", "Lyrics", "General").forEach { folder ->
                        val isSelected = selectedFolder == folder
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFolder = folder },
                            label = { Text(folder, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SukhanBurgundyDark,
                                selectedLabelColor = SukhanGold,
                                containerColor = SukhanSurfaceElevated,
                                labelColor = SukhanTextSecondary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = SukhanBorder
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (filteredDrafts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = SukhanTextMuted, modifier = Modifier.size(40.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Your Vault is empty.", color = SukhanTextSecondary, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Drafts stay 100% private on your device.", color = SukhanTextMuted, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onCreateNewDraft,
                                colors = ButtonDefaults.buttonColors(containerColor = SukhanGold, contentColor = SukhanBackground)
                            ) {
                                Text("Create First Draft")
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredDrafts, key = { it.id }) { draft ->
                            DraftItemCard(
                                draft = draft,
                                onOpen = { onOpenDraft(draft.id) },
                                onToggleFavorite = {
                                    scope.launch {
                                        repository.toggleFavoriteDraft(draft.id, draft.isFavorite)
                                    }
                                },
                                onDelete = {
                                    scope.launch {
                                        repository.deleteDraft(draft.id)
                                        Toast.makeText(context, "Deleted draft", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                onViewHistory = {
                                    inspectingDraftId = draft.id
                                }
                            )
                        }

                        item { Spacer(modifier = Modifier.height(30.dp)) }
                    }
                }
            } else {
                // Tab 1: Bookmarked Verses
                Spacer(modifier = Modifier.height(8.dp))

                if (savedVerses.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No bookmarked verses yet. Tap the bookmark icon on any poet's work to save it here.", color = SukhanTextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(savedVerses, key = { it.id }) { saved ->
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = saved.poetName,
                                            style = MaterialTheme.typography.titleMedium.copy(color = SukhanGold, fontWeight = FontWeight.SemiBold)
                                        )

                                        IconButton(
                                            onClick = {
                                                scope.launch {
                                                    repository.deleteSavedVerse(saved.id)
                                                    Toast.makeText(context, "Removed from saved", Toast.LENGTH_SHORT).show()
                                                }
                                            },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SukhanTextMuted, modifier = Modifier.size(16.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    ScriptText(
                                        text = saved.verseText,
                                        script = saved.script,
                                        fontSize = 17.sp,
                                        lineHeight = 28.sp,
                                        color = SukhanTextPrimary
                                    )

                                    if (saved.translation.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = saved.translation,
                                            style = MaterialTheme.typography.bodyMedium.copy(color = SukhanTextSecondary, fontSize = 12.sp)
                                        )
                                    }
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(30.dp)) }
                    }
                }
            }
        }
    }

    // Snapshots / Revision History Dialog
    if (inspectingDraftId != null) {
        AlertDialog(
            onDismissRequest = { inspectingDraftId = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.History, contentDescription = null, tint = SukhanGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Revision History", color = SukhanGold, fontFamily = SukhanSerif)
                }
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    if (inspectingSnapshots.isEmpty()) {
                        Text("No snapshots recorded yet.", color = SukhanTextSecondary)
                    } else {
                        inspectingSnapshots.forEach { snap ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(
                                    text = SimpleDateFormat("MMM dd, yyyy • HH:mm:ss", Locale.getDefault()).format(Date(snap.timestamp)),
                                    style = MaterialTheme.typography.labelSmall.copy(color = SukhanGoldLight)
                                )
                                Text(text = snap.note, style = MaterialTheme.typography.bodySmall.copy(color = SukhanTextMuted))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = snap.content.take(120),
                                    style = MaterialTheme.typography.bodyMedium.copy(color = SukhanTextPrimary)
                                )
                            }
                            HorizontalDivider(thickness = 0.5.dp, color = SukhanBorder.copy(alpha = 0.3f))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { inspectingDraftId = null }) {
                    Text("Close", color = SukhanGold)
                }
            },
            containerColor = SukhanBackground
        )
    }

    // Import Draft Dialog
    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = {
                Text("Import TXT / MD File", color = SukhanGold, fontFamily = SukhanSerif)
            },
            text = {
                Column {
                    Text("Paste text or poetry notes from TXT/MD to create a private draft:", color = SukhanTextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = importTitle,
                        onValueChange = { importTitle = it },
                        placeholder = { Text("Draft Title (optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = importTextContent,
                        onValueChange = { importTextContent = it },
                        placeholder = { Text("Paste poem content here...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (importTextContent.isNotBlank()) {
                            scope.launch {
                                repository.saveDraft(
                                    DraftEntity(
                                        title = importTitle.ifBlank { "Imported Verse" },
                                        content = importTextContent,
                                        folder = "Imported"
                                    )
                                )
                                showImportDialog = false
                                Toast.makeText(context, "Draft imported to Vault", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SukhanGold, contentColor = SukhanBackground)
                ) {
                    Text("Save to Vault")
                }
            },
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text("Cancel", color = SukhanTextSecondary)
                }
            },
            containerColor = SukhanBackground
        )
    }
}

@Composable
fun DraftItemCard(
    draft: DraftEntity,
    onOpen: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit,
    onViewHistory: () -> Unit
) {
    val dateString = remember(draft.updatedTimestamp) {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(draft.updatedTimestamp))
    }

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .testTag("draft_item_${draft.id}"),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Text(
                        text = draft.title.ifBlank { "Untitled Verse" },
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = SukhanGold,
                            fontWeight = FontWeight.Bold,
                            fontFamily = SukhanSerif
                        ),
                        maxLines = 1
                    )
                }

                Row {
                    IconButton(onClick = onViewHistory, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.History, contentDescription = "History", tint = SukhanTextSecondary, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onToggleFavorite, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (draft.isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (draft.isFavorite) SukhanGold else SukhanTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = SukhanTextMuted, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = draft.content.ifBlank { "Empty draft..." },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SukhanTextPrimary,
                    lineHeight = 22.sp
                ),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${draft.genre} • ${draft.wordCount} words",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SukhanGoldLight,
                        fontSize = 11.sp
                    )
                )

                Text(
                    text = dateString,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SukhanTextMuted,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
