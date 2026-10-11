package com.example.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SukhanSerif
import com.example.ui.theme.SukhanTextPrimary

@Composable
fun ScriptText(
    text: String,
    modifier: Modifier = Modifier,
    script: String = "Nastaliq", // "Nastaliq", "Devanagari", "Gurmukhi", "Roman"
    fontSize: TextUnit = 18.sp,
    lineHeight: TextUnit = (fontSize.value * 1.6f).sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = SukhanTextPrimary,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val isRtl = script.equals("Nastaliq", ignoreCase = true) || 
                script.equals("Urdu", ignoreCase = true) ||
                isUrduArabicString(text)

    val layoutDirection = if (isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr
    val defaultAlignment = if (isRtl) TextAlign.Right else TextAlign.Left
    val resolvedTextAlign = textAlign ?: defaultAlignment

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Text(
            text = text,
            modifier = modifier,
            style = TextStyle(
                fontFamily = SukhanSerif,
                fontSize = fontSize,
                lineHeight = lineHeight,
                fontWeight = fontWeight,
                color = color,
                textAlign = resolvedTextAlign
            ),
            maxLines = maxLines,
            overflow = overflow
        )
    }
}

fun isUrduArabicString(text: String): Boolean {
    // Quick heuristic for Arabic/Urdu Unicode range
    for (char in text) {
        val code = char.code
        if (code in 0x0600..0x06FF || code in 0x0750..0x077F || code in 0xFB50..0xFDFF || code in 0xFE70..0xFEFF) {
            return true
        }
    }
    return false
}
