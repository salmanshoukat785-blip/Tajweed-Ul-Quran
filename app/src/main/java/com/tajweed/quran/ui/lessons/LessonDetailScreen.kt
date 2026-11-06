package com.tajweed.quran.ui.lessons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.data.model.Rule
import com.tajweed.quran.ui.components.ArabicText
import com.tajweed.quran.ui.components.MakharijDiagram
import com.tajweed.quran.ui.components.ScreenHeader
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.NavyDark
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

@Composable
fun LessonDetailScreen(
    vm: AppViewModel,
    lessonId: String,
    onBack: () -> Unit,
    onStartQuiz: (String) -> Unit,
) {
    val lesson = TajweedContent.byId(lessonId)
    if (lesson == null) {
        LaunchedEffect(Unit) { onBack() }
        return
    }
    val isFav = lessonId in vm.favorites
    val isDone = lessonId in vm.completed

    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenHeader(
            title = lesson.title.t(),
            onBack = onBack,
            trailing = {
                IconButton(onClick = { vm.toggleFavorite(lessonId) }) {
                    Icon(
                        if (isFav) Icons.Filled.Star else Icons.Filled.StarBorder,
                        contentDescription = null, tint = Gold,
                    )
                }
            },
        )

        LazyColumn(
            Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            lesson.intro?.let { intro ->
                item {
                    Card(
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEBFF)),
                    ) {
                        Text(intro.t(), Modifier.padding(16.dp), fontSize = 15.sp, lineHeight = 26.sp, color = TextDark)
                    }
                }
            }

            if (lessonId == "makharij") {
                item { MakharijDiagram() }
            }

            itemsIndexed(lesson.rules) { index, rule ->
                RuleCard(index + 1, rule)
            }

            lesson.note?.let { note ->
                item {
                    Card(
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3D6)),
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(tr("Note", "نوٹ"), fontWeight = FontWeight.Bold, color = Color(0xFFB36B00), fontSize = 15.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(note.t(), fontSize = 15.sp, lineHeight = 26.sp, color = TextDark)
                        }
                    }
                }
            }
        }

        // bottom actions
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .navigationBarsPadding()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            OutlinedButton(
                onClick = { onStartQuiz(lessonId) },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(14.dp),
            ) {
                Icon(Icons.Filled.Quiz, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.size(6.dp))
                Text(tr("Quiz", "کوئز"), fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { vm.markCompleted(lessonId) },
                enabled = !isDone,
                modifier = Modifier.weight(1.5f).height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NavyDark,
                    disabledContainerColor = GreenOk,
                    disabledContentColor = Color.White,
                ),
            ) {
                if (isDone) {
                    Icon(Icons.Filled.CheckCircle, null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.size(6.dp))
                }
                Text(
                    if (isDone) tr("Completed", "مکمل") else tr("Mark as completed", "سبق مکمل کریں"),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun RuleCard(number: Int, rule: Rule) {
    Card(
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(28.dp).clip(CircleShape).background(Blue),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("$number", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(Modifier.size(10.dp))
                Text(rule.title.t(), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Blue)
            }

            rule.letters?.let { letters ->
                Box(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color(0xFFFFF8D6)).padding(10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    ArabicText(letters, size = 26.sp)
                }
            }

            Text(rule.body.t(), fontSize = 15.sp, lineHeight = 26.sp, color = TextDark)

            if (rule.examples.isNotEmpty()) {
                Column(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color(0xFFEAF2FF)).padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(tr("Examples", "مثالیں"), fontSize = 12.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    rule.examples.forEach { ArabicText(it, size = 28.sp) }
                }
            }
        }
    }
}
