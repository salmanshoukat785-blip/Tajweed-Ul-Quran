package com.tajweed.quran.data

import com.tajweed.quran.data.model.L
import com.tajweed.quran.data.model.QuizQuestion
import com.tajweed.quran.data.model.same

/** Quiz questions built from the rules in the PDF. */
object QuizBank {

    private fun q(lesson: String, en: String, ur: String, correct: Int, vararg options: L) =
        QuizQuestion(lesson, L(en, ur), options.toList(), correct)

    val all: List<QuizQuestion> = listOf(

        // Huroof
        q("huroof", "How many Huroof Shamsi are there?", "حروفِ شمسی کتنے ہیں؟", 2,
            same("10"), same("28"), same("14"), same("6")),
        q("huroof", "If 'Al' comes before a Qamri letter, the Lam is…", "حروفِ قمری سے پہلے ال آ جائے تو لام کو…", 0,
            L("read with jazm", "جزم کے ساتھ پڑھا جاتا ہے"),
            L("not pronounced", "نہیں پڑھا جاتا"),
            L("read with shadda", "شدّ کے ساتھ پڑھا جاتا ہے"),
            L("read thick", "موٹا پڑھا جاتا ہے")),
        q("huroof", "Which are the Huroof Halqi?", "حروفِ حلقی کون سے ہیں؟", 1,
            same("ق ط ب ج د"), same("ء ھ ع ح غ خ"), same("ی ر م ل و ن"), same("ت ث د ذ ر ز")),
        q("huroof", "Why is the Lam of 'اَلشَّمْسُ' not pronounced?", "اَلشَّمْسُ میں ال کا لام کیوں نہیں پڑھا جاتا؟", 3,
            L("Shin is a Halqi letter", "ش حرفِ حلقی ہے"),
            L("Shin is a Qamri letter", "ش حرفِ قمری ہے"),
            L("Shin has jazm", "ش پر جزم ہے"),
            L("Shin is a Shamsi letter", "ش حرفِ شمسی ہے")),

        // Makharij
        q("makharij", "How many main areas (maqamat) of makharij are there?", "مخارج کے بڑے مقامات کتنے ہیں؟", 1,
            same("3"), same("5"), same("7"), same("17")),
        q("makharij", "How many makharij are there in total?", "مخارج کل کتنے ہیں؟", 2,
            same("5"), same("10"), same("17"), same("29")),
        q("makharij", "The Madd letters (ا و ی) come from…", "حروفِ مد (ا و ی) کہاں سے نکلتے ہیں؟", 0,
            L("Jawf (hollow of mouth and throat)", "جوف (منہ اور حلق کا خالی حصہ)"),
            L("Halq (throat)", "حلق"),
            L("Lisan (tongue)", "لسان (زبان)"),
            L("Shafatain (lips)", "شفتین (ہونٹ)")),
        q("makharij", "Which letters come from the throat (Halq)?", "حلق سے کون سے حروف نکلتے ہیں؟", 1,
            same("ق ک ج ش ی"), same("ء ھ ع ح غ خ"), same("ف ب م و"), same("ت د ط")),
        q("makharij", "Ghain (غ) and Kha (خ) come from…", "غ اور خ کہاں سے نکلتے ہیں؟", 2,
            L("Aqsa Halq (deepest throat)", "اقصیٰ حلق (حلق کی گہرائی)"),
            L("Wasat Halq (middle throat)", "وسطِ حلق (حلق کا درمیان)"),
            L("Adna Halq (throat near the mouth)", "ادنیٰ حلق (منہ کے قریب)"),
            L("Khayshum (nasal cavity)", "خیشوم (ناک کا خلا)")),
        q("makharij", "Which letters come from the lips (Shafatain)?", "ہونٹوں (شفتین) سے کون سے حروف نکلتے ہیں؟", 0,
            same("ف ب م و"), same("ق ک"), same("ص س ز"), same("ء ھ")),
        q("makharij", "Where does Ghunna come from?", "غنہ کہاں سے ادا ہوتا ہے؟", 3,
            L("Halq (throat)", "حلق"),
            L("Shafatain (lips)", "شفتین (ہونٹ)"),
            L("Lisan (tongue)", "لسان (زبان)"),
            L("Khayshum (nasal cavity)", "خیشوم (ناک کا خلا)")),
        q("makharij", "Which letters come from the tip of the tongue and the roots of the upper front teeth?", "زبان کی نوک اور اوپر کے اگلے دانتوں کی جڑ سے کون سے حروف نکلتے ہیں؟", 2,
            same("ث ذ ظ"), same("ص س ز"), same("ت د ط"), same("ق ک")),
        q("makharij", "Qaf (ق) comes from…", "ق کہاں سے نکلتا ہے؟", 3,
            L("The lips", "ہونٹ"),
            L("The tip of the tongue and the upper teeth", "زبان کی نوک اور اوپر کے دانت"),
            L("The nose", "ناک"),
            L("The back of the tongue and the soft palate", "زبان کی جڑ اور نرم تالو")),
        q("makharij", "Ra (ر) comes from…", "ر کہاں سے نکلتا ہے؟", 0,
            L("The tip of the tongue, a little behind the point of Noon", "زبان کی نوک، ن کے مخرج سے ذرا پیچھے"),
            L("Both lips", "دونوں ہونٹ"),
            L("The middle of the throat", "حلق کا درمیان"),
            L("The nose", "ناک")),

        // Hamza-e-Wasli
        q("hamza-wasli", "What is Hamza-e-Wasli?", "ہمزہ وصلی کیا ہے؟", 0,
            L("An Alif at the start of a word with no harakat", "لفظ کے شروع میں ایسا الف جس پر کوئی حرکت نہ ہو"),
            L("An Alif with shadda", "شدّ والا الف"),
            L("An Alif at the end of a word", "لفظ کے آخر میں آنے والا الف"),
            L("A Hamza with pesh", "پیش والا ہمزہ")),
        q("hamza-wasli", "If Lam comes after the Alif (as in الرحمن), the Alif is read with…", "ا کے بعد ل آ جائے (جیسے الرحمن) تو الف کو کس کے ساتھ پڑھیں گے؟", 2,
            L("zer", "زیر"), L("pesh", "پیش"), L("zabar", "زبر"), L("jazm", "جزم")),
        q("hamza-wasli", "In 'اُعْبُدُوْا' the third letter has pesh, so the Alif is read with…", "اُعْبُدُوْا میں تیسرے حرف پر پیش ہے، تو الف کو کس کے ساتھ پڑھیں گے؟", 1,
            L("zabar", "زبر"), L("pesh", "پیش"), L("zer", "زیر"), L("It is not read", "نہیں پڑھا جاتا")),
        q("hamza-wasli", "In 'یَوْمِ الدِّیْنِ', what happens to Hamza-e-Wasli?", "یَوْمِ الدِّیْنِ میں ہمزہ وصلی کا کیا حکم ہے؟", 3,
            L("Read with zabar", "زبر کے ساتھ پڑھا جاتا ہے"),
            L("Read with zer", "زیر کے ساتھ پڑھا جاتا ہے"),
            L("Read with pesh", "پیش کے ساتھ پڑھا جاتا ہے"),
            L("Not pronounced, because it is joined with the previous word", "نہیں پڑھا جاتا، کیونکہ پچھلے لفظ سے ملا کر پڑھا جاتا ہے")),

        // Ra & Allah
        q("ra-allah", "Ra with zabar or pesh is read…", "ر پر زبر یا پیش ہو تو ر کو…", 0,
            L("thick", "موٹا پڑھا جائے گا"), L("thin", "باریک پڑھا جائے گا"), L("with ghunna", "غنہ کے ساتھ پڑھا جائے گا"), L("not pronounced", "نہیں پڑھا جائے گا")),
        q("ra-allah", "Ra with zer is read…", "ر پر زیر ہو تو ر کو…", 1,
            L("thick", "موٹا پڑھا جائے گا"), L("thin", "باریک پڑھا جائے گا"), L("with ghunna", "غنہ کے ساتھ پڑھا جائے گا"), L("not pronounced", "نہیں پڑھا جائے گا")),
        q("ra-allah", "Ra has jazm and the letter before it has zer. Ra is read…", "ر پر جزم ہو اور اس سے پہلے والے حرف پر زیر ہو تو ر کو…", 2,
            L("thick", "موٹا پڑھا جائے گا"), L("with ghunna", "غنہ کے ساتھ پڑھا جائے گا"), L("thin", "باریک پڑھا جائے گا"), L("not pronounced", "نہیں پڑھا جائے گا")),
        q("ra-allah", "The letter before Lafz Allah has zer (as in بِسْمِ اللّٰہِ). Lafz Allah is read…", "لفظ اللہ سے پہلے والے حرف پر زیر ہو (جیسے بِسْمِ اللّٰہِ) تو لفظ اللہ کو…", 3,
            L("thick", "موٹا پڑھا جائے گا"), L("with ghunna", "غنہ کے ساتھ پڑھا جائے گا"), L("not pronounced", "نہیں پڑھا جائے گا"), L("thin", "باریک پڑھا جائے گا")),
        q("ra-allah", "The letter before Lafz Allah has zabar or pesh. Lafz Allah is read…", "لفظ اللہ سے پہلے والے حرف پر زبر یا پیش ہو تو لفظ اللہ کو…", 0,
            L("full / thick (Pur)", "پُر (موٹا) پڑھا جائے گا"), L("thin", "باریک پڑھا جائے گا"), L("with ghunna", "غنہ کے ساتھ پڑھا جائے گا"), L("not pronounced", "نہیں پڑھا جائے گا")),

        // Ghunna
        q("ghunna", "What is made when Noon with shadda comes in a word?", "لفظ میں ن شدّ والا آ جائے تو کیا ہو گا؟", 1,
            L("Qalqalah", "قلقلہ"), L("Ghunna", "غنہ"), L("Madd", "مد"), L("Iqlab", "اقلاب")),
        q("ghunna", "What is made when Meem with shadda comes in a word?", "لفظ میں م شدّ والا آ جائے تو کیا ہو گا؟", 2,
            L("Qalqalah", "قلقلہ"), L("Madd", "مد"), L("Ghunna", "غنہ"), L("Nothing", "کچھ نہیں")),
        q("ghunna", "In 'اَللّٰھُمَّ' which letter needs ghunna?", "اَللّٰھُمَّ میں کس حرف پر غنہ ہو گا؟", 3,
            same("ل"), same("ھ"), same("ا"), same("م")),

        // Qalqalah
        q("qalqalah", "Which are the Qalqalah letters?", "حروفِ قلقلہ کون سے ہیں؟", 0,
            same("ق ط ب ج د"), same("ء ھ ع ح غ خ"), same("ی ر م ل و ن"), same("ا و ی")),
        q("qalqalah", "'اَقْرَبُ' is an example of…", "اَقْرَبُ کس کی مثال ہے؟", 1,
            L("Qalqalah Kubra", "قلقلہ کبریٰ"), L("Qalqalah Sughra", "قلقلہ صغریٰ"), L("Qalqalah Akbar", "قلقلہ اکبر"), L("Madd Leen", "مدِ لین")),
        q("qalqalah", "'لَقَدْ' is an example of…", "لَقَدْ کس کی مثال ہے؟", 2,
            L("Qalqalah Sughra", "قلقلہ صغریٰ"), L("Qalqalah Akbar", "قلقلہ اکبر"), L("Qalqalah Kubra", "قلقلہ کبریٰ"), L("Idgham", "ادغام")),
        q("qalqalah", "Qalqalah Akbar is when the qalqalah letter…", "قلقلہ اکبر تب ہوتا ہے جب حرفِ قلقلہ…", 3,
            L("is in the middle with sukoon", "درمیان میں ساکن ہو"), L("ends the word", "لفظ کے آخر میں ہو"), L("has zer", "زیر والا ہو"), L("has shadda", "شدّ والا ہو")),

        // Madd
        q("madd", "Which are the Huroof Illat?", "حروفِ علت کون سے ہیں؟", 1,
            same("ق ط ب ج د"), same("ا و ی"), same("ء ھ ع ح غ خ"), same("ی ر م ل و ن")),
        q("madd", "Madd Wajib Muttasil is lengthened by…", "مدِ واجب متصل کو کتنا لمبا کرنا ہے؟", 2,
            L("2 harakat", "2 حرکات"), L("3 to 4 harakat", "3 سے 4 حرکات"), L("5 to 6 harakat", "5 سے 6 حرکات"), L("1 harakat", "1 حرکت")),
        q("madd", "Madd Jaiz Munfasil is lengthened by…", "مدِ جائز منفصل کو کتنا لمبا کرنا ہے؟", 1,
            L("2 harakat", "2 حرکات"), L("3 to 4 harakat", "3 سے 4 حرکات"), L("5 to 6 harakat", "5 سے 6 حرکات"), L("6 harakat", "6 حرکات")),
        q("madd", "Madd Silah Sughra is lengthened by…", "مدِ صلہ صغریٰ کو کتنا لمبا کرنا ہے؟", 0,
            L("2 harakat", "2 حرکات"), L("3 to 4 harakat", "3 سے 4 حرکات"), L("5 to 6 harakat", "5 سے 6 حرکات"), L("6 harakat", "6 حرکات")),
        q("madd", "Madd Silah Kubra is lengthened by…", "مدِ صلہ کبریٰ کو کتنا لمبا کرنا ہے؟", 3,
            L("2 harakat", "2 حرکات"), L("5 to 6 harakat", "5 سے 6 حرکات"), L("6 harakat", "6 حرکات"), L("3 to 4 harakat", "3 سے 4 حرکات")),
        q("madd", "Madd Arid lis-Sukoon is lengthened by…", "مدِ عارض للسکون کو کتنا لمبا کرنا ہے؟", 1,
            L("2 harakat", "2 حرکات"), L("3 to 4 harakat", "3 سے 4 حرکات"), L("5 to 6 harakat", "5 سے 6 حرکات"), L("1 harakat", "1 حرکت")),
        q("madd", "Madd Lazim Harfi Muthaqqal (Huroof Muqatta'at) is lengthened by…", "مدِ لازم حرفی مثقل کو کتنا لمبا کرنا ہے؟", 2,
            L("2 harakat", "2 حرکات"), L("3 to 4 harakat", "3 سے 4 حرکات"), L("6 harakat", "6 حرکات"), L("1 harakat", "1 حرکت")),
        q("madd", "Which madd takes no further madd rule?", "کس مد پر کوئی اور مد کا قاعدہ نہیں لگتا؟", 3,
            L("Madd Tabee", "مدِ طبیعی"), L("Madd Badal", "مدِ بدل"), L("Madd Silah", "مدِ صلہ"), L("Madd Leen", "مدِ لین")),

        // Noon Sakin & Tanween
        q("noon-sakin", "Which letters give Izhar Halqi?", "اظہارِ حلقی کے حروف کون سے ہیں؟", 0,
            same("ء ھ ع ح غ خ"), same("ی ر م ل و ن"), same("ق ط ب ج د"), same("ب")),
        q("noon-sakin", "Which letters give Idgham after Noon Sakin or Tanween?", "نون ساکن یا تنوین کے بعد ادغام کے حروف کون سے ہیں؟", 1,
            same("ء ھ ع ح غ خ"), same("ی ر م ل و ن"), same("ق ط ب ج د"), same("ا و ی")),
        q("noon-sakin", "Ba (ب) after Noon Sakin or Tanween gives…", "نون ساکن یا تنوین کے بعد ب آ جائے تو کون سا قاعدہ ہو گا؟", 2,
            L("Izhar", "اظہار"), L("Idgham", "ادغام"), L("Iqlab", "اقلاب"), L("Ikhfa", "اخفاء")),
        q("noon-sakin", "In Iqlab, the Noon or Tanween is changed into…", "اقلاب میں ن یا تنوین کو کس سے بدلا جاتا ہے؟", 3,
            same("ن"), same("ب"), same("ل"), same("م")),
        q("noon-sakin", "Which two letters give Idgham Bila Ghunna Kamil?", "ادغام بلا غنہ کامل کے حروف کون سے ہیں؟", 1,
            same("ی م"), same("ر ل"), same("و ن"), same("ب م")),
        q("noon-sakin", "'لَئِنْ لَّمْ' is an example of…", "لَئِنْ لَّمْ کس کی مثال ہے؟", 2,
            L("Iqlab", "اقلاب"), L("Ikhfa Haqiqi", "اخفاءِ حقیقی"), L("Idgham Bila Ghunna Kamil", "ادغام بلا غنہ کامل"), L("Izhar Halqi", "اظہارِ حلقی")),
        q("noon-sakin", "'اَنْتُمْ' is an example of…", "اَنْتُمْ کس کی مثال ہے؟", 3,
            L("Iqlab", "اقلاب"), L("Izhar Halqi", "اظہارِ حلقی"), L("Idgham", "ادغام"), L("Ikhfa Haqiqi", "اخفاءِ حقیقی")),
        q("noon-sakin", "'مِنْ بَعْدِ' is an example of…", "مِنْ بَعْدِ کس کی مثال ہے؟", 0,
            L("Iqlab", "اقلاب"), L("Ikhfa Haqiqi", "اخفاءِ حقیقی"), L("Idgham", "ادغام"), L("Izhar Halqi", "اظہارِ حلقی")),
        q("noon-sakin", "In Ikhfa Haqiqi, the sound of the Noon or Tanween is read…", "اخفاءِ حقیقی میں ن یا تنوین کی آواز کو کہاں پڑھا جاتا ہے؟", 1,
            L("clearly in the throat", "حلق میں ظاہر کر کے"), L("in the nose", "ناک میں"), L("not at all", "بالکل نہیں پڑھا جاتا"), L("as a Meem", "م بنا کر")),

        // Meem Sakin
        q("meem-sakin", "Ba (ب) after Meem Sakin gives…", "میم ساکن کے بعد ب آ جائے تو کون سا قاعدہ ہو گا؟", 2,
            L("Izhar Shafawi", "اظہارِ شفوی"), L("Idgham Shafawi", "ادغامِ شفوی"), L("Ikhfa Shafawi", "اخفاءِ شفوی"), L("Iqlab", "اقلاب")),
        q("meem-sakin", "Meem (م) after Meem Sakin gives…", "میم ساکن کے بعد م آ جائے تو کون سا قاعدہ ہو گا؟", 1,
            L("Izhar Shafawi", "اظہارِ شفوی"), L("Idgham Shafawi", "ادغامِ شفوی"), L("Ikhfa Shafawi", "اخفاءِ شفوی"), L("Iqlab", "اقلاب")),
        q("meem-sakin", "Any letter other than ب and م after Meem Sakin gives…", "میم ساکن کے بعد ب اور م کے علاوہ کوئی حرف آ جائے تو کون سا قاعدہ ہو گا؟", 0,
            L("Izhar Shafawi", "اظہارِ شفوی"), L("Idgham Shafawi", "ادغامِ شفوی"), L("Ikhfa Shafawi", "اخفاءِ شفوی"), L("Qalqalah", "قلقلہ")),
        q("meem-sakin", "'تَرْمِیْھِمْ بِحِجَارَۃٍ' is an example of…", "تَرْمِیْھِمْ بِحِجَارَۃٍ کس کی مثال ہے؟", 3,
            L("Izhar Shafawi", "اظہارِ شفوی"), L("Idgham Shafawi", "ادغامِ شفوی"), L("Iqlab", "اقلاب"), L("Ikhfa Shafawi", "اخفاءِ شفوی")),
    )

    fun countFor(lessonId: String): Int = all.count { it.lessonId == lessonId }

    /** "all" = Self Test (10 mixed questions). Otherwise the quiz of one lesson. */
    fun forQuiz(id: String): List<QuizQuestion> =
        if (id == "all") all.shuffled().take(10)
        else all.filter { it.lessonId == id }.shuffled().take(10)
}
