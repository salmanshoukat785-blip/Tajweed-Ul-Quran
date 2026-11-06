package com.tajweed.quran.ui.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.ui.components.ProgressRing
import com.tajweed.quran.ui.components.ScreenHeader
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

@Composable
fun ScoreScreen(vm: AppViewModel) {
    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenHeader(tr("My Score", "میرا اسکور"))
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp),
            ) {
                Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    ProgressRing(vm.progress, Modifier.size(96.dp))
                    Spacer(Modifier.size(16.dp))
                    Column {
                        Text(tr("Learning progress", "تعلیمی پیش رفت"), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                        Text(
                            "${vm.completedCount} / ${TajweedContent.lessons.size} " + tr("lessons completed", "اسباق مکمل"),
                            color = TextMuted, fontSize = 14.sp,
                        )
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Mini(Modifier.weight(1f), Icons.Filled.EmojiEvents, "${vm.totalScore}", tr("Total score", "کل اسکور"), Color(0xFFFFF3D6), Color(0xFFE59B00))
                Mini(Modifier.weight(1f), Icons.Filled.LocalFireDepartment, "${vm.streak}", tr("Day streak", "مسلسل دن"), Color(0xFFFFE9DB), Color(0xFFEF6C00))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Mini(Modifier.weight(1f), Icons.Filled.Quiz, "${vm.quizzesTaken}", tr("Quizzes taken", "دیے گئے کوئز"), Color(0xFFE6F0FF), Blue)
                Mini(Modifier.weight(1f), Icons.Filled.CheckCircle, "${vm.accuracy}%", tr("Accuracy", "درستگی"), Color(0xFFE2F6EA), GreenOk)
            }

            Text(tr("Best quiz results", "بہترین نتائج"), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)

            BestRow(tr("Self Test", "سیلف ٹیسٹ"), vm.bestScores["all"])
            TajweedContent.lessons.forEach { lesson ->
                BestRow(lesson.title.t(), vm.bestScores[lesson.id])
            }
        }
    }
}

@Composable
private fun Mini(modifier: Modifier, icon: ImageVector, value: String, label: String, bg: Color, tint: Color) {
    Column(
        modifier.clip(RoundedCornerShape(18.dp)).background(bg).padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(30.dp))
        Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(label, fontSize = 12.sp, color = TextMuted, textAlign = TextAlign.Center)
    }
}

@Composable
private fun BestRow(label: String, best: Int?) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(label, Modifier.weight(1f), fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextDark)
                Text(
                    if (best == null) "—" else "$best%",
                    fontWeight = FontWeight.Bold,
                    color = if (best == null) TextMuted else if (best >= 70) GreenOk else Blue,
                )
            }
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { (best ?: 0) / 100f },
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = if ((best ?: 0) >= 70) GreenOk else Blue,
                trackColor = Color(0xFFE3ECFB),
            )
        }
    }
}
