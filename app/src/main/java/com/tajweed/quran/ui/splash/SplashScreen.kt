package com.tajweed.quran.ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.ui.components.TajweedLogo
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.HeaderBrush
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var started by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (started) 1f else 0.6f, tween(900), label = "scale")
    val alpha by animateFloatAsState(if (started) 1f else 0f, tween(900), label = "alpha")

    LaunchedEffect(Unit) {
        started = true
        delay(2400)
        onFinished()
    }

    Box(
        Modifier.fillMaxSize().background(HeaderBrush),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            Modifier.scale(scale).alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TajweedLogo(size = 180.dp)
            Spacer(Modifier.height(18.dp))
            Text("Tajweed ul Quran", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("تجوید القرآن", color = Gold, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
        Text(
            "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيمِ",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 32.dp)
                .alpha(alpha),
        )
    }
}
