package com.tajweed.quran.ui.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.ui.components.LanguageToggle
import com.tajweed.quran.ui.components.TajweedLogo
import com.tajweed.quran.ui.i18n.AppLanguage
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Gold
import com.tajweed.quran.ui.theme.HeaderBrush
import com.tajweed.quran.ui.theme.NavyDark
import com.tajweed.quran.ui.theme.TextDark

@Composable
fun EnterNameScreen(
    initialName: String,
    language: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    onContinue: (String) -> Unit,
) {
    var name by remember { mutableStateOf(initialName) }
    val canContinue = name.isNotBlank()

    Column(
        Modifier
            .fillMaxSize()
            .background(HeaderBrush)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.weight(1f))
            LanguageToggle(current = language, onChange = onLanguageChange)
        }
        Spacer(Modifier.height(24.dp))
        TajweedLogo(size = 150.dp)
        Spacer(Modifier.height(8.dp))
        Text("Tajweed ul Quran", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("تجوید القرآن", color = Gold, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(28.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                tr("Enter your name", "اپنا نام درج کریں"),
                fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                tr("We will greet you by name.", "ہم آپ کو آپ کے نام سے مخاطب کریں گے۔"),
                fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it.take(30) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                placeholder = { Text(tr("Your name", "آپ کا نام")) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { if (canContinue) onContinue(name) }),
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { onContinue(name) },
                enabled = canContinue,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark, contentColor = Color.White),
            ) {
                Text(tr("Continue", "جاری رکھیں"), fontSize = 17.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
