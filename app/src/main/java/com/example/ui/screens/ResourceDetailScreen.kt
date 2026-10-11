package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.ResourcesCatalog
import com.example.ui.components.GlassCard
import com.example.ui.components.LuxuryGradientCard
import com.example.ui.components.ScriptText
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

@Composable
fun ResourceDetailScreen(
    resourceId: String,
    onBackClick: () -> Unit,
    onUseTemplateInEditor: (templateText: String) -> Unit
) {
    val context = LocalContext.current
    val resource = remember(resourceId) { ResourcesCatalog.resources.find { it.id == resourceId } }

    if (resource == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SukhanBackground)
        ) {
            SukhanTopBar(title = "Resource Not Found", onBackClick = onBackClick)
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Resource guide could not be found.", color = SukhanTextSecondary)
            }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("resource_detail_screen")
    ) {
        SukhanTopBar(
            title = resource.title,
            subtitle = resource.category,
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(6.dp)) }

            // Header Banner
            item {
                LuxuryGradientCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = resource.title,
                            style = MaterialTheme.typography.displayMedium.copy(
                                color = SukhanGold,
                                fontWeight = FontWeight.Bold,
                                fontFamily = SukhanSerif
                            )
                        )
                        Text(
                            text = resource.subtitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = SukhanGoldLight
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(thickness = 0.5.dp, color = SukhanBorder)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = resource.explanation,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SukhanTextPrimary,
                                lineHeight = 26.sp
                            )
                        )
                    }
                }
            }

            // Authentic Examples
            if (resource.examples.isNotEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "AUTHENTIC EXAMPLES & CITATIONS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SukhanGold,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            resource.examples.forEach { ex ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SukhanBackground.copy(alpha = 0.6f))
                                        .padding(10.dp)
                                ) {
                                    ScriptText(
                                        text = ex,
                                        fontSize = 15.sp,
                                        lineHeight = 24.sp,
                                        color = SukhanTextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Key Rules
            if (resource.keyRules.isNotEmpty()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Rule,
                                    contentDescription = null,
                                    tint = SukhanGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "CANONICAL RULES & METRIC PRINCIPLES",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SukhanGold,
                                        letterSpacing = 1.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            resource.keyRules.forEach { rule ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("• ", color = SukhanGold, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = rule,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = SukhanTextPrimary,
                                            lineHeight = 22.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Practical Tip
            if (resource.practicalTip.isNotBlank()) {
                item {
                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = SukhanBurgundyDark.copy(alpha = 0.7f),
                        borderColor = SukhanGold.copy(alpha = 0.3f)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = SukhanGoldLight,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Ustaad's Practical Tip:",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = SukhanGoldLight,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = resource.practicalTip,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SukhanTextPrimary,
                                        lineHeight = 22.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Downloadable / Importable Template
            if (resource.downloadableTemplate.isNotBlank()) {
                item {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "LITERARY TEMPLATE SKELETON",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SukhanGold,
                                    letterSpacing = 1.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SukhanBackground)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = resource.downloadableTemplate,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SukhanGoldLight,
                                        fontFamily = SukhanSerif
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    onClick = {
                                        onUseTemplateInEditor(resource.downloadableTemplate)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = SukhanGold,
                                        contentColor = SukhanBackground
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(imageVector = Icons.Default.Create, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Open in Studio", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                }

                                OutlinedButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Sukhan Template", resource.downloadableTemplate)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Copied template to clipboard", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp), tint = SukhanGold)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Copy Template", fontSize = 12.sp, color = SukhanGold)
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(30.dp)) }
        }
    }
}
