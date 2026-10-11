package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SukhanBackground
import com.example.ui.theme.SukhanBorder
import com.example.ui.theme.SukhanBurgundyDark
import com.example.ui.theme.SukhanGold
import com.example.ui.theme.SukhanGoldLight
import com.example.ui.theme.SukhanSerif
import com.example.ui.theme.SukhanTextMuted
import com.example.ui.theme.SukhanTextPrimary
import com.example.ui.theme.SukhanTextSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val alphaAnim = remember { Animatable(0f) }
    val scaleAnim = remember { Animatable(0.85f) }

    LaunchedEffect(Unit) {
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
        delay(1400)
        onSplashFinished()
    }

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            SukhanBackground,
            SukhanBurgundyDark.copy(alpha = 0.6f),
            SukhanBackground
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(32.dp)
                .alpha(alphaAnim.value)
                .scale(scaleAnim.value)
        ) {
            // Calligraphy emblem ring
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .border(1.5.dp, SukhanBorder, CircleShape)
                    .padding(18.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = null,
                    tint = SukhanGold,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // App Name
            Text(
                text = "SUKHAN",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = SukhanGold,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 6.sp,
                    fontFamily = SukhanSerif
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Hindi / Hinglish Script
            Text(
                text = "सुख़न",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = SukhanTextPrimary,
                    fontSize = 26.sp,
                    fontFamily = SukhanSerif
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Exact Tagline mandated
            Text(
                text = "A home for words, poets & untold feelings.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = SukhanTextPrimary.copy(alpha = 0.9f),
                    fontFamily = SukhanSerif,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Exact Creator Credit mandated
            Box(
                modifier = Modifier
                    .border(1.dp, SukhanBorder, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Made by Prince Thakur",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = SukhanGoldLight,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp,
                        fontSize = 13.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
