package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ripple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanGold
import com.example.ui.theme.SukhanSurface
import com.example.ui.theme.SukhanTextMuted
import com.example.ui.theme.SukhanTextSecondary

sealed class SukhanBottomTab(
    val route: String,
    val title: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    object Home : SukhanBottomTab("home", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Write : SukhanBottomTab("write", "Write", Icons.Filled.Create, Icons.Outlined.Create)
    object Poets : SukhanBottomTab("poets", "Poets", Icons.Filled.AutoStories, Icons.Outlined.AutoStories)
    object Vault : SukhanBottomTab("vault", "My Vault", Icons.Filled.Inventory2, Icons.Outlined.Inventory2)
    object Settings : SukhanBottomTab("settings", "Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun SukhanBottomBar(
    currentRoute: String,
    onNavigateToTab: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        SukhanBottomTab.Home,
        SukhanBottomTab.Write,
        SukhanBottomTab.Poets,
        SukhanBottomTab.Vault,
        SukhanBottomTab.Settings
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SukhanSurface.copy(alpha = 0.98f))
    ) {
        HorizontalDivider(thickness = 0.75.dp, color = SukhanBorder)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEach { tab ->
                val isSelected = currentRoute == tab.route
                val interactionSource = remember { MutableInteractionSource() }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = ripple(bounded = true, color = SukhanGold),
                            onClick = { onNavigateToTab(tab.route) }
                        )
                        .testTag("nav_item_${tab.route}")
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) SukhanBurgundyDark else SukhanSurface.copy(alpha = 0.0f))
                                .border(
                                    width = if (isSelected) 1.dp else 0.dp,
                                    color = if (isSelected) SukhanBorder else SukhanBorder.copy(alpha = 0f),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) tab.activeIcon else tab.inactiveIcon,
                                contentDescription = tab.title,
                                tint = if (isSelected) SukhanGold else SukhanTextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = tab.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) SukhanGold else SukhanTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
