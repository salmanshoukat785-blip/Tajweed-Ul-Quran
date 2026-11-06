package com.tajweed.quran.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tajweed.quran.ui.home.HomeScreen
import com.tajweed.quran.ui.i18n.AppLanguage
import com.tajweed.quran.ui.i18n.LocalLang
import com.tajweed.quran.ui.i18n.tr
import com.tajweed.quran.ui.lessons.LessonDetailScreen
import com.tajweed.quran.ui.lessons.LessonsScreen
import com.tajweed.quran.ui.name.EnterNameScreen
import com.tajweed.quran.ui.onboarding.OnboardingScreen
import com.tajweed.quran.ui.profile.ProfileScreen
import com.tajweed.quran.ui.quiz.PracticeScreen
import com.tajweed.quran.ui.quiz.QuizScreen
import com.tajweed.quran.ui.score.ScoreScreen
import com.tajweed.quran.ui.splash.SplashScreen
import com.tajweed.quran.ui.theme.Bg
import com.tajweed.quran.ui.theme.Blue
import com.tajweed.quran.ui.theme.TajweedTheme
import com.tajweed.quran.viewmodel.AppViewModel

private data class TabItem(val route: String, val en: String, val ur: String, val icon: ImageVector)

private val tabItems = listOf(
    TabItem(Routes.HOME, "Home", "ہوم", Icons.Filled.Home),
    TabItem(Routes.LESSONS, "Lessons", "اسباق", Icons.AutoMirrored.Filled.MenuBook),
    TabItem(Routes.PRACTICE, "Practice", "پریکٹس", Icons.Filled.Edit),
    TabItem(Routes.SCORE, "Score", "اسکور", Icons.Filled.EmojiEvents),
    TabItem(Routes.PROFILE, "Profile", "پروفائل", Icons.Filled.Person),
)

private fun NavHostController.goTab(route: String) {
    navigate(route) {
        popUpTo(Routes.HOME) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun TajweedApp(vm: AppViewModel = viewModel()) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val route = backStack?.destination?.route

    CompositionLocalProvider(
        LocalLang provides vm.language,
        LocalLayoutDirection provides
            if (vm.language == AppLanguage.UR) LayoutDirection.Rtl else LayoutDirection.Ltr,
    ) {
        TajweedTheme {
            Scaffold(
                containerColor = Bg,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                bottomBar = {
                    if (route in Routes.tabs) {
                        NavigationBar(containerColor = Color.White) {
                            tabItems.forEach { item ->
                                NavigationBarItem(
                                    selected = route == item.route,
                                    onClick = { if (route != item.route) nav.goTab(item.route) },
                                    icon = { Icon(item.icon, contentDescription = null) },
                                    label = { Text(tr(item.en, item.ur), fontSize = 11.sp) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color.White,
                                        selectedTextColor = Blue,
                                        indicatorColor = Blue,
                                    ),
                                )
                            }
                        }
                    }
                },
            ) { inner ->
                NavHost(
                    navController = nav,
                    startDestination = Routes.SPLASH,
                    modifier = Modifier.padding(inner),
                ) {
                    composable(Routes.SPLASH) {
                        SplashScreen(onFinished = {
                            val dest = when {
                                !vm.onboardingDone -> Routes.ONBOARDING
                                vm.userName.isBlank() -> Routes.NAME
                                else -> Routes.HOME
                            }
                            nav.navigate(dest) { popUpTo(Routes.SPLASH) { inclusive = true } }
                        })
                    }
                    composable(Routes.ONBOARDING) {
                        OnboardingScreen(
                            language = vm.language,
                            onLanguageChange = vm::setLanguage,
                            onFinish = {
                                vm.finishOnboarding()
                                nav.navigate(Routes.NAME) { popUpTo(Routes.ONBOARDING) { inclusive = true } }
                            },
                        )
                    }
                    composable(Routes.NAME) {
                        EnterNameScreen(
                            initialName = vm.userName,
                            language = vm.language,
                            onLanguageChange = vm::setLanguage,
                            onContinue = { name ->
                                vm.saveName(name)
                                nav.navigate(Routes.HOME) { popUpTo(Routes.NAME) { inclusive = true } }
                            },
                        )
                    }

                    composable(Routes.HOME) {
                        HomeScreen(
                            vm = vm,
                            onOpenLesson = { nav.navigate(Routes.lesson(it)) },
                            onOpenQuiz = { nav.navigate(Routes.quiz(it)) },
                            onOpenLessons = { nav.goTab(Routes.LESSONS) },
                            onOpenPractice = { nav.goTab(Routes.PRACTICE) },
                            onOpenScore = { nav.goTab(Routes.SCORE) },
                            onOpenProfile = { nav.goTab(Routes.PROFILE) },
                        )
                    }
                    composable(Routes.LESSONS) {
                        LessonsScreen(vm) { nav.navigate(Routes.lesson(it)) }
                    }
                    composable(Routes.PRACTICE) {
                        PracticeScreen(vm) { nav.navigate(Routes.quiz(it)) }
                    }
                    composable(Routes.SCORE) { ScoreScreen(vm) }
                    composable(Routes.PROFILE) { ProfileScreen(vm) }

                    composable(
                        Routes.LESSON,
                        arguments = listOf(navArgument("id") { type = NavType.StringType }),
                    ) { entry ->
                        LessonDetailScreen(
                            vm = vm,
                            lessonId = entry.arguments?.getString("id").orEmpty(),
                            onBack = { nav.popBackStack() },
                            onStartQuiz = { nav.navigate(Routes.quiz(it)) },
                        )
                    }
                    composable(
                        Routes.QUIZ,
                        arguments = listOf(navArgument("id") { type = NavType.StringType }),
                    ) { entry ->
                        QuizScreen(
                            vm = vm,
                            quizId = entry.arguments?.getString("id").orEmpty(),
                            onBack = { nav.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}
