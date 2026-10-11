package com.example.data.catalog

import com.example.data.model.DailySher
import com.example.data.model.MoodCategory
import com.example.data.model.WritingTemplate

object MoodsCatalog {

    val moods: List<MoodCategory> = listOf(
        MoodCategory(
            id = "love",
            name = "Ishq & Prem",
            nativeName = "इश्क़ और प्रेम (Love)",
            description = "Saccha prem, samarpan aur aashiqana ehsaas.",
            tagColorHex = 0xFFC6A66B,
            keywords = listOf("इश्क़", "प्रेम", "वस्ल", "महबूब", "दीदार", "धड़कन", "वफ़ा", "Nazar"),
            writingPrompt = "Ek aisi shaant nazar ke baare me likhein jo lafzon se zyada keh gayi ho."
        ),
        MoodCategory(
            id = "heartbreak",
            name = "Dard-e-Dil",
            nativeName = "शिकस्त-ए-दिल (Heartbreak)",
            description = "Toota hua vishwas, bichhadne ka dukh aur ashq.",
            tagColorHex = 0xFF642B40,
            keywords = listOf("दर्द", "ज़ख़्म", "बेवफ़ाई", "हिज्र", "अश्क़", "टूटा दिल", "Tanhai"),
            writingPrompt = "Khaali kamre me ek toote vaade ki goonj par do line likhein."
        ),
        MoodCategory(
            id = "longing",
            name = "Firaaq & Tadap",
            nativeName = "फ़िराक़ और इंतज़ार (Longing)",
            description = "Sitaron bhari raaton me intezaar aur milan ki pyaas.",
            tagColorHex = 0xFFB87D4B,
            keywords = listOf("इंतज़ार", "फ़िराक़", "तड़प", "शब-ए-हिज्रां", "याद", "Umeed"),
            writingPrompt = "Ek aise khat par sher likhein jo likha to gaya par bheja nahi gaya."
        ),
        MoodCategory(
            id = "loneliness",
            name = "Tanhai",
            nativeName = "तन्हाई और ख़ामोशी (Solitude)",
            description = "Bheed me bhi akelapan aur khamosh raaton se baatein.",
            tagColorHex = 0xFF4A4E69,
            keywords = listOf("तन्हाई", "ख़ामोशी", "सन्नाटा", "साया", "Akelapan"),
            writingPrompt = "Ghar ke ek kone me thandi hoti chai aur ghadi ki tik-tik par likhein."
        ),
        MoodCategory(
            id = "nostalgia",
            name = "Yaad-e-Maazi",
            nativeName = "याद-ए-माज़ी (Nostalgia)",
            description = "Bite hue din, bachpan ki galiyan aur purane doston ki yaad.",
            tagColorHex = 0xFF8C7A6B,
            keywords = listOf("याद", "माज़ी", "बचपन", "पुरानी गली", "Guzre Din"),
            writingPrompt = "Us purani gali ki dhool par sher likhein jahan kabhi aapki hansi goonjti thi."
        ),
        MoodCategory(
            id = "rebellion",
            name = "Inquilab & Baghavat",
            nativeName = "इंक़लाब और बग़ावत (Rebellion)",
            description = "Zulm ke khilaaf aawaz aur satya ki ladaai.",
            tagColorHex = 0xFF8E2800,
            keywords = listOf("इंक़लाब", "बग़ावत", "ज़ंजीर", "हक़", "Aawaz", "Khoon"),
            writingPrompt = "Jab khamoshi bik rahi ho, tab sach bolne wale ke jazbe par likhein."
        ),
        MoodCategory(
            id = "hope",
            name = "Umeed & Roshni",
            nativeName = "उम्मीद और रोशनी (Hope)",
            description = "Kaali raat ke baad subah ki pehli kiran ka vishwas.",
            tagColorHex = 0xFFD4AF37,
            keywords = listOf("सहर", "उम्मीद", "चिराग़", "नूर", "बहार", "Roshni"),
            writingPrompt = "Banjar zameen me umeed ka beej bone wale hosle par likhein."
        ),
        MoodCategory(
            id = "self_respect",
            name = "Khudi & Aatam-Samman",
            nativeName = "ख़ुद्दारी और अना (Self-Respect)",
            description = "Apna sir uncha rakhna aur aatam-samman se samjhauta na karna.",
            tagColorHex = 0xFFA0522D,
            keywords = listOf("ख़ुदी", "ग़ैरत", "अना", "वकार", "Aatam Samman"),
            writingPrompt = "Us pyale ko thukrane par likhein jise peene ke liye jhukna pade."
        ),
        MoodCategory(
            id = "philosophy",
            name = "Falsafa",
            nativeName = "फ़लसफ़ा और विचार (Philosophy)",
            description = "Zindagi ka sach, aatma-bodh aur kismat ki gehrai.",
            tagColorHex = 0xFF5C6B73,
            keywords = listOf("हस्ती", "क़ायनात", "तक़दीर", "सच", "Zindagi"),
            writingPrompt = "Kya hum sapna dekh rahe hain, ya hum kisi aur ka sapna hain?"
        ),
        MoodCategory(
            id = "healing",
            name = "Sukoon & Shifa",
            nativeName = "सुकून और शांति (Peace & Healing)",
            description = "Dil ke ghavon ka bharna aur aatmik shanti.",
            tagColorHex = 0xFF52796F,
            keywords = listOf("सुकून", "सबर", "शिफ़ा", "दुआ", "Shanti", "Marham"),
            writingPrompt = "Apne zakhmon ka shukriya adaa karein jinhone aapko jeena sikhaya."
        )
    )

    fun getMoodById(id: String): MoodCategory? = moods.find { it.id == id }
}

object DailySherCatalog {

    val dailyShers: List<DailySher> = listOf(
        DailySher(
            id = "sher_1",
            poetName = "Mirza Ghalib",
            poetId = "ghalib",
            originalText = "हज़ारों ख़्वाहिशें ऐसी कि हर ख़्वाहिश पे दम निकले\nबहुत निकले मिरे अरमान लेकिन फिर भी कम निकले",
            script = "Hindi",
            transliteration = "Hazaron khwahishen aisi ki har khwahish pe dam nikle\nBahut nikle mire arman lekin phir bhi kam nikle",
            translation = "Thousands of desires, each one worth dying for;\nMany longings fulfilled, yet so few they still seem.",
            mood = "Philosophy",
            context = "Ghalib kehte hain ki insaan ki khwahishein be-hisaab hoti hain.",
            source = "Diwan-e-Ghalib"
        ),
        DailySher(
            id = "sher_2",
            poetName = "Jaun Elia",
            poetId = "jaun_elia",
            originalText = "बे-दिली क्या यूँ ही दिन गुज़र जाएँगे\nसिर्फ़ ज़िंदा रहे हम तो मर जाएँगे",
            script = "Hindi",
            transliteration = "Be-dili kya yunhi din guzar jayenge\nSirf zinda rahe ham to mar jayenge",
            translation = "In this joylessness, will our days simply pass away?\nIf we merely keep breathing, we shall surely die.",
            mood = "Loneliness",
            context = "Jaun Elia ka dil ko hila dene wala ashaar.",
            source = "Shayad (Jaun Elia)"
        ),
        DailySher(
            id = "sher_3",
            poetName = "Allama Iqbal",
            poetId = "iqbal",
            originalText = "ख़ुदी को कर बुलंद इतना कि हर तक़दीर से पहले\nख़ुदा बंदे से ख़ुद पूछे बता तेरी रज़ा क्या है",
            script = "Hindi",
            transliteration = "Khudi ko kar buland itna ki har taqdeer se pehle\nKhuda bande se khud poochhe bata teri raza kya hai",
            translation = "Elevate yourself so high that before every destiny,\nGod Himself asks: Tell me, what is your will?",
            mood = "Self-Respect",
            context = "Aatam-samman aur himmat ka amar sandesh.",
            source = "Bal-e-Jibril"
        ),
        DailySher(
            id = "sher_4",
            poetName = "Faiz Ahmed Faiz",
            poetId = "faiz",
            originalText = "दिल ना-उम्मीद तो नहीं नाकाम ही तो है\nलंबी है ग़म की शाम मगर शाम ही तो है",
            script = "Hindi",
            transliteration = "Dil na-umeed to nahi nakaam hi to hai\nLambi hai gham ki shaam magar shaam hi to hai",
            translation = "The heart is not hopeless, it is only a setback;\nThe evening of sorrow is long, but it is only an evening after all.",
            mood = "Hope",
            context = "Dukh ke baad umeed ki subah aane ka vishwas.",
            source = "Dast-e-Saba"
        ),
        DailySher(
            id = "sher_5",
            poetName = "Mir Taqi Mir",
            poetId = "mir",
            originalText = "पत्ता पत्ता बूटा बूटा हाल हमारा जाने है\nजाने न जाने गुल ही न जाने बाग़ तो सारा जाने है",
            script = "Hindi",
            transliteration = "Patta patta boota boota haal hamara jaane hai\nJaane na jaane gul hi na jaane baagh to saara jaane hai",
            translation = "Every leaf, every little plant knows our state;\nWhether the rose knows or not, the entire garden knows.",
            mood = "Love",
            context = "Sari duniya jaanti hai par mehboob be-khabar hai.",
            source = "Kulliyat-e-Mir"
        )
    )

    fun getTodaySher(): DailySher {
        val calendar = java.util.Calendar.getInstance()
        val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)
        val index = dayOfYear % dailyShers.size
        return dailyShers[index]
    }
}

object TemplatesCatalog {

    val templates: List<WritingTemplate> = listOf(
        WritingTemplate(
            id = "ghazal_5_sher",
            title = "Classical 5-Sher Ghazal",
            genre = "Ghazal",
            description = "5 ashaar ka standard format: Matla, do beech ke sher, aur aakhiri Maqta me aapka Takhallus.",
            structureGuide = "Pehle dono misre me Qafiya aur Radif ek jaisa rahega.",
            starterContent = """[Matla - Pehla Sher]
____________________ (क़ाफ़िया) (रदीफ़)
____________________ (क़ाफ़िया) (रदीफ़)

[Sher 2]
____________________
____________________ (क़ाफ़िया) (रदीफ़)

[Sher 3]
____________________
____________________ (क़ाफ़िया) (रदीफ़)

[Sher 4]
____________________
____________________ (क़ाफ़िया) (रदीफ़)

[Maqta - Apna Naam / Takhallus]
____________________
____________________ [आपका नाम] (क़ाफ़िया) (रदीफ़)"""
        ),
        WritingTemplate(
            id = "nazm_free_verse",
            title = "Modern Nazm / Azad Nazm",
            genre = "Nazm",
            description = "Bina kisi bandhan ke apne dil ke bhaav lagataar likhne ke liye.",
            structureGuide = "Rhythm aur gehre ehsaasaat par focus karein.",
            starterContent = """[Title: ]

रात के सन्नाटे में
जब पुराने दर-ओ-दीवार
किसी बिछड़े हुए की याद दोहराते हैं...

हमने वक़्त की धूल से पूछा:
क्या तुम वही लम्हा लौटा सकते हो?"""
        ),
        WritingTemplate(
            id = "song_mukhda_antra",
            title = "Geet / Song: Mukhda & Antra",
            genre = "Hindi / Hinglish Lyrics",
            description = "Film aur gaane ka format: Catchy Mukhda aur kahani bayan karta Antra.",
            structureGuide = "Mukhda har antre ke baad repeat hota hai.",
            starterContent = """[MUKHDA / HOOK]
दिल को क़रार मिल जाएगा
तू मिल जा बहार मिल जाएगा

[ANTRA 1]
हमने गुज़ारी हैं कई रातें तन्हा
तेरे बिना हर लम्हा है बे-मानी सा
अब जो आओ तो ये साँस भी रुक जाए ना...

[RETURN TO MUKHDA]
दिल को क़रार मिल जाएगा!"""
        ),
        WritingTemplate(
            id = "two_line_sher",
            title = "Do Line Shayari (शायरी / शेर)",
            genre = "Shayari",
            description = "Do line me poori baat: Pehli line me setup aur doosri me punchline.",
            structureGuide = "Misra 1 me sawal ya baat, Misra 2 me jawab ya dilchasp mod.",
            starterContent = """[Pehla Misra / Setup]
कभी कभी जो हम ख़ुद से कलाम करते हैं

[Doosra Misra / Resolution]
तो तेरे नाम से हर क़िस्सा तमाम करते हैं"""
        )
    )
}
