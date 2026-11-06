package com.tajweed.quran.data

import com.tajweed.quran.data.model.L
import com.tajweed.quran.data.model.Lesson
import com.tajweed.quran.data.model.Rule

/**
 * All lesson content, taken from "Complete_Tajwed_Rules.pdf".
 * Urdu text follows the PDF; English is a translation of it.
 */
object TajweedContent {

    private fun r(
        titleEn: String, titleUr: String,
        bodyEn: String, bodyUr: String,
        letters: String? = null,
        vararg examples: String,
    ) = Rule(L(titleEn, titleUr), L(bodyEn, bodyUr), letters, examples.toList())

    fun byId(id: String): Lesson? = lessons.firstOrNull { it.id == id }

    val lessons: List<Lesson> = listOf(

        // 1 ─────────────────────────────────────────────── Huroof
        Lesson(
            id = "huroof",
            title = L("Huroof (Letters)", "حروف"),
            subtitle = L("Shamsi, Qamri & Halqi letters", "حروفِ شمسی، قمری اور حلقی"),
            rules = listOf(
                r(
                    "Huroof Shamsi", "حروفِ شمسی",
                    "If 'Al' (ال) comes before any of these 14 letters, the Lam is not pronounced.",
                    "ان 14 حروف سے پہلے اگر ال آ جائے تو لام نہیں پڑھا جاتا۔",
                    "ت ث د ذ ر ز س ش ص ض ط ظ ل ن",
                    "اَلشَّمْسُ", "اَلرَّحْمٰنِ", "اَلرَّحِیْمِ",
                ),
                r(
                    "Huroof Qamri", "حروفِ قمری",
                    "All letters other than Shamsi are Qamri. If 'Al' comes before a Qamri letter, the Lam is read with jazm (sukoon).",
                    "حروفِ شمسی کے علاوہ باقی سارے حروف قمری ہیں۔ اگر حروفِ قمری سے پہلے ال آ جائے تو ل جزم کے ساتھ پڑھا جاتا ہے۔",
                    "ا ب ج ح خ ع غ ف ق ک م و ہ ی",
                    "اَلْقَمَرُ", "اَلْحَمْدُ",
                ),
                r(
                    "Huroof Halqi", "حروفِ حلقی",
                    "The throat letters. They are used in the rules of Noon Sakin and Tanween (Izhar Halqi).",
                    "حلق کے حروف۔ یہ نون ساکن اور تنوین کے قاعدے (اظہارِ حلقی) میں کام آتے ہیں۔",
                    "ء ھ ع ح غ خ",
                ),
            ),
        ),

        // 2 ─────────────────────────────────────────────── Hamza-e-Wasli
        Lesson(
            id = "hamza-wasli",
            title = L("Hamza-e-Wasli", "ہمزہ وصلی"),
            subtitle = L("Alif at the start of a word", "لفظ کے شروع میں آنے والا الف"),
            intro = L(
                "An Alif that comes at the start of a word and carries no harakat (zabar, zer, pesh, tanween, jazm or shadda) is called Hamza-e-Wasli.",
                "ایسا الف جو لفظ کے شروع میں آئے اور اس پر کوئی حرکت نہ ہو، (حرکت سے مراد: زبر، زیر، پیش، تنوین، جزم، شدّ) ہمزہ وصلی کہلاتا ہے۔",
            ),
            rules = listOf(
                r(
                    "Alif followed by Lam", "ا کے بعد ل آ جائے",
                    "If Lam comes after the Alif, read the Alif with zabar.",
                    "ا کے بعد ل آ جائے تو زبر کے ساتھ پڑھنا ہے۔",
                    null, "اَلرَّحْمٰنِ",
                ),
                r(
                    "Third letter has pesh", "تیسرے حرف پر پیش",
                    "If Lam does not come after the Alif, join the Alif and look at the third letter. If it has pesh, read the Alif with pesh.",
                    "ا کے بعد ل نہ آئے تو الف کو ملا کر تیسرا حرف دیکھنا ہے، اگر پیش ہو تو پیش کے ساتھ پڑھنا ہے۔",
                    null, "اُعْبُدُوْا",
                ),
                r(
                    "Third letter has zer or zabar", "تیسرے حرف پر زیر یا زبر",
                    "If Lam does not come after the Alif, join the Alif and look at the third letter. If it has zer or zabar, read the Alif with zer.",
                    "ا کے بعد ل نہ آئے تو الف کو ملا کر تیسرا حرف دیکھنا ہے، اگر زیر یا زبر ہو تو زیر کے ساتھ پڑھنا ہے۔",
                    null, "اِسْتَعِنُوْا", "اِھْدِنَا",
                ),
                r(
                    "When joined with the previous word", "پچھلے لفظ سے ملا کر پڑھنا",
                    "If you join it with the previous word, Hamza-e-Wasli is not pronounced.",
                    "اگر پچھلے لفظ کو ملا کر پڑھیں گے تو ہمزہ وصلی نہیں پڑھا جاتا۔",
                    null, "یَوْمِ الدِّیْنِ",
                ),
            ),
        ),

        // 3 ─────────────────────────────────────────────── Ra & Lafz Allah
        Lesson(
            id = "ra-allah",
            title = L("Ra & Lafz Allah", "حرفِ را اور لفظ اللہ"),
            subtitle = L("Thick and thin pronunciation", "موٹا اور باریک پڑھنے کے قواعد"),
            rules = listOf(
                r(
                    "Ra: read thick", "حرفِ را: موٹا",
                    "If Ra has zabar or pesh, read it thick. If Ra has jazm and the letter before it has zabar or pesh, read it thick.",
                    "اگر ر پر زبر یا پیش آئے تو ر کو موٹا پڑھا جائے گا۔ اگر ر پر جزم آئے اور اس سے پہلے والے حرف پر زبر یا پیش آئے تو ر کو موٹا پڑھا جائے گا۔",
                    null, "رَبِّ", "اَرْسَلَ",
                ),
                r(
                    "Ra: read thin", "حرفِ را: باریک",
                    "If Ra has zer, read it thin. If Ra has jazm and the letter before it has zer, read it thin.",
                    "اگر ر پر زیر آئے تو ر کو باریک پڑھا جائے گا۔ اگر ر پر جزم آئے اور اس سے پہلے والے حرف پر زیر آئے تو ر کو باریک پڑھا جائے گا۔",
                    null, "رِزْقًا", "فِرْعَوْنَ",
                ),
                r(
                    "Lafz Allah: read thick", "لفظ اللہ: موٹا",
                    "If Lafz Allah has zabar or pesh, read it thick. If the letter before Lafz Allah has zabar or pesh, read Lafz Allah full (Pur).",
                    "اگر لفظ اللہ پر زبر یا پیش آئے تو لفظ اللہ کو موٹا پڑھا جائے گا۔ اگر لفظ اللہ سے پہلے والے حرف پر زبر یا پیش آ جائے تو لفظ اللہ کو پُر پڑھا جائے گا۔",
                    null, "وَاللّٰہُ", "قَالَ اللّٰہُ",
                ),
                r(
                    "Lafz Allah: read thin", "لفظ اللہ: باریک",
                    "If Lafz Allah has zer, read it thin. If the letter before Lafz Allah has zer, read Lafz Allah thin.",
                    "اگر لفظ اللہ پر زیر آئے تو لفظ اللہ کو باریک پڑھا جائے گا۔ اگر لفظ اللہ سے پہلے والے حرف پر زیر آ جائے تو لفظ اللہ کو باریک پڑھا جائے گا۔",
                    null, "بِسْمِ اللّٰہِ", "لِلّٰہِ",
                ),
            ),
        ),

        // 4 ─────────────────────────────────────────────── Noon / Meem Mushaddad
        Lesson(
            id = "ghunna",
            title = L("Noon & Meem Mushaddad", "نون اور میم مشدد"),
            subtitle = L("Ghunna rules", "غنہ کے قواعد"),
            rules = listOf(
                r(
                    "Noon with Shadda", "نون شدّ والا",
                    "If a Noon with shadda comes in a word, ghunna is made.",
                    "اگر لفظ میں ن شدّ والا آ جائے تو غنہ ہو گا۔",
                    null, "قُلْ اَعُوْذُ بِرَبِّ النَّاسِ",
                ),
                r(
                    "Meem with Shadda", "میم شدّ والا",
                    "If a Meem with shadda comes in a word, ghunna is made.",
                    "اگر لفظ میں م شدّ والا آ جائے تو غنہ ہو گا۔",
                    null, "اَللّٰھُمَّ صَلِّ عَلٰی مُحَمَّدٍ",
                ),
            ),
        ),

        // 5 ─────────────────────────────────────────────── Qalqalah
        Lesson(
            id = "qalqalah",
            title = L("Qalqalah", "قلقلہ"),
            subtitle = L("Sughra, Kubra & Akbar", "قلقلہ صغریٰ، کبریٰ اور اکبر"),
            rules = listOf(
                r(
                    "Qalqalah letters", "حروفِ قلقلہ",
                    "Pronounce these letters in a way that the letter is clearly heard.",
                    "ادائیگی اس انداز سے کرنا کہ حرف واضح طور پر سنائی دے۔",
                    "ق ط ب ج د",
                ),
                r(
                    "Qalqalah Sughra", "قلقلہ صغریٰ",
                    "When the qalqalah letter is sakin in the middle of a word.",
                    "جب حرفِ قلقلہ لفظ کے درمیان ساکن ہو۔",
                    null, "اَقْرَبُ",
                ),
                r(
                    "Qalqalah Kubra", "قلقلہ کبریٰ",
                    "When the word ends on a qalqalah letter.",
                    "جب لفظ کا اختتام حروفِ قلقلہ پر ہو۔",
                    null, "لَقَدْ",
                ),
                r(
                    "Qalqalah Akbar", "قلقلہ اکبر",
                    "When the qalqalah letter has a shadda.",
                    "جب حرفِ قلقلہ شدّ والا ہو۔",
                    null, "تَبَّتْ", "اَلْحَقُّ", "تَبَّ",
                ),
            ),
        ),

        // 6 ─────────────────────────────────────────────── Madd
        Lesson(
            id = "madd",
            title = L("Madd (Elongation)", "مد"),
            subtitle = L("Huroof Illat and all types of Madd", "حروفِ علت اور مد کی اقسام"),
            rules = listOf(
                r(
                    "Huroof Illat", "حروفِ علت",
                    "The three letters of Illat are Alif, Waw and Ya. From them come Madd Tabee, Madd Badal and Madd Leen.",
                    "حروفِ علت تین ہیں: ا، و، ی۔ ان سے مدِ طبیعی، مدِ بدل اور مدِ لین بنتے ہیں۔",
                    "ا و ی",
                ),
                r(
                    "Madd Tabee", "مدِ طبیعی",
                    "Alif after a letter with zabar. Waw after a letter with pesh. Ya after a letter with zer.",
                    "الف سے پہلے حرف پر زبر۔ واؤ سے پہلے حرف پر پیش۔ ی سے پہلے حرف پر زیر۔",
                    null, "اِھْدِنَا", "یَقُوْلُوْنَ", "اَلسّٰجِدِیْنَ",
                ),
                r(
                    "Madd Badal", "مدِ بدل",
                    "Shown by a standing zabar (for Alif), a standing zer (for Waw) and an inverted pesh (for Ya).",
                    "کھڑی زبر (الف کی جگہ)، کھڑی زیر (واؤ کی جگہ)، الٹا پیش (ی کی جگہ)۔",
                    null, "اَلرَّحْمٰنِ", "عِبَادِہٖ", "نِعْمَتُہٗ",
                ),
                r(
                    "Madd Leen", "مدِ لین",
                    "Waw or Ya with sukoon, where the letter before it has zabar.",
                    "واؤ یا ی ساکن جن سے پہلے والے حرف پر زبر ہو۔",
                    null, "لَوْ", "قُرَیْشٍ",
                ),
                r(
                    "Madd Arid lis-Sukoon", "مدِ عارض للسکون",
                    "If the letter after a madd letter is made sakin (when stopping), lengthen the madd letter by 3 to 4 harakat.",
                    "اگر کسی لفظ میں حروفِ مد کے بعد والے حرف کو ساکن کر دیا جائے تو حروفِ مد کو 3 سے 4 حرکات لمبا کرنا ہے۔",
                    null, "یَقُوْلُوْنَ", "اَلسّٰجِدِیْنَ",
                ),
                r(
                    "Madd Jaiz Munfasil", "مدِ جائز منفصل",
                    "If a word ends on a madd letter and the next word starts with hamza, lengthen the madd letter by 3 to 4 harakat.",
                    "اگر کسی لفظ کا اختتام حروفِ مد پر ہو اور اس سے اگلا لفظ ہمزہ سے شروع ہو تو حروفِ مد کو 3 سے 4 حرکات لمبا کرنا ہے۔",
                    null, "قَالُوْٓا اِنْ",
                ),
                r(
                    "Madd Wajib Muttasil", "مدِ واجب متصل",
                    "If a madd letter is in the middle of a word and the next letter starts with hamza, lengthen the madd letter by 5 to 6 harakat.",
                    "اگر کسی لفظ کے درمیان میں حروفِ مد ہو اور اس سے اگلا حرف ہمزہ سے شروع ہو تو حروفِ مد کو 5 سے 6 حرکات لمبا کرنا ہے۔",
                    null, "اَلسَّمَآءِ",
                ),
                r(
                    "Madd Lazim Kalmi Muthaqqal", "مدِ لازم کلمی مثقل",
                    "If a madd letter is in the middle of a word and the next letter has a shadda, lengthen the madd letter by 5 to 6 harakat.",
                    "اگر کسی لفظ کے درمیان میں حروفِ مد ہو اور اس سے اگلا حرف شدّ والا ہو تو حروفِ مد کو 5 سے 6 حرکات لمبا کرنا ہے۔",
                    null, "اَلضَّآلِّیْنَ",
                ),
                r(
                    "Madd Lazim Kalmi Mukhaffaf", "مدِ لازم کلمی مخفّف",
                    "If a madd letter is in the middle of a word, lengthen the madd letter by 2 harakat.",
                    "اگر کسی لفظ کے درمیان میں حروفِ مد ہو تو حروفِ مد کو 2 حرکات لمبا کرنا ہے۔",
                    null, "وَالصّٰٓفّٰتِ",
                ),
                r(
                    "Madd Lazim Harfi Muthaqqal", "مدِ لازم حرفی مثقل",
                    "Huroof Muqatta'at: lengthen by 6 harakat.",
                    "حروفِ مقطعات: چھ حرکات لمبا کرنا ہے۔",
                    "ج د ذ س ش ص ض ع غ ک ق ل م ن",
                ),
                r(
                    "Madd Lazim Harfi Mukhaffaf", "مدِ لازم حرفی مخفّف",
                    "Huroof Muqatta'at: lengthen by 2 harakat.",
                    "حروفِ مقطعات: دو حرکات لمبا کرنا ہے۔",
                    "ا ب ت ث ح خ ر ز ط ظ ف ء ھ ی",
                ),
                r(
                    "Madd Silah Sughra", "مدِ صلہ صغریٰ",
                    "If a Ha (ہ) at the end of a word comes with Madd Badal and the next word does not start with hamza, lengthen by 2 harakat.",
                    "اگر کسی لفظ کے آخر میں ہ مدِ بدل کے ساتھ آئے اور اگلا حرف ہمزہ سے شروع نہ ہو تو 2 حرکات لمبا کرنا ہے۔",
                    null, "عِبَادِہٖ",
                ),
                r(
                    "Madd Silah Kubra", "مدِ صلہ کبریٰ",
                    "If a Ha (ہ) at the end of a word comes with Madd Badal and the next word starts with hamza, lengthen by 3 to 4 harakat.",
                    "اگر کسی لفظ کے آخر میں ہ مدِ بدل کے ساتھ آئے اور اگلا حرف ہمزہ سے شروع ہو تو 3 سے 4 حرکات لمبا کرنا ہے۔",
                    null, "مِنْ دُوْنِہٖٓ اٰلِھَۃً",
                ),
            ),
            note = L(
                "No other madd rule applies to Madd Leen. All the following madd rules are built only from Madd Tabee and Madd Badal.",
                "مدِ لین پر کوئی اور مد کا قاعدہ نہیں لگتا۔ مد کے اگلے جتنے بھی قواعد ہیں وہ صرف مدِ طبیعی اور مدِ بدل سے بنیں گے۔",
            ),
        ),

        // 7 ─────────────────────────────────────────────── Noon Sakin & Tanween
        Lesson(
            id = "noon-sakin",
            title = L("Noon Sakin & Tanween", "نون ساکن اور تنوین"),
            subtitle = L("Izhar, Ikhfa, Idgham & Iqlab", "اظہار، اخفاء، ادغام اور اقلاب"),
            intro = L(
                "Noon Sakin means a Noon with sukoon (نْ). Tanween means two zabar, two zer or two pesh.",
                "نون ساکن سے مراد: نْ ۔ تنوین سے مراد: دو زبر، دو زیر، دو پیش۔",
            ),
            rules = listOf(
                r(
                    "Izhar Halqi", "اظہارِ حلقی",
                    "If a Halqi letter comes after Noon Sakin or Tanween, pronounce the sound of the Noon or Tanween clearly.",
                    "اگر یہ حروف آ جائیں تو ن یا تنوین کی آواز کو ظاہر کرنا ہے۔",
                    "ء ھ ع ح غ خ",
                    "وَکُلَّ شَیْءٍ اَحْصَیْنٰہُ",
                ),
                r(
                    "Ikhfa Haqiqi", "اخفاءِ حقیقی",
                    "If any letter other than the Halqi letters and the Idgham letters comes after Noon Sakin or Tanween, read the sound of the Noon or Tanween in the nose.",
                    "حروفِ حلقی اور حروفِ ادغام کے علاوہ کوئی حرف ن یا تنوین کے بعد آ جائے تو ن یا تنوین کی آواز کو ناک میں پڑھنا۔",
                    null, "اَنْتُمْ", "تَنْتَھُوْا",
                ),
                r(
                    "Idgham", "ادغام",
                    "If these letters come after Noon Sakin or Tanween, they are read by joining with them.",
                    "نون ساکن یا تنوین کے بعد اگر یہ حروف آ جائیں تو ان کو ملا کر پڑھا جاتا ہے۔",
                    "ی ر م ل و ن",
                ),
                r(
                    "Idgham Bighunna Naqis", "ادغام بغنہ ناقص",
                    "The Idgham is done with ghunna for these letters.",
                    "ان حروف میں ادغام غنہ کے ساتھ ہوتا ہے۔",
                    "ی م و ن",
                    "بَشَرٌ مِّثْلُنَا", "مَنْ یُّحْیِ الْعِظَامَ", "مِنْ نَّخِیْلٍ وَّاَعْنَابٍ",
                ),
                r(
                    "Idgham Bila Ghunna Kamil", "ادغام بلا غنہ کامل",
                    "In these letters there is no ghunna of the Noon or Tanween.",
                    "ان میں ن اور تنوین کا غنہ نہیں ہو گا۔",
                    "ر ل",
                    "لَئِنْ لَّمْ", "مُحَمَّدٌ رَّسُوْلُ اللّٰہِ",
                ),
                r(
                    "Idgham Mutaqaribain", "ادغام متقاربین",
                    "The meeting of two letters that are the same in makhraj and sifat (as written in the PDF).",
                    "دو حروف کا ملنا جو مخارج اور صفات میں ایک ہوں۔",
                    null, "اَلَمْ نَخْلُقْکُّمْ",
                ),
                r(
                    "Idgham Mutajanisain", "ادغام متجانسین",
                    "The meeting of two letters that are the same in makhraj but different in sifat.",
                    "دو حروف کا ملنا جو مخارج میں ایک اور صفات میں مختلف ہوں۔",
                    null, "قَدْ تَّبَیَّنَ",
                ),
                r(
                    "Idgham Mutamathilain", "ادغام متمثلین",
                    "The meeting of two identical letters.",
                    "دو ایک جیسے حروف کا ملنا۔",
                    null, "لَھُمْ مَّا یَشَاءُوْنَ",
                ),
                r(
                    "Noon Qutni", "ن قطنی",
                    "If a word ends with Tanween and the next word starts with Hamza-e-Wasli, drop one harakat from the Tanween and read the next word with a Noon (with zer).",
                    "لفظ کا تنوین پر اختتام ہو اور اگلا حرف ہمزہ وصلی سے شروع ہو تو تنوین میں سے ایک حرکت ختم کر کے ن (زیر) کے ساتھ اگلے لفظ کو پڑھنا ہے۔",
                    null, "اَحَدٌ اللّٰہُ الصَّمَدُ",
                ),
                r(
                    "Iqlab", "اقلاب",
                    "If Ba (ب) comes after Noon Sakin or Tanween, change the Noon or Tanween into Meem (م) and read it with ghunna.",
                    "نون ساکن یا تنوین کے بعد اگر ب آ جائے تو نون ساکن یا تنوین کو م سے بدل کر غنہ کر کے پڑھا جاتا ہے۔",
                    "ب",
                    "سَمِیْعٌ بَصِیْرٌ", "مِنْ بَعْدِ",
                ),
            ),
        ),

        // 8 ─────────────────────────────────────────────── Meem Sakin
        Lesson(
            id = "meem-sakin",
            title = L("Meem Sakin", "میم ساکن"),
            subtitle = L("Izhar, Idgham & Ikhfa Shafawi", "اظہار، ادغام اور اخفاءِ شفوی"),
            rules = listOf(
                r(
                    "Izhar Shafawi", "اظہارِ شفوی",
                    "If any letter other than Ba (ب) and Meem (م) comes after Meem Sakin, pronounce the Meem clearly.",
                    "میم ساکن کے بعد اگر ب، م کے علاوہ کوئی بھی حرف آ جائے تو م کو ظاہر کر کے پڑھا جائے گا۔",
                    null, "اَمْ لَمْ تُنْذِرْھُمْ",
                ),
                r(
                    "Idgham Shafawi", "ادغامِ شفوی",
                    "If Meem (م) comes after Meem Sakin, join the first Meem with the second Meem and read it with ghunna.",
                    "میم ساکن کے بعد اگر م آ جائے تو پہلی م کو دوسری م سے ملا کر غنہ کر کے پڑھا جائے گا۔",
                    null, "لَھُمْ مَّا یَشَاءُوْنَ",
                ),
                r(
                    "Ikhfa Shafawi", "اخفاءِ شفوی",
                    "If Ba (ب) comes after Meem Sakin, make ghunna without clearly showing the sound of the Meem.",
                    "میم ساکن کے بعد اگر ب آ جائے تو م کی آواز کو ظاہر کئے بغیر غنہ کرنا۔",
                    "ب",
                    "تَرْمِیْھِمْ بِحِجَارَۃٍ",
                ),
            ),
        ),
    )
}
