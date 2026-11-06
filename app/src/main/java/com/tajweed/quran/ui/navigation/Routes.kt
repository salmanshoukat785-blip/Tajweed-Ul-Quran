package com.tajweed.quran.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val NAME = "name"

    const val HOME = "home"
    const val LESSONS = "lessons"
    const val PRACTICE = "practice"
    const val SCORE = "score"
    const val PROFILE = "profile"

    const val LESSON = "lesson/{id}"
    const val QUIZ = "quiz/{id}"

    fun lesson(id: String) = "lesson/$id"
    fun quiz(id: String) = "quiz/$id"

    val tabs = listOf(HOME, LESSONS, PRACTICE, SCORE, PROFILE)
}
