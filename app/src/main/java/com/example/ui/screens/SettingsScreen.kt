package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.GeminiAiService
import com.example.data.local.SukhanRepository
import com.example.ui.components.GlassCard
import com.example.ui.components.LuxuryGradientCard
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    repository: SukhanRepository,
    aiService: GeminiAiService
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val draftCount by repository.draftCount.collectAsState(initial = 0)
    val totalWords by repository.totalWordsWritten.collectAsState(initial = 0)
    val savedVerseCount by repository.savedVerseCount.collectAsState(initial = 0)

    val defaultScript by repository.preferences.defaultScript.collectAsState(initial = "Nastaliq")
    val isRtlEnabled by repository.preferences.isRtlEnabled.collectAsState(initial = true)
    val customApiKey by repository.preferences.customApiKey.collectAsState(initial = "")
    val penName by repository.preferences.penName.collectAsState(initial = "Prince Thakur")
    val isWatermarkEnabled by repository.preferences.isWatermarkEnabled.collectAsState(initial = true)

    var inputApiKey by remember(customApiKey) { mutableStateOf(customApiKey) }
    var inputPenName by remember(penName) { mutableStateOf(penName) }

    val effectiveApiKey = aiService.getEffectiveApiKey()
    val isAiConfigured = effectiveApiKey.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("settings_screen")
    ) {
        SukhanTopBar(
            title = "Profile & Settings",
            subtitle = "Made by Prince Thakur"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // 1. Creator Profile Tribute Card
            LuxuryGradientCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .border(1.5.dp, SukhanGold, CircleShape)
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = SukhanGold,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "SUKHAN",
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = SukhanGold,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp,
                            fontFamily = SukhanSerif
                        )
                    )

                    Text(
                        text = "A home for words, poets & untold feelings.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SukhanGoldLight,
                            fontFamily = SukhanSerif,
                            fontSize = 13.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SukhanBurgundyDark)
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Created by Prince Thakur",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = SukhanGoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Sukhan was born out of profound reverence for classical and modern Urdu, Punjabi, and Hindi literature. Dedicated to poets, dreamers, and seekers who find solace in the architecture of words.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SukhanTextSecondary,
                            lineHeight = 22.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2. Personal Vault Statistics
            Text(
                text = "VAULT STATISTICS",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SukhanGold,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCard(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$draftCount", style = MaterialTheme.typography.displayMedium.copy(color = SukhanGold, fontWeight = FontWeight.Bold))
                        Text(text = "Drafts Saved", style = MaterialTheme.typography.labelSmall.copy(color = SukhanTextSecondary))
                    }
                }

                GlassCard(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$totalWords", style = MaterialTheme.typography.displayMedium.copy(color = SukhanGold, fontWeight = FontWeight.Bold))
                        Text(text = "Words Written", style = MaterialTheme.typography.labelSmall.copy(color = SukhanTextSecondary))
                    }
                }

                GlassCard(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$savedVerseCount", style = MaterialTheme.typography.displayMedium.copy(color = SukhanGold, fontWeight = FontWeight.Bold))
                        Text(text = "Saved Verses", style = MaterialTheme.typography.labelSmall.copy(color = SukhanTextSecondary))
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Literary & Script Preferences
            Text(
                text = "LITERARY PREFERENCES",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SukhanGold,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Author Pen Name
                    OutlinedTextField(
                        value = inputPenName,
                        onValueChange = {
                            inputPenName = it
                            scope.launch { repository.preferences.setPenName(it) }
                        },
                        label = { Text("Author Pen Name (Takhallus)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SukhanGold,
                            unfocusedBorderColor = SukhanBorder,
                            focusedTextColor = SukhanTextPrimary,
                            unfocusedTextColor = SukhanTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Preferred Language & Script
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Primary Script: Hindi & Hinglish", color = SukhanTextPrimary, fontWeight = FontWeight.SemiBold)
                            Text("All shers, drafts and cards formatted in Hindi and Hinglish", color = SukhanTextSecondary, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(thickness = 0.5.dp, color = SukhanBorder.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(12.dp))

                    // Watermark Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Card Studio Watermark", color = SukhanTextPrimary, fontWeight = FontWeight.SemiBold)
                            Text("Display 'SUKHAN • Prince Thakur' on exported cards", color = SukhanTextSecondary, fontSize = 12.sp)
                        }
                        Switch(
                            checked = isWatermarkEnabled,
                            onCheckedChange = { scope.launch { repository.preferences.setWatermarkEnabled(it) } },
                            colors = SwitchDefaults.colors(checkedThumbColor = SukhanGold, checkedTrackColor = SukhanBurgundyDark)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4. Gemini AI Configuration
            Text(
                text = "GEMINI LITERARY AI INTEGRATION",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SukhanGold,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Key,
                            contentDescription = null,
                            tint = if (isAiConfigured) SukhanGold else SukhanGoldLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isAiConfigured) "AI Status: Active & Connected" else "AI Status: Key Not Configured",
                            color = if (isAiConfigured) SukhanGold else SukhanTextSecondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Keys are securely accessed via BuildConfig (AI Studio Secrets) or you can enter a personal Gemini 3.5 Flash API Key below:",
                        color = SukhanTextMuted,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = inputApiKey,
                        onValueChange = { inputApiKey = it },
                        placeholder = { Text("AIzaSy...", color = SukhanTextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SukhanGold,
                            unfocusedBorderColor = SukhanBorder,
                            focusedTextColor = SukhanTextPrimary,
                            unfocusedTextColor = SukhanTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                repository.preferences.setCustomApiKey(inputApiKey.trim())
                                Toast.makeText(context, "API Key updated successfully", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SukhanGold, contentColor = SukhanBackground),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save API Key", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 5. Data Privacy & Local Backup
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = SukhanBurgundyDark.copy(alpha = 0.5f),
                borderColor = SukhanGold.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = SukhanGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("100% Private On-Device Storage", color = SukhanGoldLight, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your poems, personal drafts, and revisions reside strictly in your device's local Room database. No poetry is ever uploaded or monetized.",
                        color = SukhanTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                val allDrafts = repository.allDrafts.first()
                                val backupText = buildString {
                                    appendLine("=== SUKHAN POETRY VAULT BACKUP ===")
                                    appendLine("Author: $inputPenName")
                                    appendLine("Date: ${java.util.Date()}")
                                    appendLine("Total Drafts: ${allDrafts.size}")
                                    appendLine("==================================\n")
                                    allDrafts.forEach { d ->
                                        appendLine("--- ${d.title} (${d.genre}) ---")
                                        appendLine(d.content)
                                        appendLine("\n")
                                    }
                                }
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Sukhan Vault Backup", backupText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "Exported ${allDrafts.size} drafts to clipboard", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SukhanSurfaceElevated),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = SukhanGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Backup All Drafts to Clipboard", color = SukhanTextPrimary, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
