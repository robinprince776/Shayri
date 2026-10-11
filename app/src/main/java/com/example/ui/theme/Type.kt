package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val SukhanSerif = FontFamily.Serif
val SukhanSans = FontFamily.SansSerif

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = SukhanSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.5.sp,
        color = SukhanTextPrimary
    ),
    displayMedium = TextStyle(
        fontFamily = SukhanSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.25.sp,
        color = SukhanTextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily = SukhanSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.sp,
        color = SukhanTextPrimary
    ),
    titleLarge = TextStyle(
        fontFamily = SukhanSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp,
        color = SukhanTextPrimary
    ),
    titleMedium = TextStyle(
        fontFamily = SukhanSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = SukhanTextPrimary
    ),
    bodyLarge = TextStyle(
        fontFamily = SukhanSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp,
        color = SukhanTextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily = SukhanSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.25.sp,
        color = SukhanTextSecondary
    ),
    labelLarge = TextStyle(
        fontFamily = SukhanSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        color = SukhanGold
    ),
    labelSmall = TextStyle(
        fontFamily = SukhanSans,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = SukhanTextMuted
    )
)
