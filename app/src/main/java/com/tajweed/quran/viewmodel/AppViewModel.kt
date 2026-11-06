package com.tajweed.quran.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.tajweed.quran.data.TajweedContent
import com.tajweed.quran.data.UserPrefs
import com.tajweed.quran.data.model.Lesson
import com.tajweed.quran.ui.i18n.AppLanguage
import java.time.LocalDate

/** Single ViewModel that holds the whole app state (MVVM). */
class AppViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs = UserPrefs(app)
    private val bestKeys: List<String> = listOf("all") + TajweedContent.lessons.map { it.id }

    var language by mutableStateOf(AppLanguage.from(prefs.language)); private set
    var userName by mutableStateOf(prefs.userName); private set
    var onboardingDone by mutableStateOf(prefs.onboardingDone); private set
    var completed by mutableStateOf(prefs.completed); private set
    var favorites by mutableStateOf(prefs.favorites); private set
    var totalScore by mutableIntStateOf(prefs.totalScore); private set
    var correctAnswers by mutableIntStateOf(prefs.correctAnswers); private set
    var answeredTotal by mutableIntStateOf(prefs.answeredTotal); private set
    var quizzesTaken by mutableIntStateOf(prefs.quizzesTaken); private set
    var streak by mutableIntStateOf(0); private set
    var goalDoneToday by mutableStateOf(false); private set
    var bestScores by mutableStateOf(loadBest()); private set

    init {
        updateStreak()
    }

    // ── derived values ────────────────────────────────────────────────
    val progress: Float
        get() {
            val total = TajweedContent.lessons.size
            val done = TajweedContent.lessons.count { it.id in completed }
            return if (total == 0) 0f else done.toFloat() / total
        }

    val completedCount: Int
        get() = TajweedContent.lessons.count { it.id in completed }

    /** First lesson that is not completed yet (or the first one if all are done). */
    val todayLesson: Lesson
        get() = TajweedContent.lessons.firstOrNull { it.id !in completed }
            ?: TajweedContent.lessons.first()

    val accuracy: Int
        get() = if (answeredTotal == 0) 0 else correctAnswers * 100 / answeredTotal

    // ── actions ───────────────────────────────────────────────────────
   @JvmName("applyLanguage") fun setLanguage(lang: AppLanguage) {
        language = lang
        prefs.language = lang.code
    }

    fun saveName(name: String) {
        userName = name.trim()
        prefs.userName = userName
    }

    fun finishOnboarding() {
        onboardingDone = true
        prefs.onboardingDone = true
    }

    fun toggleFavorite(lessonId: String) {
        favorites = if (lessonId in favorites) favorites - lessonId else favorites + lessonId
        prefs.favorites = favorites
    }

    fun markCompleted(lessonId: String) {
        completed = completed + lessonId
        prefs.completed = completed
        val today = LocalDate.now().toString()
        prefs.goalDate = today
        goalDoneToday = true
    }

    fun recordQuiz(key: String, correct: Int, total: Int) {
        if (total <= 0) return
        totalScore += correct * 10
        correctAnswers += correct
        answeredTotal += total
        quizzesTaken += 1
        prefs.totalScore = totalScore
        prefs.correctAnswers = correctAnswers
        prefs.answeredTotal = answeredTotal
        prefs.quizzesTaken = quizzesTaken

        val pct = correct * 100 / total
        if (pct > (bestScores[key] ?: -1)) {
            prefs.setBest(key, pct)
            bestScores = bestScores + (key to pct)
        }
    }

    fun resetProgress() {
        prefs.clearProgress(bestKeys)
        completed = emptySet()
        favorites = emptySet()
        totalScore = 0
        correctAnswers = 0
        answeredTotal = 0
        quizzesTaken = 0
        bestScores = emptyMap()
        goalDoneToday = false
        prefs.streak = 0
        prefs.lastActiveDate = ""
        updateStreak()
    }

    // ── helpers ───────────────────────────────────────────────────────
    private fun loadBest(): Map<String, Int> =
        bestKeys.associateWith { prefs.best(it) }.filterValues { it >= 0 }

    private fun updateStreak() {
        val today = LocalDate.now()
        val last = prefs.lastActiveDate
        if (last != today.toString()) {
            prefs.streak = if (last == today.minusDays(1).toString()) prefs.streak + 1 else 1
            prefs.lastActiveDate = today.toString()
        }
        streak = prefs.streak
        goalDoneToday = prefs.goalDate == today.toString()
    }
}
