package com.tajweed.quran.ui.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tajweed.quran.ui.components.LanguageToggle
import com.tajweed.quran.ui.components.ScreenHeader
import com.tajweed.quran.ui.components.TajweedLogo
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.NavyDark
import com.tajweed.quran.ui.theme.RedBad
import com.tajweed.quran.ui.theme.TextDark
import com.tajweed.quran.ui.theme.TextMuted
import com.tajweed.quran.viewmodel.AppViewModel

@Composable
fun ProfileScreen(vm: AppViewModel) {
    var name by remember(vm.userName) { mutableStateOf(vm.userName) }
    var showReset by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(Bg)) {
        ScreenHeader(tr("Profile", "پروفائل"))
        Column(
            Modifier.verticalScroll(rememberScrollState()).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                Modifier.size(88.dp).clip(CircleShape).background(Blue),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    vm.userName.take(1).uppercase().ifBlank { "؟" },
                    color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Bold,
                )
            }
            Text(vm.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)

            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(tr("Your name", "آپ کا نام"), fontWeight = FontWeight.Bold, color = TextDark)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it.take(30) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )
                    Button(
                        onClick = { vm.saveName(name) },
                        enabled = name.isNotBlank() && name.trim() != vm.userName,
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                    ) { Text(tr("Save", "محفوظ کریں"), fontWeight = FontWeight.Bold) }
                }
            }

            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        tr("Language", "زبان"),
                        fontWeight = FontWeight.Bold, color = TextDark, modifier = Modifier.weight(1f),
                    )
                    LanguageToggle(current = vm.language, onChange = vm::setLanguage, dark = false)
                }
            }

            OutlinedButton(
                onClick = { showReset = true },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(14.dp),
            ) {
                Icon(Icons.Filled.Refresh, null, tint = RedBad, modifier = Modifier.size(20.dp))
                Spacer(Modifier.size(8.dp))
                Text(tr("Reset progress", "پیش رفت ختم کریں"), color = RedBad, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(8.dp))
            TajweedLogo(size = 70.dp)
            Text("Tajweed ul Quran  •  تجوید القرآن", color = TextMuted, fontSize = 13.sp)
        }
    }

    if (showReset) {
        AlertDialog(
            onDismissRequest = { showReset = false },
            title = { Text(tr("Reset progress?", "پیش رفت ختم کریں؟")) },
            text = {
                Text(
                    tr(
                        "Your score, streak, completed and favorite lessons will be cleared.",
                        "آپ کا اسکور، مسلسل دن، مکمل اور پسندیدہ اسباق ختم ہو جائیں گے۔",
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = { vm.resetProgress(); showReset = false }) {
                    Text(tr("Reset", "ختم کریں"), color = RedBad, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showReset = false }) { Text(tr("Cancel", "منسوخ")) }
            },
        )
    }
}
