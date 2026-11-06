package com.tajweed.quran.ui.lessons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.ui.components.ScreenHeader
import com.tajweed.quran.ui.components.lessonBrush
import com.tajweed.quran.ui.components.lessonIcon
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

@Composable
fun LessonsScreen(vm: AppViewModel, onOpenLesson: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenHeader(tr("Lessons", "اسباق"))
        LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(TajweedContent.lessons, key = { it.id }) { lesson ->
                Card(
                    Modifier.fillMaxWidth().clickable { onOpenLesson(lesson.id) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                ) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            Modifier.size(52.dp).clip(CircleShape).background(lessonBrush(lesson.id)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(lessonIcon(lesson.id), null, tint = Color.White, modifier = Modifier.size(28.dp))
                        }
                        Spacer(Modifier.size(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(lesson.title.t(), fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextDark)
                            Text(lesson.subtitle.t(), fontSize = 13.sp, color = TextMuted)
                            Text(
                                "${lesson.rules.size} " + tr("rules", "قواعد"),
                                fontSize = 12.sp, color = TextMuted,
                            )
                        }
                        if (lesson.id in vm.favorites) {
                            Icon(Icons.Filled.Star, null, tint = Gold, modifier = Modifier.size(22.dp))
                        }
                        if (lesson.id in vm.completed) {
                            Spacer(Modifier.size(4.dp))
                            Icon(Icons.Filled.CheckCircle, null, tint = GreenOk, modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }
}
