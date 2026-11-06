package com.tajweed.quran.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.R
import com.tajweed.quran.ui.i18n.AppLanguage
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.HeaderBrush
import com.tajweed.quran.ui.theme.TextDark
import kotlin.math.roundToInt

/** "Tajweed ul Quran" logo. Replace res/drawable/ic_logo.xml with your own logo file. */
@Composable
fun TajweedLogo(modifier: Modifier = Modifier, size: Dp = 120.dp) {
    Image(
        painter = painterResource(R.drawable.ic_logo),
        contentDescription = "Tajweed ul Quran",
        modifier = modifier.size(size),
    )
}

/** Blue gradient top bar used on every inner screen. */
@Composable
fun ScreenHeader(
    title: String,
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(HeaderBrush)
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        } else {
            Box(Modifier.size(12.dp))
        }
        Text(
            title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )
        if (trailing != null) trailing()
    }
}

/** English | اردو switch. */
@Composable
fun LanguageToggle(
    current: AppLanguage,
    onChange: (AppLanguage) -> Unit,
    dark: Boolean = true,
) {
    Row(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(if (dark) Color.White.copy(alpha = 0.18f) else Color(0xFFE3ECFB))
            .padding(3.dp)
    ) {
        AppLanguage.entries.forEach { lang ->
            val selected = lang == current
            val textColor = when {
                selected && dark -> Blue
                selected -> Color.White
                dark -> Color.White
                else -> Blue
            }
            Text(
                if (lang == AppLanguage.EN) "English" else "اردو",
                color = textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (selected) (if (dark) Color.White else Blue) else Color.Transparent
                    )
                    .clickable { onChange(lang) }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            )
        }
    }
}

/** Arabic text, always right-to-left and centred. */
@Composable
fun ArabicText(
    text: String,
    modifier: Modifier = Modifier,
    size: TextUnit = 26.sp,
    color: Color = TextDark,
) {
    Text(
        text,
        modifier = modifier,
        color = color,
        fontSize = size,
        lineHeight = size * 1.7f,
        textAlign = TextAlign.Center,
        style = TextStyle(textDirection = TextDirection.Rtl),
    )
}

/** Circular progress (like the 70% ring on the reference dashboard). */
@Composable
fun ProgressRing(progress: Float, modifier: Modifier = Modifier.size(84.dp)) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            val stroke = 10.dp.toPx()
            val arcSize = Size(size.width - stroke, size.height - stroke)
            val topLeft = Offset(stroke / 2, stroke / 2)
            drawArc(
                Color(0xFFD8E3F5), 0f, 360f, false,
                topLeft = topLeft, size = arcSize,
                style = Stroke(stroke, cap = StrokeCap.Round),
            )
            drawArc(
                GreenOk, -90f, 360f * progress.coerceIn(0f, 1f), false,
                topLeft = topLeft, size = arcSize,
                style = Stroke(stroke, cap = StrokeCap.Round),
            )
        }
        Text(
            "${(progress * 100).roundToInt()}%",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextDark,
        )
    }
}

/** Faint mosque silhouette drawn in the dashboard header. */
@Composable
fun MosqueSilhouette(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val c = Color.White.copy(alpha = 0.10f)
        val w = size.width
        val h = size.height
        drawRect(c, Offset(w * 0.15f, h * 0.55f), Size(w * 0.7f, h * 0.45f))
        drawArc(c, 180f, 180f, true, Offset(w * 0.3f, h * 0.2f), Size(w * 0.4f, h * 0.7f))
        drawRect(c, Offset(w * 0.02f, h * 0.25f), Size(w * 0.1f, h * 0.75f))
        drawRect(c, Offset(w * 0.88f, h * 0.25f), Size(w * 0.1f, h * 0.75f))
        drawCircle(c, w * 0.07f, Offset(w * 0.07f, h * 0.22f))
        drawCircle(c, w * 0.07f, Offset(w * 0.93f, h * 0.22f))
    }
}

/** Lets a card overlap the header above it by [amount]. */
fun Modifier.overlapTop(amount: Dp): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val shift = amount.roundToPx()
    layout(placeable.width, placeable.height - shift) { placeable.place(0, -shift) }
}

@Composable
fun IconBadge(icon: ImageVector, tint: Color, background: Color, size: Dp = 44.dp) {
    Box(
        Modifier.size(size).clip(CircleShape).background(background),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(size * 0.55f))
    }
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
    }
}
