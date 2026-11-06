package com.tajweed.quran.ui.onboarding

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.ui.components.LanguageToggle
import com.tajweed.quran.ui.components.TajweedLogo
import com.tajweed.quran.ui.i18n.AppLanguage
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.HeaderBrush
import com.tajweed.quran.ui.theme.NavyDark
import kotlinx.coroutines.launch

private data class OnbPage(
    val icon: ImageVector,
    val titleEn: String,
    val titleUr: String,
    val textEn: String,
    val textUr: String,
)

/**
 * 3 welcome pages. Every page shows English AND Urdu text plus the salaam.
 * Buttons: Skip + Next, and "Get Started" on the last page.
 */
@Composable
fun OnboardingScreen(
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onFinish: () -> Unit,
) {
    val pages = listOf(
        OnbPage(
            Icons.AutoMirrored.Filled.MenuBook,
            "Welcome", "خوش آمدید",
            "Learn the rules of Tajweed ul Quran step by step.",
            "تجوید القرآن کے قواعد قدم بہ قدم سیکھیں۔",
        ),
        OnbPage(
            Icons.Filled.RecordVoiceOver,
            "Learn with Examples", "مثالوں کے ساتھ سیکھیں",
            "Every rule comes with examples from the Quran.",
            "ہر قاعدہ قرآنی مثالوں کے ساتھ۔",
        ),
        OnbPage(
            Icons.Filled.EmojiEvents,
            "Practice & Progress", "مشق کریں اور آگے بڑھیں",
            "Take quizzes, earn points and keep your daily streak.",
            "کوئز دیں، اسکور حاصل کریں اور روزانہ سیکھنے کی عادت بنائیں۔",
        ),
    )
    val pager = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    val isLast = pager.currentPage == pages.lastIndex

    Column(Modifier.fillMaxSize().background(HeaderBrush).systemBarsPadding()) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TajweedLogo(size = 44.dp)
            Spacer(Modifier.weight(1f))
            LanguageToggle(current = language, onChange = onLanguageChange)
        }

        HorizontalPager(state = pager, modifier = Modifier.weight(1f)) { index ->
            PageContent(pages[index])
        }

        // dots
        Row(
            Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(pages.size) { i ->
                val active = i == pager.currentPage
                Box(
                    Modifier
                        .padding(4.dp)
                        .size(width = if (active) 26.dp else 10.dp, height = 10.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (active) Gold else Color.White.copy(alpha = 0.4f))
                )
            }
        }

        if (isLast) {
            Button(
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = NavyDark),
            ) {
                Text("Get Started  •  شروع کریں", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        } else {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(onClick = onFinish) {
                    Text("Skip  •  چھوڑیں", color = Color.White, fontSize = 16.sp)
                }
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { scope.launch { pager.animateScrollToPage(pager.currentPage + 1) } },
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = NavyDark),
                ) {
                    Text("Next  •  آگے", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun PageContent(page: OnbPage) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            Modifier.size(120.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(page.icon, contentDescription = null, tint = Gold, modifier = Modifier.size(64.dp))
        }
        Spacer(Modifier.height(24.dp))

        // Salaam – English + Urdu
        Text("السلام علیکم", color = Gold, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("Assalamu Alaikum", color = Color.White.copy(alpha = 0.85f), fontSize = 15.sp)
        Spacer(Modifier.height(20.dp))

        Text(
            page.titleEn, color = Color.White, fontSize = 26.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
        )
        Text(
            page.titleUr, color = Color.White, fontSize = 26.sp,
            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(14.dp))
        Text(
            page.textEn, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp,
            textAlign = TextAlign.Center, lineHeight = 24.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            page.textUr, color = Color.White.copy(alpha = 0.9f), fontSize = 17.sp,
            textAlign = TextAlign.Center, lineHeight = 28.sp,
        )
    }
}
