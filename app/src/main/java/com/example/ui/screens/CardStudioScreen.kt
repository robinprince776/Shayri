package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.FormatAlignRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.ui.components.ScriptText
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
import java.io.File
import java.io.FileOutputStream

enum class CardTheme(
    val title: String,
    val bgColor: Color,
    val textColor: Color,
    val accentColor: Color,
    val borderColor: Color
) {
    DARK_LUXURY(
        "Dark Luxury",
        Color(0xFF101014),
        Color(0xFFF3EBDD),
        Color(0xFFC6A66B),
        Color(0x66C6A66B)
    ),
    BURGUNDY_EDITORIAL(
        "Burgundy Editorial",
        Color(0xFF381523),
        Color(0xFFFDFBF7),
        Color(0xFFE5B8C8),
        Color(0x66E5B8C8)
    ),
    IVORY_PAPER(
        "Ivory Paper",
        Color(0xFFF7F3E9),
        Color(0xFF23201C),
        Color(0xFF8B6B4C),
        Color(0x448B6B4C)
    ),
    MINIMAL_MONO(
        "Minimal Mono",
        Color(0xFF09090B),
        Color(0xFFFAFAFA),
        Color(0xFFA1A1AA),
        Color(0x33FAFAFA)
    ),
    GLASS_GLOW(
        "Glass-Inspired",
        Color(0xFF1F1B24),
        Color(0xFFF3EBDD),
        Color(0xFFC6A66B),
        Color(0x88C6A66B)
    )
}

@Composable
fun CardStudioScreen(
    initialPoemText: String = "",
    initialPoetName: String = "Prince Thakur",
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    var poemText by remember {
        mutableStateOf(
            if (initialPoemText.isNotBlank()) initialPoemText
            else "हज़ारों ख़्वाहिशें ऐसी कि हर ख़्वाहिश पे दम निकले\nबहुत निकले मिरे अरमान लेकिन फिर भी कम निकले"
        )
    }
    var poetName by remember { mutableStateOf(if (initialPoetName.isNotBlank()) initialPoetName else "Mirza Ghalib") }
    var selectedTheme by remember { mutableStateOf(CardTheme.DARK_LUXURY) }
    var fontSizeSp by remember { mutableFloatStateOf(20f) }
    var textAlign by remember { mutableStateOf(TextAlign.Center) }
    var includeWatermark by remember { mutableStateOf(true) }

    fun sharePoetryImage() {
        try {
            val bitmap = renderCardBitmap(
                poemText = poemText,
                poetName = poetName,
                theme = selectedTheme,
                includeWatermark = includeWatermark,
                align = textAlign,
                fontSize = fontSizeSp
            )

            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val imageFile = File(cachePath, "sukhan_poetry_${System.currentTimeMillis()}.png")
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }

            val contentUri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, contentUri)
                putExtra(Intent.EXTRA_TEXT, "$poemText\n\n— $poetName\n(Created with SUKHAN • Made by Prince Thakur)")
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Poetry Card"))
        } catch (e: Exception) {
            // Fallback to text sharing if FileProvider authority isn't configured
            val textIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$poemText\n\n— $poetName\n\nShared via SUKHAN • Made by Prince Thakur")
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(textIntent, "Share Poetry Card"))
            Toast.makeText(context, "Sharing poem card", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SukhanBackground)
            .testTag("card_studio_screen")
    ) {
        SukhanTopBar(
            title = "Poetry Card Studio",
            subtitle = selectedTheme.title,
            onBackClick = onBackClick,
            actions = {
                IconButton(
                    onClick = { sharePoetryImage() },
                    modifier = Modifier.testTag("card_studio_share_button")
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share Card", tint = SukhanGold)
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // Live Card Canvas Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(selectedTheme.bgColor)
                    .border(1.5.dp, selectedTheme.borderColor, RoundedCornerShape(20.dp))
                    .padding(24.dp)
                    .testTag("card_preview_box")
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = when (textAlign) {
                        TextAlign.Left -> Alignment.Start
                        TextAlign.Right -> Alignment.End
                        else -> Alignment.CenterHorizontally
                    }
                ) {
                    // Emblem Header
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .border(1.dp, selectedTheme.accentColor.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "सुख़न",
                            fontSize = 11.sp,
                            color = selectedTheme.accentColor,
                            fontFamily = SukhanSerif
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Poem Text
                    ScriptText(
                        text = poemText,
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.65f).sp,
                        color = selectedTheme.textColor,
                        textAlign = textAlign,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Poet Attribution
                    Text(
                        text = "— $poetName",
                        style = TextStyle(
                            fontFamily = SukhanSerif,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = selectedTheme.accentColor
                        )
                    )

                    if (includeWatermark) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "SUKHAN • Prince Thakur",
                            style = TextStyle(
                                fontSize = 10.sp,
                                letterSpacing = 1.5.sp,
                                color = selectedTheme.textColor.copy(alpha = 0.45f)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Theme Selector
            Text(
                text = "LAYOUT THEMES (5 STYLES)",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SukhanGold,
                    letterSpacing = 1.2.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CardTheme.values().forEach { theme ->
                    val isSelected = selectedTheme == theme
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(theme.bgColor)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) SukhanGold else SukhanBorder,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedTheme = theme }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = theme.title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = theme.textColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Customization Options: Alignment & Font size
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Text Alignment", color = SukhanTextPrimary, fontSize = 13.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = { textAlign = TextAlign.Left }) {
                        Icon(imageVector = Icons.Default.FormatAlignLeft, contentDescription = "Left", tint = if (textAlign == TextAlign.Left) SukhanGold else SukhanTextMuted)
                    }
                    IconButton(onClick = { textAlign = TextAlign.Center }) {
                        Icon(imageVector = Icons.Default.FormatAlignCenter, contentDescription = "Center", tint = if (textAlign == TextAlign.Center) SukhanGold else SukhanTextMuted)
                    }
                    IconButton(onClick = { textAlign = TextAlign.Right }) {
                        Icon(imageVector = Icons.Default.FormatAlignRight, contentDescription = "Right", tint = if (textAlign == TextAlign.Right) SukhanGold else SukhanTextMuted)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Font size slider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Font Size (${fontSizeSp.toInt()}sp)", color = SukhanTextPrimary, fontSize = 13.sp)
                Slider(
                    value = fontSizeSp,
                    onValueChange = { fontSizeSp = it },
                    valueRange = 14f..30f,
                    colors = SliderDefaults.colors(thumbColor = SukhanGold, activeTrackColor = SukhanGold),
                    modifier = Modifier.width(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Watermark Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Include Sukhan Watermark", color = SukhanTextPrimary, fontSize = 13.sp)
                Switch(
                    checked = includeWatermark,
                    onCheckedChange = { includeWatermark = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = SukhanGold, checkedTrackColor = SukhanBurgundyDark)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Edit Inputs
            OutlinedTextField(
                value = poetName,
                onValueChange = { poetName = it },
                label = { Text("Attribution / Author Name") },
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

            OutlinedTextField(
                value = poemText,
                onValueChange = { poemText = it },
                label = { Text("Poem Text") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SukhanGold,
                    unfocusedBorderColor = SukhanBorder,
                    focusedTextColor = SukhanTextPrimary,
                    unfocusedTextColor = SukhanTextPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = { sharePoetryImage() },
                colors = ButtonDefaults.buttonColors(containerColor = SukhanGold, contentColor = SukhanBackground),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Export & Share Poetry Card", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// Android Bitmap Renderer
fun renderCardBitmap(
    poemText: String,
    poetName: String,
    theme: CardTheme,
    includeWatermark: Boolean,
    align: TextAlign,
    fontSize: Float
): Bitmap {
    val width = 1080
    val height = 1350
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    // Background color
    val bgPaint = Paint().apply {
        color = android.graphics.Color.argb(
            (theme.bgColor.alpha * 255).toInt(),
            (theme.bgColor.red * 255).toInt(),
            (theme.bgColor.green * 255).toInt(),
            (theme.bgColor.blue * 255).toInt()
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

    // Border
    val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 14f
        color = android.graphics.Color.argb(
            (theme.borderColor.alpha * 255).toInt(),
            (theme.borderColor.red * 255).toInt(),
            (theme.borderColor.green * 255).toInt(),
            (theme.borderColor.blue * 255).toInt()
        )
    }
    canvas.drawRoundRect(RectF(40f, 40f, (width - 40).toFloat(), (height - 40).toFloat()), 32f, 32f, borderPaint)

    // Top Emblem
    val emblemPaint = Paint().apply {
        color = android.graphics.Color.argb(
            255,
            (theme.accentColor.red * 255).toInt(),
            (theme.accentColor.green * 255).toInt(),
            (theme.accentColor.blue * 255).toInt()
        )
        textSize = 42f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.SERIF
        isAntiAlias = true
    }
    canvas.drawText("✦  SUKHAN  ✦", width / 2f, 150f, emblemPaint)

    // Poem Lines
    val textPaint = Paint().apply {
        color = android.graphics.Color.argb(
            255,
            (theme.textColor.red * 255).toInt(),
            (theme.textColor.green * 255).toInt(),
            (theme.textColor.blue * 255).toInt()
        )
        textSize = (fontSize * 2.4f)
        typeface = Typeface.SERIF
        isAntiAlias = true
        textAlign = when (align) {
            TextAlign.Left -> Paint.Align.LEFT
            TextAlign.Right -> Paint.Align.RIGHT
            else -> Paint.Align.CENTER
        }
    }

    val lines = poemText.lines()
    val lineSpacing = textPaint.textSize * 1.6f
    val startY = (height / 2f) - ((lines.size * lineSpacing) / 2f) + 40f
    val posX = when (align) {
        TextAlign.Left -> 120f
        TextAlign.Right -> (width - 120f)
        else -> width / 2f
    }

    lines.forEachIndexed { i, line ->
        canvas.drawText(line, posX, startY + (i * lineSpacing), textPaint)
    }

    // Poet Attribution
    val poetPaint = Paint().apply {
        color = android.graphics.Color.argb(
            255,
            (theme.accentColor.red * 255).toInt(),
            (theme.accentColor.green * 255).toInt(),
            (theme.accentColor.blue * 255).toInt()
        )
        textSize = 38f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    canvas.drawText("— $poetName", width / 2f, height - 220f, poetPaint)

    // Watermark
    if (includeWatermark) {
        val wmPaint = Paint().apply {
            color = android.graphics.Color.argb(120, 200, 200, 200)
            textSize = 28f
            letterSpacing = 0.2f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("SUKHAN • Prince Thakur", width / 2f, height - 120f, wmPaint)
    }

    return bitmap
}
