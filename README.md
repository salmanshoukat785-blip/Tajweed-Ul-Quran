# Tajweed ul Quran – Android app (Kotlin + Jetpack Compose)

Bilingual (English / اردو) Tajweed learning app. All lesson content comes from
`Complete_Tajwed_Rules.pdf` (8 chapters, 43 rules) and the home dashboard follows the
reference design (header, today's lesson + progress ring, stats, course cards, daily goal, bottom nav).

## App flow
1. **Splash** – Tajweed ul Quran logo
2. **Onboarding (3 pages)** – English + Urdu text with Salaam, `Skip` / `Next`, last page `Get Started`
3. **Enter Name** – with the logo
4. **Home dashboard** – today's lesson, score / streak / favorites, 8 course cards, Self Test, Quiz, My Score, daily goal
5. Bottom tabs: Home · Lessons · Practice · Score · Profile
6. Language can be switched any time (onboarding, name page, Profile). Urdu = full RTL.

## Project structure
```
app/src/main/java/com/tajweed/quran/
├── MainActivity.kt
├── data/
│   ├── TajweedContent.kt      ← all lessons/rules from the PDF (edit content here)
│   ├── QuizBank.kt            ← quiz questions
│   ├── UserPrefs.kt           ← SharedPreferences (name, score, streak, progress)
│   └── model/Models.kt
├── viewmodel/AppViewModel.kt  ← app state (MVVM)
└── ui/
    ├── theme/  i18n/  components/  navigation/
    ├── splash/ onboarding/ name/ home/
    ├── lessons/ quiz/ score/ profile/
app/src/main/res/drawable/ic_logo.xml   ← PLACEHOLDER logo
```

## Use your own logo
Delete `app/src/main/res/drawable/ic_logo.xml` and add your image as
`app/src/main/res/drawable/ic_logo.png`. Everything (splash, name page, dashboard) picks it up.

## Run
1. Open the folder in **Android Studio** (Ladybug or newer) and let Gradle sync.
2. Run on a device/emulator (minSdk 26).

## Push to GitHub
```
git init
git add .
git commit -m "Tajweed ul Quran app"
git branch -M main
git remote add origin https://github.com/<your-user>/<your-repo>.git
git push -u origin main
```
