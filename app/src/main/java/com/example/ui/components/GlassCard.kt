package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBorderSubtle
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanSurface
import com.example.ui.theme.SukhanSurfaceElevated

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = SukhanSurfaceElevated.copy(alpha = 0.82f),
    borderColor: Color = SukhanBorder,
    borderWidth: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(BorderStroke(borderWidth, borderColor), shape)
    ) {
        content()
    }
}

@Composable
fun LuxuryGradientCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(18.dp),
    borderColor: Color = SukhanBorder,
    content: @Composable BoxScope.() -> Unit
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            SukhanBurgundyDark.copy(alpha = 0.85f),
            SukhanSurface.copy(alpha = 0.95f),
            SukhanSurfaceElevated.copy(alpha = 0.90f)
        )
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(gradientBrush)
            .border(BorderStroke(1.dp, borderColor), shape)
    ) {
        content()
    }
}
