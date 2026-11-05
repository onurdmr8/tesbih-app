package com.example.zikirmatik.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Dhikr(
    val id: String,
    val title: String,
    val arabic: String = "",
    val meaning: String = "",
    val defaultTarget: Int = 33,
    val isCustom: Boolean = false
)

data class HistoryRecord(
    val id: Long = System.currentTimeMillis(),
    val dhikrTitle: String,
    val count: Int,
    val target: Int,
    val dateText: String = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
)

enum class TasbihTheme(
    val title: String,
    val primaryHex: Long,
    val secondaryHex: Long,
    val surfaceHex: Long,
    val beadHex: Long,
    val accentHex: Long
) {
    EMERALD_GOLD(
        title = "Zümrüt Yeşili & Altın",
        primaryHex = 0xFF0D5C3A,
        secondaryHex = 0xFF148F56,
        surfaceHex = 0xFF062A1B,
        beadHex = 0xFFD4AF37,
        accentHex = 0xFFFFD700
    ),
    NIGHT_BLUE(
        title = "Gece Mavisi & Safir",
        primaryHex = 0xFF0F3460,
        secondaryHex = 0xFF1A508B,
        surfaceHex = 0xFF0A192F,
        beadHex = 0xFF00ADB5,
        accentHex = 0xFFE0E5B6
    ),
    ROSE_GOLD(
        title = "Gül Ağacı & Bronz",
        primaryHex = 0xFF6B2737,
        secondaryHex = 0xFF8C3449,
        surfaceHex = 0xFF2D1118,
        beadHex = 0xFFE0A96D,
        accentHex = 0xFFFFCDB2
    ),
    METALLIC_SILVER(
        title = "Metalik Titanyum",
        primaryHex = 0xFF393E46,
        secondaryHex = 0xFF4E545C,
        surfaceHex = 0xFF222831,
        beadHex = 0xFFEEEEEE,
        accentHex = 0xFF00ADB5
    ),
    MIDNIGHT_DARK(
        title = "OLED Gece Siyahı",
        primaryHex = 0xFF1E1E1E,
        secondaryHex = 0xFF2C2C2C,
        surfaceHex = 0xFF121212,
        beadHex = 0xFF10B981,
        accentHex = 0xFF34D399
    )
}

object PredefinedDhikrs {
    val list = listOf(
        Dhikr(
            id = "subhanallah",
            title = "Sübhanallah",
            arabic = "سُبْحَانَ اللَّهِ",
            meaning = "Allah her türlü eksiklik ve kusurdan münezzehtir.",
            defaultTarget = 33
        ),
        Dhikr(
            id = "elhamdulillah",
            title = "Elhamdülillah",
            arabic = "الْحَمْدُ لِلَّهِ",
            meaning = "Bütün hamd ve şükürler yalnız Allah'a aittir.",
            defaultTarget = 33
        ),
        Dhikr(
            id = "allahu_ekber",
            title = "Allahu Ekber",
            arabic = "اللَّهُ أَكْبَرُ",
            meaning = "Allah her şeyden yücedir, en büyüktür.",
            defaultTarget = 33
        ),
        Dhikr(
            id = "kelime_i_tevhid",
            title = "Lâ ilâhe illallâh",
            arabic = "لَا إِلٰهَ إِلَّا اللهُ",
            meaning = "Allah'tan başka hiçbir ilah yoktur.",
            defaultTarget = 100
        ),
        Dhikr(
            id = "estagfirullah",
            title = "Estağfirullah el-Azîm",
            arabic = "أَسْتَغْفِرُ اللَّهَ الْعَظِيمَ",
            meaning = "Yüce Allah'tan günahlarım için bağışlanma dilerim.",
            defaultTarget = 100
        ),
        Dhikr(
            id = "salavat",
            title = "Salavat-ı Şerife",
            arabic = "اللَّهُمَّ صَلِّ عَلَىٰ مُحَمَّدٍ وَعَلَىٰ آلِ مُحَمَّدٍ",
            meaning = "Allah'ım! Efendimiz Hz. Muhammed'e ve aline rahmet eyle.",
            defaultTarget = 100
        ),
        Dhikr(
            id = "hasbunallah",
            title = "Hasbünallâhu ve ni'mel vekîl",
            arabic = "حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ",
            meaning = "Allah bize yeter, O ne güzel vekildir.",
            defaultTarget = 33
        ),
        Dhikr(
            id = "havle",
            title = "Lâ havle velâ kuvvete illâ billâh",
            arabic = "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ",
            meaning = "Güç ve kuvvet ancak yüce Allah'ın yardımıyladır.",
            defaultTarget = 33
        ),
        Dhikr(
            id = "ya_latif",
            title = "Yâ Latîf",
            arabic = "يَا لَطِيفُ",
            meaning = "Lütuf, ihsan ve merhameti sonsuz olan.",
            defaultTarget = 129
        ),
        Dhikr(
            id = "serbest",
            title = "Serbest Zikir",
            arabic = "ذِكْرٌ",
            meaning = "Hedef sınırlaması olmaksızın dilediğinizce zikir çekin.",
            defaultTarget = 0
        )
    )
}
