package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.ResourcesCatalog
import com.example.data.model.LiteraryResource
import com.example.ui.components.GlassCard
import com.example.ui.components.LuxuryGradientCard
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

@Composable
fun ResourcesScreen(
    onSelectResource: (resourceId: String) -> Unit,
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val filteredResources = remember(searchQuery, selectedCategory) {
        var list = ResourcesCatalog.searchResources(searchQuery)
        if (selectedCategory != "All") {
            list = list.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
        list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("resources_screen")
    ) {
        SukhanTopBar(
            title = "Writing Resource Centre",
            subtitle = "Ghazal meter, Farhang, Rhymes & Structures",
            onBackClick = onBackClick
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
                placeholder = { Text("Search meter, Radif, Qafiya, Farhang...", color = SukhanTextMuted, fontSize = 13.sp) },
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
                    .testTag("resource_search_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Categories horizontal bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ResourcesCatalog.categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 11.sp) },
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

            Spacer(modifier = Modifier.height(14.dp))

            // Resources List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredResources, key = { it.id }) { res ->
                    ResourceSummaryCard(
                        resource = res,
                        onClick = { onSelectResource(res.id) }
                    )
                }

                item { Spacer(modifier = Modifier.height(30.dp)) }
            }
        }
    }
}

@Composable
fun ResourceSummaryCard(
    resource: LiteraryResource,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("resource_card_${resource.id}"),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SukhanBurgundyDark)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = resource.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SukhanGoldLight,
                            fontSize = 10.sp
                        )
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = SukhanGold,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resource.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = SukhanTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontFamily = SukhanSerif
                )
            )

            Text(
                text = resource.subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SukhanGoldLight,
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resource.explanation,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SukhanTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                ),
                maxLines = 2
            )
        }
    }
}
