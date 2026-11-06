package com.tajweed.quran.ui.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.tajweed.quran.data.model.L

enum class AppLanguage(val code: String) {
    EN("en"), UR("ur");

    companion object {
        fun from(code: String?): AppLanguage = entries.firstOrNull { it.code == code } ?: UR
    }
}

val LocalLang = staticCompositionLocalOf { AppLanguage.UR }

fun L.get(lang: AppLanguage): String = if (lang == AppLanguage.UR) ur else en

/** Translate a bilingual [L] value using the current app language. */
@Composable
fun L.t(): String = get(LocalLang.current)

/** Pick English or Urdu text depending on the current app language. */
@Composable
fun tr(en: String, ur: String): String = if (LocalLang.current == AppLanguage.UR) ur else en
