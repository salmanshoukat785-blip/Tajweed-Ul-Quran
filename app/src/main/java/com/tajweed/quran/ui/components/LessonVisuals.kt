package com.tajweed.quran.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Waves
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.ui.theme.CourseBrushes

fun lessonIcon(id: String): ImageVector = when (id) {
    "huroof" -> Icons.Filled.SortByAlpha
    "hamza-wasli" -> Icons.Filled.Link
    "ra-allah" -> Icons.Filled.RecordVoiceOver
    "ghunna" -> Icons.Filled.GraphicEq
    "qalqalah" -> Icons.Filled.Waves
    "madd" -> Icons.Filled.Straighten
    "noon-sakin" -> Icons.AutoMirrored.Filled.MenuBook
    else -> Icons.Filled.Mic
}

fun lessonBrush(id: String): Brush {
    val i = TajweedContent.lessons.indexOfFirst { it.id == id }.coerceAtLeast(0)
    return CourseBrushes[i % CourseBrushes.size]
}
