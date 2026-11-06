package com.tajweed.quran.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.data.QuizBank
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.ui.components.ScreenHeader
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.NavyDark
import com.tajweed.quran.ui.theme.RedBad
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

/** quizId == "all" → Self Test (10 mixed questions). Otherwise a lesson id. */
@Composable
fun QuizScreen(vm: AppViewModel, quizId: String, onBack: () -> Unit) {
    var round by remember { mutableIntStateOf(0) }
    val questions = remember(quizId, round) { QuizBank.forQuiz(quizId) }
    var index by remember(round) { mutableIntStateOf(0) }
    var selected by remember(round, index) { mutableStateOf<Int?>(null) }
    var correct by remember(round) { mutableIntStateOf(0) }
    var finished by remember(round) { mutableStateOf(false) }

    val title = if (quizId == "all") tr("Self Test", "سیلف ٹیسٹ")
    else TajweedContent.byId(quizId)?.title?.t() ?: tr("Quiz", "کوئز")

    LaunchedEffect(finished, round) {
        if (finished) vm.recordQuiz(quizId, correct, questions.size)
    }

    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenHeader(title, onBack = onBack)

        when {
            questions.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(tr("No questions yet.", "ابھی کوئی سوال نہیں ہے۔"), color = TextMuted)
            }

            finished -> ResultView(
                correct = correct,
                total = questions.size,
                onRetry = { round += 1 },
                onBack = onBack,
            )

            else -> {
                val q = questions[index]
                val order = remember(round, index) { q.options.indices.shuffled() }
                val answered = selected != null
                val isLast = index == questions.lastIndex

                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        "${index + 1} / ${questions.size}",
                        color = TextMuted, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                    )
                    LinearProgressIndicator(
                        progress = { (index + if (answered) 1 else 0) / questions.size.toFloat() },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = Blue,
                        trackColor = Color(0xFFD8E3F5),
                    )

                    Card(
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(2.dp),
                    ) {
                        Text(
                            q.question.t(),
                            Modifier.padding(18.dp),
                            fontSize = 19.sp, lineHeight = 30.sp, fontWeight = FontWeight.Bold, color = TextDark,
                            style = TextStyle(textDirection = TextDirection.Content),
                        )
                    }

                    order.forEach { optionIndex ->
                        val state = when {
                            !answered -> OptionState.Idle
                            optionIndex == q.correctIndex -> OptionState.Correct
                            optionIndex == selected -> OptionState.Wrong
                            else -> OptionState.Dimmed
                        }
                        OptionRow(q.options[optionIndex].t(), state) {
                            if (!answered) {
                                selected = optionIndex
                                if (optionIndex == q.correctIndex) correct += 1
                            }
                        }
                    }
                }

                if (answered) {
                    Button(
                        onClick = { if (isLast) finished = true else index += 1 },
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(16.dp)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                    ) {
                        Text(
                            if (isLast) tr("Finish", "ختم کریں") else tr("Next", "اگلا سوال"),
                            fontWeight = FontWeight.Bold, fontSize = 16.sp,
                        )
                    }
                } else {
                    Spacer(Modifier.navigationBarsPadding().height(84.dp))
                }
            }
        }
    }
}

private enum class OptionState { Idle, Correct, Wrong, Dimmed }

@Composable
private fun OptionRow(text: String, state: OptionState, onClick: () -> Unit) {
    val bg = when (state) {
        OptionState.Correct -> Color(0xFFE2F6EA)
        OptionState.Wrong -> Color(0xFFFCE4E4)
        else -> Color.White
    }
    val border = when (state) {
        OptionState.Correct -> GreenOk
        OptionState.Wrong -> RedBad
        else -> Color(0xFFD0DCF0)
    }
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .clickable(enabled = state == OptionState.Idle) { onClick() }
            .border(1.5.dp, border, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text,
            Modifier.weight(1f),
            fontSize = 17.sp, lineHeight = 26.sp,
            color = if (state == OptionState.Dimmed) TextMuted else TextDark,
            style = TextStyle(textDirection = TextDirection.Content),
        )
        when (state) {
            OptionState.Correct -> Icon(Icons.Filled.Check, null, tint = GreenOk)
            OptionState.Wrong -> Icon(Icons.Filled.Close, null, tint = RedBad)
            else -> {}
        }
    }
}


@Composable
private fun ResultView(correct: Int, total: Int, onRetry: () -> Unit, onBack: () -> Unit) {
    val pct = if (total == 0) 0 else correct * 100 / total
    val message = when {
        pct >= 90 -> tr("Excellent! MashaAllah", "بہت خوب! ماشاء اللہ")
        pct >= 70 -> tr("Very good!", "بہت اچھا!")
        pct >= 50 -> tr("Good, keep practicing", "اچھا، مشق جاری رکھیں")
        else -> tr("Revise the lesson and try again", "سبق دوبارہ پڑھیں اور پھر کوشش کریں")
    }
    Column(
        Modifier.fillMaxSize().navigationBarsPadding().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(Icons.Filled.EmojiEvents, null, tint = Gold, modifier = Modifier.size(90.dp))
        Spacer(Modifier.height(12.dp))
        Text("$pct%", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Blue)
        Text(
            tr("$correct of $total correct", "$total میں سے $correct درست"),
            fontSize = 18.sp, color = TextDark,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "+${correct * 10} " + tr("points", "پوائنٹس"),
            fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GreenOk,
        )
        Spacer(Modifier.height(10.dp))
        Text(message, fontSize = 17.sp, color = TextMuted, textAlign = TextAlign.Center)
        Spacer(Modifier.height(28.dp))
        Button(
            onClick = onRetry,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
        ) { Text(tr("Try again", "دوبارہ کوشش کریں"), fontWeight = FontWeight.Bold) }
        Spacer(Modifier.height(10.dp))
        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(14.dp),
        ) { Text(tr("Back", "واپس"), fontWeight = FontWeight.Bold) }
    }
}
