package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.PoetsCatalog
import com.example.data.model.Poet
import com.example.ui.components.GlassCard
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
fun PoetsScreen(
    onSelectPoet: (poetId: String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val allPoets = remember { PoetsCatalog.poets }
    val filteredPoets = remember(searchQuery, selectedFilter) {
        var list = PoetsCatalog.searchPoets(searchQuery)
        if (selectedFilter != "All") {
            list = list.filter { poet ->
                when (selectedFilter) {
                    "Classical Urdu" -> poet.movement.contains("Delhi", ignoreCase = true) || poet.movement.contains("Classical", ignoreCase = true)
                    "Modernist" -> poet.movement.contains("Modern", ignoreCase = true)
                    "Progressive" -> poet.movement.contains("Progressive", ignoreCase = true) || poet.movement.contains("Taraqqi", ignoreCase = true)
                    "Sufi & Bhakti" -> poet.movement.contains("Sufi", ignoreCase = true) || poet.movement.contains("Bhakti", ignoreCase = true)
                    else -> true
                }
            }
        }
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("poets_screen")
    ) {
        SukhanTopBar(
            title = "Poets' Library",
            subtitle = "${filteredPoets.size} Verified Masters"
        )

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
                placeholder = { Text("Search poet, era, movement, verse...", color = SukhanTextMuted, fontSize = 13.sp) },
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
                    .testTag("poet_search_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Classical Urdu", "Modernist", "Progressive", "Sufi & Bhakti").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter, fontSize = 11.sp) },
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

            // Poet Cards List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredPoets, key = { it.id }) { poet ->
                    PoetCatalogueCard(
                        poet = poet,
                        onClick = { onSelectPoet(poet.id) }
                    )
                }

                item { Spacer(modifier = Modifier.height(30.dp)) }
            }
        }
    }
}

@Composable
fun PoetCatalogueCard(
    poet: Poet,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("poet_card_${poet.id}"),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = poet.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = SukhanGold,
                                fontWeight = FontWeight.Bold,
                                fontFamily = SukhanSerif
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified attribution",
                            tint = SukhanGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = poet.originalName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = SukhanGoldLight,
                            fontFamily = SukhanSerif,
                            fontSize = 15.sp
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SukhanBurgundyDark)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = poet.forms.firstOrNull() ?: "Ghazal",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanGoldLight,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${poet.era} • ${poet.movement}",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SukhanTextMuted,
                    fontSize = 11.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Signature Sher
            if (poet.famousSher.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SukhanBackground.copy(alpha = 0.6f))
                        .padding(12.dp)
                ) {
                    ScriptText(
                        text = poet.famousSher,
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = SukhanTextPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${poet.languages.joinToString(", ")} • ${poet.majorWorks.size} Major Works",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SukhanTextMuted,
                        fontSize = 11.sp
                    )
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Read Works",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = SukhanGold,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
