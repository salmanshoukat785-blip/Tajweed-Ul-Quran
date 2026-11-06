package com.tajweed.quran.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.data.model.Lesson
import com.tajweed.quran.ui.components.MosqueSilhouette
import com.tajweed.quran.ui.components.ProgressRing
import com.tajweed.quran.ui.components.TajweedLogo
import com.tajweed.quran.ui.components.lessonBrush
import com.tajweed.quran.ui.components.lessonIcon
import com.tajweed.quran.ui.components.overlapTop
import com.tajweed.quran.ui.i18n.t
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.CourseBrushes
import com.tajweed.quran.ui.theme.GreenOk
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.HeaderBrush
import com.tajweed.quran.ui.theme.NavyDark
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    vm: AppViewModel,
    onOpenLesson: (String) -> Unit,
    onOpenQuiz: (String) -> Unit,
    onOpenLessons: () -> Unit,
    onOpenPractice: () -> Unit,
    onOpenScore: () -> Unit,
    onOpenProfile: () -> Unit,
) {
    val lessons = TajweedContent.lessons
    val today = vm.todayLesson

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

        // ── Header ───────────────────────────────────────────────
        Box(Modifier.fillMaxWidth().background(HeaderBrush)) {
            MosqueSilhouette(
                Modifier.align(Alignment.BottomStart).size(width = 190.dp, height = 110.dp)
            )
            Column(Modifier.statusBarsPadding().padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 52.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(44.dp))
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        TajweedLogo(size = 64.dp)
                        Text("Tajweed ul Quran", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("تجوید القرآن", color = Gold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(
                        onClick = onOpenProfile,
                        modifier = Modifier.size(44.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.18f)),
                    ) {
                        Icon(Icons.Filled.Settings, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(Modifier.height(10.dp))
                val name = vm.userName
                Text(
                    if (name.isBlank()) tr("Assalamu Alaikum!", "السلام علیکم!")
                    else tr("Assalamu Alaikum, $name!", "السلام علیکم، $name!"),
                    color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold,
                )
                Text(
                    tr("Let's continue learning Tajweed", "آئیں تجوید سیکھنے کا سفر جاری رکھیں"),
                    color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp,
                )
            }
        }

        // ── Today's lesson card ──────────────────────────────────
        Card(
            Modifier.padding(horizontal = 16.dp).fillMaxWidth().overlapTop(34.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(6.dp),
        ) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    ProgressRing(vm.progress)
                    Spacer(Modifier.height(6.dp))
                    Text(tr("Learning progress", "تعلیمی پیش رفت"), fontSize = 12.sp, color = TextMuted, textAlign = TextAlign.Center)
                }
                Column(Modifier.weight(1.6f).padding(horizontal = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        tr("Today's Lesson", "آج کا سبق"),
                        color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.clip(RoundedCornerShape(50)).background(Blue).padding(horizontal = 12.dp, vertical = 4.dp),
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(today.title.t(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark, textAlign = TextAlign.Center)
                    Text(today.subtitle.t(), fontSize = 12.sp, color = TextMuted, textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        Modifier.clip(RoundedCornerShape(50)).background(Blue).clickable { onOpenLesson(today.id) }.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(tr("Start Lesson", "سبق شروع کریں"), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
                Box(
                    Modifier.size(64.dp).clip(RoundedCornerShape(18.dp)).background(Color(0xFFE6F0FF)),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.AutoMirrored.Filled.MenuBook, null, tint = Blue, modifier = Modifier.size(42.dp))
                }
            }
        }

        // ── Stats ────────────────────────────────────────────────
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            StatCard(Modifier.weight(1f), "${vm.totalScore}", tr("Total Score", "کل اسکور"),
                Icons.Filled.EmojiEvents, Color(0xFFE59B00), Color(0xFFFFF3D6))
            StatCard(Modifier.weight(1f), "${vm.streak}", tr("Day streak", "دنوں کی مسلسل تعلیم"),
                Icons.Filled.LocalFireDepartment, Color(0xFFEF6C00), Color(0xFFE4F7E8))
            StatCard(Modifier.weight(1f), "${vm.favorites.size}", tr("Favorite lessons", "پسندیدہ اسباق"),
                Icons.Filled.Bookmark, Blue, Color(0xFFEBE2FB))
        }

        // ── Courses ──────────────────────────────────────────────
        Spacer(Modifier.height(18.dp))
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(tr("Our Courses", "ہمارے کورسز"), fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark, modifier = Modifier.weight(1f))
            Text(
                tr("View all", "سب دیکھیں"),
                color = Blue, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                modifier = Modifier.clip(RoundedCornerShape(50)).background(Color(0xFFDDEBFF)).clickable { onOpenLessons() }.padding(horizontal = 14.dp, vertical = 6.dp),
            )
        }
        Spacer(Modifier.height(10.dp))

        lessons.chunked(3).forEach { row ->
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                row.forEach { lesson ->
                    CourseCard(lesson, done = lesson.id in vm.completed, modifier = Modifier.weight(1f)) {
                        onOpenLesson(lesson.id)
                    }
                }
                repeat(3 - row.size) { Spacer(Modifier.weight(1f)) }
            }
            Spacer(Modifier.height(10.dp))
        }

        // ── Self test / Quiz / My score ──────────────────────────
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            ActionCard(Modifier.weight(1f), CourseBrushes[3], Icons.Filled.EditNote,
                tr("Self Test", "سیلف ٹیسٹ"), tr("Check your preparation", "اپنی تیاری جانچیں"),
                tr("Mixed", "متعدد ٹیسٹ")) { onOpenQuiz("all") }
            ActionCard(Modifier.weight(1f), CourseBrushes[4], Icons.Filled.Quiz,
                tr("Quiz", "کوئز"), tr("Test your knowledge", "اپنے علم کا امتحان لیں"),
                "${com.tajweed.quran.data.QuizBank.all.size} " + tr("questions", "سوالات")) { onOpenPractice() }
            ActionCard(Modifier.weight(1f), CourseBrushes[5], Icons.Filled.BarChart,
                tr("My Score", "میرا اسکور"), tr("See your performance", "اپنی کارکردگی دیکھیں"),
                tr("Details", "تفصیلات")) { onOpenScore() }
        }

        // ── Daily goal ───────────────────────────────────────────
        Spacer(Modifier.height(14.dp))
        Row(
            Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFDDEBFF))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Filled.Lightbulb, null, tint = Gold, modifier = Modifier.size(32.dp))
            Spacer(Modifier.size(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    tr("Today's goal: complete 1 lesson", "آج کا مقصد: 1 سبق مکمل کرنا"),
                    fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark,
                )
                if (vm.goalDoneToday) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CheckCircle, null, tint = GreenOk, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.size(4.dp))
                        Text(tr("Goal completed!", "مقصد مکمل!"), fontSize = 12.sp, color = GreenOk)
                    }
                }
            }
            Text(
                tr("Daily lesson", "روزانہ کا سبق"),
                color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp,
                modifier = Modifier.clip(RoundedCornerShape(50)).background(NavyDark).clickable { onOpenLesson(today.id) }.padding(horizontal = 14.dp, vertical = 8.dp),
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun StatCard(
    modifier: Modifier, value: String, label: String,
    icon: ImageVector, tint: Color, bg: Color,
) {
    Column(
        modifier.clip(RoundedCornerShape(20.dp)).background(bg).padding(vertical = 12.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(30.dp))
        Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextDark)
        Text(label, fontSize = 11.sp, color = TextMuted, textAlign = TextAlign.Center, maxLines = 2)
    }
}

@Composable
private fun CourseCard(lesson: Lesson, done: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(lessonBrush(lesson.id))
            .clickable { onClick() }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
            Icon(lessonIcon(lesson.id), null, tint = Color.White, modifier = Modifier.size(34.dp))
        }
        Spacer(Modifier.height(4.dp))
        Text(
            lesson.title.t(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp,
            textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis,
        )
        Text(
            lesson.subtitle.t(), color = Color.White.copy(alpha = 0.9f), fontSize = 10.sp,
            textAlign = TextAlign.Center, maxLines = 2, overflow = TextOverflow.Ellipsis, lineHeight = 14.sp,
        )
        Spacer(Modifier.weight(1f))
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(50)).background(Color.White).padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "${lesson.rules.size} " + tr("rules", "قواعد"),
                fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.weight(1f),
            )
            Icon(
                if (done) Icons.Filled.CheckCircle else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                null, tint = if (done) GreenOk else Blue, modifier = Modifier.size(18.dp),
            )
        }
    }
}

@Composable
private fun ActionCard(
    modifier: Modifier, brush: Brush, icon: ImageVector,
    title: String, subtitle: String, pill: String, onClick: () -> Unit,
) {
    Column(
        modifier
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(brush)
            .clickable { onClick() }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(icon, null, tint = Color.White, modifier = Modifier.size(38.dp))
        Spacer(Modifier.height(4.dp))
        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center)
        Text(subtitle, color = Color.White.copy(alpha = 0.92f), fontSize = 10.sp, textAlign = TextAlign.Center, maxLines = 2, lineHeight = 14.sp)
        Spacer(Modifier.weight(1f))
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(50)).background(Color.White).padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(pill, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Blue, modifier = Modifier.size(18.dp))
        }
    }
}
