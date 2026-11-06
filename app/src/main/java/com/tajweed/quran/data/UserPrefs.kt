package com.tajweed.quran.data

import android.content.Context

/** Small SharedPreferences wrapper that stores everything about the user. */
class UserPrefs(context: Context) {
    private val sp = context.getSharedPreferences("tajweed_prefs", Context.MODE_PRIVATE)

    var language: String
        get() = sp.getString("language", "ur") ?: "ur"
        set(v) { sp.edit().putString("language", v).apply() }

    var userName: String
        get() = sp.getString("user_name", "") ?: ""
        set(v) { sp.edit().putString("user_name", v).apply() }

    var onboardingDone: Boolean
        get() = sp.getBoolean("onboarding_done", false)
        set(v) { sp.edit().putBoolean("onboarding_done", v).apply() }

    var totalScore: Int
        get() = sp.getInt("total_score", 0)
        set(v) { sp.edit().putInt("total_score", v).apply() }

    var correctAnswers: Int
        get() = sp.getInt("correct_answers", 0)
        set(v) { sp.edit().putInt("correct_answers", v).apply() }

    var answeredTotal: Int
        get() = sp.getInt("answered_total", 0)
        set(v) { sp.edit().putInt("answered_total", v).apply() }

    var quizzesTaken: Int
        get() = sp.getInt("quizzes_taken", 0)
        set(v) { sp.edit().putInt("quizzes_taken", v).apply() }

    var streak: Int
        get() = sp.getInt("streak", 0)
        set(v) { sp.edit().putInt("streak", v).apply() }

    var lastActiveDate: String
        get() = sp.getString("last_active", "") ?: ""
        set(v) { sp.edit().putString("last_active", v).apply() }

    var goalDate: String
        get() = sp.getString("goal_date", "") ?: ""
        set(v) { sp.edit().putString("goal_date", v).apply() }

    var completed: Set<String>
        get() = sp.getStringSet("completed", emptySet())?.toSet() ?: emptySet()
        set(v) { sp.edit().putStringSet("completed", v.toSet()).apply() }

    var favorites: Set<String>
        get() = sp.getStringSet("favorites", emptySet())?.toSet() ?: emptySet()
        set(v) { sp.edit().putStringSet("favorites", v.toSet()).apply() }

    /** Best quiz percentage for a quiz key, or -1 if never taken. */
    fun best(key: String): Int = sp.getInt("best_$key", -1)
    fun setBest(key: String, value: Int) { sp.edit().putInt("best_$key", value).apply() }

    /** Clears progress but keeps name, language and onboarding state. */
    fun clearProgress(bestKeys: List<String>) {
        val e = sp.edit()
        listOf("total_score", "correct_answers", "answered_total", "quizzes_taken",
            "streak", "last_active", "goal_date", "completed", "favorites").forEach { e.remove(it) }
        bestKeys.forEach { e.remove("best_$it") }
        e.apply()
    }
}
