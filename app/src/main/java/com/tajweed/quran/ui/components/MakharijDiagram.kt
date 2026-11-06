package com.tajweed.quran.ui.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.data.model.L
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.TextMuted

/**
 * One makhraj on the mouth diagram.
 * (x, y) are fractions of the drawing width / height. Left side = lips, right side = throat.
 */
private data class MakhrajPoint(
    val number: Int,
    val group: Int,
    val x: Float,
    val y: Float,
    val letters: String,
)

private val GroupColors = listOf(
    Color(0xFF7E57C2), // Jawf
    Color(0xFFE64A19), // Halq
    Color(0xFF1565C0), // Lisan
    Color(0xFF2E7D32), // Shafatain
    Color(0xFFF9A825), // Khayshum
)

private val GroupNames = listOf(
    L("Jawf", "جوف"),
    L("Halq", "حلق"),
    L("Lisan", "لسان"),
    L("Shafatain", "شفتین"),
    L("Khayshum", "خیشوم"),
)

private val Points = listOf(
    MakhrajPoint(1, 0, 0.50f, 0.27f, "ا و ی"),
    MakhrajPoint(2, 1, 0.86f, 0.89f, "ء ھ"),
    MakhrajPoint(3, 1, 0.86f, 0.75f, "ع ح"),
    MakhrajPoint(4, 1, 0.86f, 0.61f, "غ خ"),
    MakhrajPoint(5, 2, 0.71f, 0.40f, "ق"),
    MakhrajPoint(6, 2, 0.62f, 0.38f, "ک"),
    MakhrajPoint(7, 2, 0.50f, 0.38f, "ج ش ی"),
    MakhrajPoint(8, 2, 0.57f, 0.50f, "ض"),
    MakhrajPoint(9, 2, 0.40f, 0.47f, "ل"),
    MakhrajPoint(10, 2, 0.25f, 0.38f, "ن"),
    MakhrajPoint(11, 2, 0.32f, 0.38f, "ر"),
    MakhrajPoint(12, 2, 0.19f, 0.31f, "ط د ت"),
    MakhrajPoint(13, 2, 0.15f, 0.52f, "ص س ز"),
    MakhrajPoint(14, 2, 0.145f, 0.40f, "ظ ذ ث"),
    MakhrajPoint(15, 3, 0.06f, 0.47f, "ف"),
    MakhrajPoint(16, 3, 0.05f, 0.62f, "ب م و"),
    MakhrajPoint(17, 4, 0.50f, 0.10f, "ن م"),
)

/** Side view of the mouth with the 17 makharij marked, plus a legend of the letters. */
@Composable
fun MakharijDiagram(modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                tr("Mouth diagram (side view)", "منہ کا نقشہ (سائیڈ ویو)"),
                fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Blue,
            )
            MouthCanvas()
            Text(
                tr(
                    "Lips are on the left and the throat is on the right. The numbers match the 17 makharij below.",
                    "بائیں طرف ہونٹ اور دائیں طرف حلق ہے۔ نمبر نیچے دیے گئے 17 مخارج کے مطابق ہیں۔",
                ),
                fontSize = 13.sp, lineHeight = 20.sp, color = TextMuted,
            )
            Legend()
        }
    }
}

@Composable
private fun MouthCanvas() {
    Canvas(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1.18f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF4F8FF)),
    ) {
        val w = size.width
        val h = size.height
        fun px(n: Float) = n * w
        fun py(n: Float) = n * h

        val line = Color(0xFFB36B5E)
        val cavityFill = Color(0xFFFFEEEA)
        val thin = 2.dp.toPx()

        // nasal cavity (Khayshum)
        drawOval(
            color = Color(0xFFFFE9C7),
            topLeft = Offset(px(0.28f), py(0.03f)),
            size = Size(px(0.50f), py(0.15f)),
        )
        drawOval(
            color = line,
            topLeft = Offset(px(0.28f), py(0.03f)),
            size = Size(px(0.50f), py(0.15f)),
            style = Stroke(width = thin),
        )

        // mouth cavity
        val cavity = Path().apply {
            moveTo(px(0.12f), py(0.33f))
            cubicTo(px(0.25f), py(0.22f), px(0.55f), py(0.17f), px(0.80f), py(0.30f))
            lineTo(px(0.80f), py(0.68f))
            lineTo(px(0.12f), py(0.60f))
            close()
        }
        drawPath(cavity, cavityFill)

        // throat (Halq) with its three parts
        drawRoundRect(
            color = cavityFill,
            topLeft = Offset(px(0.79f), py(0.30f)),
            size = Size(px(0.14f), py(0.67f)),
            cornerRadius = CornerRadius(px(0.03f)),
        )
        drawRoundRect(
            color = line,
            topLeft = Offset(px(0.79f), py(0.30f)),
            size = Size(px(0.14f), py(0.67f)),
            cornerRadius = CornerRadius(px(0.03f)),
            style = Stroke(width = thin),
        )
        drawLine(line, Offset(px(0.79f), py(0.68f)), Offset(px(0.93f), py(0.68f)), strokeWidth = thin / 2)
        drawLine(line, Offset(px(0.79f), py(0.82f)), Offset(px(0.93f), py(0.82f)), strokeWidth = thin / 2)

        // tongue
        val tongue = Path().apply {
            moveTo(px(0.13f), py(0.57f))
            cubicTo(px(0.20f), py(0.50f), px(0.30f), py(0.44f), px(0.45f), py(0.43f))
            cubicTo(px(0.60f), py(0.42f), px(0.72f), py(0.46f), px(0.79f), py(0.60f))
            lineTo(px(0.79f), py(0.68f))
            lineTo(px(0.13f), py(0.62f))
            close()
        }
        drawPath(tongue, Color(0xFFEF9A9A))
        drawPath(tongue, Color(0xFFC96B6B), style = Stroke(width = thin))

        // palate
        val palate = Path().apply {
            moveTo(px(0.12f), py(0.33f))
            cubicTo(px(0.25f), py(0.22f), px(0.55f), py(0.17f), px(0.80f), py(0.30f))
        }
        drawPath(palate, line, style = Stroke(width = thin * 2))

        // teeth
        val toothStroke = Color(0xFF9AA5B8)
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(px(0.05f), py(0.30f)),
            size = Size(px(0.06f), py(0.13f)),
            cornerRadius = CornerRadius(px(0.01f)),
        )
        drawRoundRect(
            color = toothStroke,
            topLeft = Offset(px(0.05f), py(0.30f)),
            size = Size(px(0.06f), py(0.13f)),
            cornerRadius = CornerRadius(px(0.01f)),
            style = Stroke(width = thin / 2),
        )
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(px(0.05f), py(0.52f)),
            size = Size(px(0.06f), py(0.10f)),
            cornerRadius = CornerRadius(px(0.01f)),
        )
        drawRoundRect(
            color = toothStroke,
            topLeft = Offset(px(0.05f), py(0.52f)),
            size = Size(px(0.06f), py(0.10f)),
            cornerRadius = CornerRadius(px(0.01f)),
            style = Stroke(width = thin / 2),
        )

        // lips
        val lipFill = Color(0xFFF48FB1)
        val lipLine = Color(0xFFC2185B)
        drawRoundRect(
            color = lipFill,
            topLeft = Offset(px(0.0f), py(0.27f)),
            size = Size(px(0.055f), py(0.13f)),
            cornerRadius = CornerRadius(px(0.025f)),
        )
        drawRoundRect(
            color = lipLine,
            topLeft = Offset(px(0.0f), py(0.27f)),
            size = Size(px(0.055f), py(0.13f)),
            cornerRadius = CornerRadius(px(0.025f)),
            style = Stroke(width = thin / 2),
        )
        drawRoundRect(
            color = lipFill,
            topLeft = Offset(px(0.0f), py(0.53f)),
            size = Size(px(0.055f), py(0.14f)),
            cornerRadius = CornerRadius(px(0.025f)),
        )
        drawRoundRect(
            color = lipLine,
            topLeft = Offset(px(0.0f), py(0.53f)),
            size = Size(px(0.055f), py(0.14f)),
            cornerRadius = CornerRadius(px(0.025f)),
            style = Stroke(width = thin / 2),
        )

        // markers
        Points.forEach { p ->
            val c = Offset(px(p.x), py(p.y))
            drawCircle(Color.White, radius = w * 0.034f, center = c)
            drawCircle(GroupColors[p.group], radius = w * 0.030f, center = c)
        }

        // numbers inside the markers
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
            textSize = w * 0.036f
            typeface = Typeface.DEFAULT_BOLD
        }
        drawIntoCanvas { canvas ->
            Points.forEach { p ->
                paint.color = if (p.group == 4) Color(0xFF14213D).toArgb() else Color.White.toArgb()
                canvas.nativeCanvas.drawText(
                    p.number.toString(),
                    px(p.x),
                    py(p.y) + paint.textSize * 0.35f,
                    paint,
                )
            }
        }
    }
}

@Composable
private fun Legend() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        GroupNames.forEachIndexed { groupIndex, name ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(12.dp).clip(CircleShape).background(GroupColors[groupIndex]))
                    Spacer(Modifier.size(8.dp))
                    Text(name.t(), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = GroupColors[groupIndex].darker())
                }
                Points.filter { it.group == groupIndex }.chunked(3).forEach { rowItems ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        rowItems.forEach { p ->
                            Row(
                                Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Box(
                                    Modifier.size(22.dp).clip(CircleShape).background(GroupColors[p.group]),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        "${p.number}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (p.group == 4) Color(0xFF14213D) else Color.White,
                                    )
                                }
                                Spacer(Modifier.size(6.dp))
                                ArabicText(p.letters, size = 20.sp)
                            }
                        }
                        repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
                    }
                }
            }
        }
    }
}

/** A slightly darker shade, used so the group names stay readable on white. */
private fun Color.darker(): Color = Color(red * 0.8f, green * 0.8f, blue * 0.8f, alpha)
