package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.AiResult
import com.example.data.ai.AiToolType
import com.example.data.ai.GeminiAiService
import com.example.data.catalog.TemplatesCatalog
import com.example.data.local.DraftEntity
import com.example.data.local.SukhanRepository
import com.example.ui.components.GlassCard
import com.example.ui.components.SukhanTopBar
import com.example.ui.components.isUrduArabicString
import com.example.ui.theme.SukhanBackground
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBorderSubtle
import com.example.ui.theme.SukhanBurgundy
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanGold
import com.example.ui.theme.SukhanGoldLight
import com.example.ui.theme.SukhanSerif
import com.example.ui.theme.SukhanSurface
import com.example.ui.theme.SukhanSurfaceElevated
import com.example.ui.theme.SukhanTextMuted
import com.example.ui.theme.SukhanTextPrimary
import com.example.ui.theme.SukhanTextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    repository: SukhanRepository,
    aiService: GeminiAiService,
    initialDraftId: Long? = null,
    initialPromptText: String? = null,
    onNavigateToCardStudio: (poemText: String, poetName: String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var draftId by remember { mutableStateOf(initialDraftId ?: 0L) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("Hinglish") }
    var selectedScript by remember { mutableStateOf("Hinglish") }
    var selectedGenre by remember { mutableStateOf("Ghazal") }
    var selectedMood by remember { mutableStateOf("Longing") }
    var tags by remember { mutableStateOf("") }
    var saveStatus by remember { mutableStateOf("Draft ready") }

    // Undo / Redo history
    val undoStack = remember { mutableStateListOf<String>() }
    val redoStack = remember { mutableStateListOf<String>() }

    // AI modal state
    var showAiSheet by remember { mutableStateOf(false) }
    var selectedAiTool by remember { mutableStateOf(AiToolType.NEXT_LINE) }
    var isAiLoading by remember { mutableStateOf(false) }
    var aiResultText by remember { mutableStateOf<String?>(null) }
    var aiErrorMessage by remember { mutableStateOf<String?>(null) }

    // Template picker dialog
    var showTemplateDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }

    // Load initial draft or prompt
    LaunchedEffect(initialDraftId) {
        if (initialDraftId != null && initialDraftId > 0L) {
            val loaded = repository.getDraftByIdOnce(initialDraftId)
            if (loaded != null) {
                draftId = loaded.id
                title = loaded.title
                content = loaded.content
                selectedLanguage = loaded.language
                selectedScript = loaded.script
                selectedGenre = loaded.genre
                selectedMood = loaded.mood
                tags = loaded.tags
                saveStatus = "Loaded saved draft"
            }
        } else if (!initialPromptText.isNullOrBlank()) {
            title = "Prompt: ${initialPromptText.take(24)}..."
            content = "# Prompt: $initialPromptText\n\n"
        }
    }

    // Debounced Autosave (saves every 3 seconds of pause if content changes)
    LaunchedEffect(title, content, selectedLanguage, selectedScript, selectedGenre, selectedMood) {
        if (content.isNotBlank() || title.isNotBlank()) {
            delay(3000)
            val updated = DraftEntity(
                id = draftId,
                title = title.ifBlank { "Untitled Verse" },
                content = content,
                language = selectedLanguage,
                script = selectedScript,
                genre = selectedGenre,
                mood = selectedMood,
                tags = tags
            )
            val savedId = repository.saveDraft(updated)
            draftId = savedId
            saveStatus = "Autosaved at ${java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}"
        }
    }

    fun pushHistory(newContent: String) {
        if (content != newContent) {
            undoStack.add(content)
            redoStack.clear()
            content = newContent
        }
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val previous = undoStack.removeAt(undoStack.size - 1)
            redoStack.add(content)
            content = previous
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val next = redoStack.removeAt(redoStack.size - 1)
            undoStack.add(content)
            content = next
        }
    }

    val wordCount = content.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
    val charCount = content.length
    val lineCount = if (content.isEmpty()) 0 else content.lines().size
    val isUrduRtl = selectedScript == "Nastaliq" || isUrduArabicString(content)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("write_screen")
    ) {
        // Top Bar
        SukhanTopBar(
            title = "Writing Studio",
            subtitle = saveStatus,
            onBackClick = onNavigateBack,
            actions = {
                // Undo
                IconButton(
                    onClick = { undo() },
                    enabled = undoStack.isNotEmpty(),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Undo,
                        contentDescription = "Undo",
                        tint = if (undoStack.isNotEmpty()) SukhanGold else SukhanTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Redo
                IconButton(
                    onClick = { redo() },
                    enabled = redoStack.isNotEmpty(),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Redo,
                        contentDescription = "Redo",
                        tint = if (redoStack.isNotEmpty()) SukhanGold else SukhanTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Save Explicit
                IconButton(
                    onClick = {
                        scope.launch {
                            val saved = repository.saveDraft(
                                DraftEntity(
                                    id = draftId,
                                    title = title.ifBlank { "Untitled Verse" },
                                    content = content,
                                    language = selectedLanguage,
                                    script = selectedScript,
                                    genre = selectedGenre,
                                    mood = selectedMood,
                                    tags = tags
                                )
                            )
                            draftId = saved
                            saveStatus = "Saved just now"
                            Toast.makeText(context, "Draft saved in Vault", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("write_save_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save Draft",
                        tint = SukhanGold,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // AI Assistant launcher button
                IconButton(
                    onClick = { showAiSheet = true },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("write_ai_assistant_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Assistant",
                        tint = SukhanGoldLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Script & Language Bar (Sirf Hinglish aur Hindi)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf("Hinglish (रोमन)", "Hindi (हिन्दी)").forEach { scriptOption ->
                    val scriptKey = if (scriptOption.startsWith("Hindi")) "Hindi" else "Hinglish"
                    val isSelected = selectedScript == scriptKey
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            selectedScript = scriptKey
                            selectedLanguage = if (scriptKey == "Hindi") "Hindi" else "Hinglish"
                        },
                        label = { Text(scriptOption, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
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

            Spacer(modifier = Modifier.height(8.dp))

            // Genre & Mood Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Shayari", "Ghazal", "Nazm", "Geet / Lyrics", "Dohe", "Couplet", "Kavita").forEach { genre ->
                    val isSelected = selectedGenre == genre
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedGenre = genre },
                        label = { Text(genre, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SukhanGold.copy(alpha = 0.2f),
                            selectedLabelColor = SukhanGoldLight,
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

            // Title Field
            BasicTextField(
                value = title,
                onValueChange = { title = it },
                textStyle = TextStyle(
                    fontFamily = SukhanSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = SukhanTextPrimary
                ),
                cursorBrush = SolidColor(SukhanGold),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("write_title_input"),
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text(
                            text = "Poem or Ghazal Title...",
                            style = TextStyle(
                                fontFamily = SukhanSerif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = SukhanTextMuted
                            )
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(thickness = 0.5.dp, color = SukhanBorder.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(12.dp))

            // Main Editor Field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SukhanSurface.copy(alpha = 0.5f))
                    .border(1.dp, SukhanBorderSubtle, RoundedCornerShape(12.dp))
                    .padding(14.dp)
            ) {
                BasicTextField(
                    value = content,
                    onValueChange = { pushHistory(it) },
                    textStyle = TextStyle(
                        fontFamily = SukhanSerif,
                        fontSize = 18.sp,
                        lineHeight = 32.sp,
                        color = SukhanTextPrimary,
                        textAlign = if (isUrduRtl) TextAlign.Right else TextAlign.Left
                    ),
                    cursorBrush = SolidColor(SukhanGold),
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("write_body_input"),
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()) {
                            Text(
                                text = "Pour your thoughts into verses...\n\n(Choose templates, rhymes, or AI guidance anytime)",
                                style = TextStyle(
                                    fontFamily = SukhanSerif,
                                    fontSize = 16.sp,
                                    lineHeight = 28.sp,
                                    color = SukhanTextMuted,
                                    textAlign = if (isUrduRtl) TextAlign.Right else TextAlign.Left
                                )
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Metrics Bar: Words, Characters, Lines
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$wordCount words • $charCount chars • $lineCount lines",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SukhanGoldLight,
                        fontSize = 11.sp
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Template Picker
                    OutlinedButton(
                        onClick = { showTemplateDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.testTag("write_template_button")
                    ) {
                        Text("Templates", fontSize = 11.sp, color = SukhanGold)
                    }

                    // Export / Share
                    OutlinedButton(
                        onClick = { showExportDialog = true },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.testTag("write_export_button")
                    ) {
                        Text("Export / Share", fontSize = 11.sp, color = SukhanGold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AI Quick Assistant Tray
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = SukhanGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "LITERARY AI ASSISTANT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SukhanGold,
                                    letterSpacing = 1.sp
                                )
                            )
                        }

                        Text(
                            text = "Replaceable API",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SukhanTextMuted,
                                fontSize = 10.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AiToolType.values().forEach { tool ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SukhanBurgundyDark.copy(alpha = 0.7f))
                                    .border(1.dp, SukhanBorder, RoundedCornerShape(8.dp))
                                    .clickable {
                                        selectedAiTool = tool
                                        showAiSheet = true
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tool.title,
                                    fontSize = 11.sp,
                                    color = SukhanTextPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Template Picker Dialog
    if (showTemplateDialog) {
        AlertDialog(
            onDismissRequest = { showTemplateDialog = false },
            title = {
                Text("Select Writing Template", color = SukhanGold, fontFamily = SukhanSerif)
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    TemplatesCatalog.templates.forEach { tmpl ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    pushHistory(
                                        if (content.isBlank()) tmpl.starterContent
                                        else "$content\n\n${tmpl.starterContent}"
                                    )
                                    selectedGenre = tmpl.genre
                                    showTemplateDialog = false
                                    Toast.makeText(context, "Inserted ${tmpl.title}", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 10.dp)
                        ) {
                            Text(tmpl.title, color = SukhanGoldLight, fontWeight = FontWeight.SemiBold)
                            Text(tmpl.description, color = SukhanTextSecondary, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(tmpl.structureGuide, color = SukhanTextMuted, fontSize = 11.sp)
                        }
                        HorizontalDivider(thickness = 0.5.dp, color = SukhanBorder.copy(alpha = 0.2f))
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTemplateDialog = false }) {
                    Text("Cancel", color = SukhanGold)
                }
            },
            containerColor = SukhanBackground
        )
    }

    // Export / Share Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = {
                Text("Export & Share", color = SukhanGold, fontFamily = SukhanSerif)
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Choose export or sharing format:",
                        color = SukhanTextSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 14.dp)
                    )

                    // 1. Copy text
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Sukhan Draft", "$title\n\n$content\n\n— via SUKHAN")
                            clipboard.setPrimaryClip(clip)
                            showExportDialog = false
                            Toast.makeText(context, "Copied full draft to clipboard", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SukhanSurfaceElevated),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = SukhanGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Copy to Clipboard", color = SukhanTextPrimary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. Share via Android Intent
                    Button(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_SUBJECT, title)
                                putExtra(Intent.EXTRA_TEXT, "$title\n\n$content\n\n— SUKHAN (by Prince Thakur)")
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share Draft"))
                            showExportDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SukhanSurfaceElevated),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = SukhanGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share via App Intent", color = SukhanTextPrimary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 3. Export to TXT / Markdown file locally
                    Button(
                        onClick = {
                            try {
                                val filename = "${(title.ifBlank { "draft" }).replace("\\s+".toRegex(), "_")}_${System.currentTimeMillis()}.txt"
                                val file = File(context.filesDir, filename)
                                FileOutputStream(file).use {
                                    it.write("$title\n\n$content\n\n— Written in SUKHAN".toByteArray())
                                }
                                showExportDialog = false
                                Toast.makeText(context, "Exported: ${file.name}", Toast.LENGTH_LONG).show()
                            } catch (e: Exception) {
                                Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SukhanSurfaceElevated),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = SukhanGold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Export to TXT & Markdown", color = SukhanTextPrimary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 4. Poetry Card Studio
                    Button(
                        onClick = {
                            showExportDialog = false
                            onNavigateToCardStudio(content, title.ifBlank { "Author" })
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SukhanBurgundyDark),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Palette, contentDescription = null, tint = SukhanGoldLight)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Open in Card Studio", color = SukhanGoldLight)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Close", color = SukhanGold)
                }
            },
            containerColor = SukhanBackground
        )
    }

    // AI Literary Assistant Bottom Sheet
    if (showAiSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showAiSheet = false },
            sheetState = sheetState,
            containerColor = SukhanBackground,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = selectedAiTool.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = SukhanGold,
                                fontFamily = SukhanSerif,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = selectedAiTool.description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SukhanTextSecondary,
                                fontSize = 12.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action to generate
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (content.isBlank()) {
                                Toast.makeText(context, "Write a line in your draft first!", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            isAiLoading = true
                            aiErrorMessage = null
                            aiResultText = null
                            scope.launch {
                                when (val result = aiService.executeAiTool(
                                    toolType = selectedAiTool,
                                    userPoemText = content,
                                    genre = selectedGenre,
                                    language = selectedLanguage,
                                    mood = selectedMood
                                )) {
                                    is AiResult.Success -> {
                                        aiResultText = result.responseText
                                        isAiLoading = false
                                    }
                                    is AiResult.MissingApiKey -> {
                                        aiErrorMessage = result.message
                                        isAiLoading = false
                                    }
                                    is AiResult.Error -> {
                                        aiErrorMessage = result.message
                                        isAiLoading = false
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SukhanGold,
                            contentColor = SukhanBackground
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("ai_generate_action_button")
                    ) {
                        if (isAiLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = SukhanBackground,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Consulting Ustaad...", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate Suggestion", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Error / Missing API Key Guidance Banner
                aiErrorMessage?.let { errMsg ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = SukhanBurgundyDark.copy(alpha = 0.9f),
                        borderColor = SukhanBurgundy
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Setup Notice",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = SukhanGoldLight,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = errMsg,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SukhanTextPrimary,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp
                                )
                            )
                        }
                    }
                }

                // AI Result Display (Non-Destructive!)
                aiResultText?.let { result ->
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = SukhanGold
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Literary Suggestion (Original draft untouched):",
                                style = MaterialTheme.typography.labelSmall.copy(color = SukhanGold)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = result,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = SukhanTextPrimary,
                                    fontFamily = SukhanSerif,
                                    lineHeight = 26.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Insert at End
                                Button(
                                    onClick = {
                                        pushHistory("$content\n\n$result")
                                        showAiSheet = false
                                        Toast.makeText(context, "Appended to your draft", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SukhanBurgundy),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Append to Draft", fontSize = 11.sp)
                                }

                                // Copy to Clipboard
                                OutlinedButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Sukhan AI Suggestion", result)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Copied suggestion", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Copy Text", fontSize = 11.sp, color = SukhanGold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
