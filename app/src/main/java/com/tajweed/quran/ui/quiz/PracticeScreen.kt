package com.tajweed.quran.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.EditNote
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
import com.tajweed.quran.data.QuizBank
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.ui.components.ScreenHeader
import com.tajweed.quran.ui.components.lessonBrush
import com.tajweed.quran.ui.components.lessonIcon
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.CourseBrushes
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

@Composable
fun PracticeScreen(vm: AppViewModel, onStartQuiz: (String) -> Unit) {
    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenHeader(tr("Practice", "پریکٹس"))
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Self test
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(CourseBrushes[3])
                    .clickable { onStartQuiz("all") }
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Filled.EditNote, null, tint = Color.White, modifier = Modifier.size(44.dp))
                Spacer(Modifier.size(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(tr("Self Test", "سیلف ٹیسٹ"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(
                        tr("10 mixed questions from all chapters", "تمام ابواب سے 10 متعدد سوالات"),
                        color = Color.White.copy(alpha = 0.95f), fontSize = 13.sp,
                    )
                    vm.bestScores["all"]?.let {
                        Text(tr("Best: $it%", "بہترین: $it%"), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.White)
            }

            Spacer(Modifier.height(4.dp))
            Text(tr("Quiz by chapter", "باب وار کوئز"), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)

            TajweedContent.lessons.forEach { lesson ->
                Card(
                    Modifier.fillMaxWidth().clickable { onStartQuiz(lesson.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp),
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            Modifier.size(44.dp).clip(CircleShape).background(lessonBrush(lesson.id)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(lessonIcon(lesson.id), null, tint = Color.White, modifier = Modifier.size(24.dp))
                        }
                        Spacer(Modifier.size(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(lesson.title.t(), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                            Text(
                                "${QuizBank.countFor(lesson.id)} " + tr("questions", "سوالات"),
                                fontSize = 12.sp, color = TextMuted,
                            )
                        }
                        vm.bestScores[lesson.id]?.let {
                            Text("$it%", fontWeight = FontWeight.Bold, color = if (it >= 70) GreenOk else Blue)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = TextMuted)
                    }
                }
            }
        }
    }
}
