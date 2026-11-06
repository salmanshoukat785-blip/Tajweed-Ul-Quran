package com.tajweed.quran.data.model

/** A bilingual text: English + Urdu. */
data class L(val en: String, val ur: String)

/** Same text in both languages (letters, numbers, Arabic words). */
fun same(s: String) = L(s, s)

/** One rule / section inside a lesson. */
data class Rule(
    val title: L,
    val body: L,
    val letters: String? = null,
    val examples: List<String> = emptyList(),
)

/** One chapter of the PDF = one lesson/course card on the dashboard. */
data class Lesson(
    val id: String,
    val title: L,
    val subtitle: L,
    val intro: L? = null,
    val rules: List<Rule>,
    val note: L? = null,
)

data class QuizQuestion(
    val lessonId: String,
    val question: L,
    val options: List<L>,
    val correctIndex: Int,
)
