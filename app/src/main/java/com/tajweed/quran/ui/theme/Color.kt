package com.tajweed.quran.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val NavyDark = Color(0xFF0A2A6B)
val Blue = Color(0xFF1456C9)
val BlueLight = Color(0xFF2F7BF0)
val Gold = Color(0xFFF5B82E)
val Bg = Color(0xFFEFF5FF)
val TextDark = Color(0xFF14213D)
val TextMuted = Color(0xFF5B6B8C)
val GreenOk = Color(0xFF22A559)
val RedBad = Color(0xFFD64545)

val HeaderBrush = Brush.verticalGradient(listOf(Color(0xFF0A2A6B), Color(0xFF1456C9)))

private fun g(a: Long, b: Long) = Brush.linearGradient(listOf(Color(a), Color(b)))

/** One gradient per lesson card (8) – also reused for the three action cards. */
val CourseBrushes = listOf(
    g(0xFF1B63D8, 0xFF0A3A9E), // blue
    g(0xFF2BB673, 0xFF14924F), // green
    g(0xFF7B4BDB, 0xFF5632B8), // purple
    g(0xFFFF9A2E, 0xFFF26B0F), // orange
    g(0xFF1FC2A6, 0xFF0E8F7E), // teal
    g(0xFFF7C32E, 0xFFE79A0C), // gold
    g(0xFFE85A8F, 0xFFC23671), // pink
    g(0xFF2AA9E0, 0xFF1479B8), // sky
)
