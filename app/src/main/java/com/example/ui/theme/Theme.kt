package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SukhanColorScheme = darkColorScheme(
    primary = SukhanGold,
    onPrimary = SukhanBackground,
    primaryContainer = SukhanBurgundyDark,
    onPrimaryContainer = SukhanGoldLight,
    secondary = SukhanBurgundy,
    onSecondary = SukhanTextPrimary,
    secondaryContainer = SukhanBurgundyDark,
    onSecondaryContainer = SukhanTextPrimary,
    tertiary = SukhanGoldLight,
    onTertiary = SukhanBackground,
    background = SukhanBackground,
    onBackground = SukhanTextPrimary,
    surface = SukhanSurface,
    onSurface = SukhanTextPrimary,
    surfaceVariant = SukhanSurfaceElevated,
    onSurfaceVariant = SukhanTextSecondary,
    outline = SukhanBorder,
    outlineVariant = SukhanBorderSubtle
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SukhanColorScheme,
        typography = Typography,
        content = content
    )
}
