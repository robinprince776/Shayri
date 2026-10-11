package com.example.data.catalog

import com.example.data.model.Poem
import com.example.data.model.Poet

object PoetsCatalog {

    val poets: List<Poet> = listOf(
        Poet(
            id = "ghalib",
            name = "Mirza Ghalib",
            originalName = "मिर्ज़ा ग़ालिब",
            transliteration = "Mirza Ghalib",
            era = "1797 – 1869 (Mughal Daur, Delhi)",
            movement = "Classical Shayari / Dilli School",
            biography = "Mirza Asadullah Baig Khan 'Ghalib' Urdu aur Hindi shayari ke sabse azeem shayar maane jaate hain. Ghalib ki shayari me zindagi ka falsafa, ishq ka dard, aur gehra intellect jhalakta hai.",
            majorWorks = listOf("Diwan-e-Ghalib", "Ghalib ke Khatoot", "Kulliyat-e-Ghalib"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Shayari", "Sher"),
            sourceAttribution = "Diwan-e-Ghalib (Public Domain)",
            rightsStatus = "Public Domain (Historic)",
            relatedPoetIds = listOf("mir", "jaun_elia", "iqbal"),
            famousSher = "हज़ारों ख़्वाहिशें ऐसी कि हर ख़्वाहिश पे दम निकले\nबहुत निकले मिरे अरमान लेकिन फिर भी कम निकले"
        ),
        Poet(
            id = "jaun_elia",
            name = "Jaun Elia",
            originalName = "जौन एलिया",
            transliteration = "Jaun Elia",
            era = "1931 – 2002 (Modern Daur)",
            movement = "Modern Dard / Nihilist Romanticism",
            biography = "Syed Sibt-e-Ashghar Naqvi, jinhe Jaun Elia ke naam se jaana jaata hai, Amroha me paida hue. Jaun ki shayari me tanhai, dard, be-dili, aur sidha dil ko chhone wali raw feelings milti hain.",
            majorWorks = listOf("Shayad", "Ya'ani", "Gumaan", "Lekin", "Farnood"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Nazm", "Qata"),
            sourceAttribution = "Kitab 'Shayad' se pramanit sher",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("ghalib", "faraz", "shakir"),
            famousSher = "बे-दिली क्या यूँ ही दिन गुज़र जाएँगे\nसिर्फ़ ज़िंदा रहे हम तो मर जाएँगे"
        ),
        Poet(
            id = "mir",
            name = "Mir Taqi Mir",
            originalName = "मीर तक़ी मीर",
            transliteration = "Mir Taqi Mir",
            era = "1723 – 1810 (Khuda-e-Sukhan)",
            movement = "Shayari ke Badshah / Dilli School",
            biography = "Mir Taqi Mir ko 'Khuda-e-Sukhan' yaani Shayari ka Khuda kaha jaata hai. Mir ke ashaar me sadagi, dil ka dard, aur lafzo ki aisi mithas hai jo sidha rooh me utar jaati hai.",
            majorWorks = listOf("Kulliyat-e-Mir", "Nukat-ush-Shu'ara", "Zikr-e-Mir"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Sher", "Masnavi"),
            sourceAttribution = "Kulliyat-e-Mir (Public Domain)",
            rightsStatus = "Public Domain",
            relatedPoetIds = listOf("ghalib", "jaun_elia"),
            famousSher = "पत्ता पत्ता बूटा बूटा हाल हमारा जाने है\nजाने न जाने गुल ही न जाने बाग़ तो सारा जाने है"
        ),
        Poet(
            id = "faiz",
            name = "Faiz Ahmed Faiz",
            originalName = "फ़ैज़ अहमद फ़ैज़",
            transliteration = "Faiz Ahmed Faiz",
            era = "1911 – 1984 (Taraqqi-pasand Daur)",
            movement = "Progressive Writers Movement / Inquilab aur Ishq",
            biography = "Faiz Ahmed Faiz ne ishq aur inquilab ko ek sath piroya. Unki nazmon me mehboob ke husn ke sath sath samaaj ke dard aur azaadi ki pukaar hai.",
            majorWorks = listOf("Naqsh-e-Faryadi", "Dast-e-Saba", "Zindan-Nama", "Nuskha-e-Wafa"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Nazm", "Ghazal", "Geet"),
            sourceAttribution = "Kulliyat-e-Faiz",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("sahir", "faraz", "rahat"),
            famousSher = "मुझ से पहली सी मोहब्बत मिरे महबूब न माँग\nमैंने समझा था कि तू है तो दरख़्शाँ है हयात"
        ),
        Poet(
            id = "sahir",
            name = "Sahir Ludhianvi",
            originalName = "साहिर लुधियानवी",
            transliteration = "Sahir Ludhianvi",
            era = "1921 – 1980 (Cinema aur Shayari)",
            movement = "Inquilabi Geetkar / Pragatisheel",
            biography = "Abdul Hayee 'Sahir' Ludhiana ke rehne wale the. Sahir ne Hindi cinema aur shayari dono ko naye aayam diye. Unke geet samajik sach aur aam insaan ki aawaz hain.",
            majorWorks = listOf("Talkhiyan", "Parchhaiyan", "Aao Ke Koi Khwab Bunein"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Geet", "Nazm", "Shayari"),
            sourceAttribution = "Talkhiyan (Kitab Numa)",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("faiz", "rahat"),
            famousSher = "वो सुबह कभी तो आएगी वो सुबह कभी तो आएगी\nइन काली सदियों के सर से जब रात का आँचल ढलकेगा"
        ),
        Poet(
            id = "faraz",
            name = "Ahmad Faraz",
            originalName = "अहमद फ़राज़",
            transliteration = "Ahmad Faraz",
            era = "1931 – 2008 (Romantic Shayari)",
            movement = "Modern Romanticism & Baghavat",
            biography = "Ahmad Faraz apne romantic ashaar aur musical lehje ke liye mashhoor hain. Faraz ne tanashahi ke khilaaf bhi baghavati shayari likhi aur jail bhi gaye.",
            majorWorks = listOf("Tanha Tanha", "Dard-e-Aashob", "Janaan Janaan", "Shab-e-Khoon"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Nazm"),
            sourceAttribution = "Kulliyat-e-Faraz",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("jaun_elia", "faiz", "shakir"),
            famousSher = "सिलसिले तोड़ गया वो सभी जाते जाते\nवरना इतने तो मरासिम थे कि आते जाते"
        ),
        Poet(
            id = "iqbal",
            name = "Allama Iqbal",
            originalName = "अल्लामा इक़बाल",
            transliteration = "Allama Iqbal",
            era = "1877 – 1938 (Falsafa aur Khudi)",
            movement = "Shair-e-Mashriq / Khudi ka Sandesh",
            biography = "Allama Iqbal ne 'Khudi' (aatma-samman aur self-realization) ka falsafa diya. Unke ashaar insaan ko aage badhne aur himmat se jeene ki prerna dete hain.",
            majorWorks = listOf("Bang-e-Dra", "Bal-e-Jibril", "Zarb-e-Kaleem"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Nazm", "Falsafa"),
            sourceAttribution = "Kulliyat-e-Iqbal (Public Domain)",
            rightsStatus = "Public Domain",
            relatedPoetIds = listOf("ghalib", "kabir"),
            famousSher = "ख़ुदी को कर बुलंद इतना कि हर तक़दीर से पहले\nख़ुदा बंदे से ख़ुद पूछे बता तेरी रज़ा क्या है"
        ),
        Poet(
            id = "shakir",
            name = "Parveen Shakir",
            originalName = "परवीन शाकिर",
            transliteration = "Parveen Shakir",
            era = "1952 – 1994 (Khushboo ki Shayara)",
            movement = "Stri Sanvedna aur Nazuk Ehsaas",
            biography = "Parveen Shakir ne ghazal me ladki ke ehsaasaat, khushboo, barish aur aatmiyata ko khoobsurat roop diya. Unki pehli kitab 'Khushboo' ne dhoom macha di thi.",
            majorWorks = listOf("Khushboo", "Sad-Barg", "Khud-Kalami", "Inkaar"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Nazm"),
            sourceAttribution = "Kulliyat Maah-e-Tamaam",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("faraz", "jaun_elia"),
            famousSher = "वो तो ख़ुशबू है हवाओं में बिखर जाएगा\nमसअला फूल का है फूल किधर जाएगा"
        ),
        Poet(
            id = "firaq",
            name = "Firaq Gorakhpuri",
            originalName = "फ़िराक़ गोरखपुरी",
            transliteration = "Firaq Gorakhpuri",
            era = "1896 – 1982 (Jnanpith Puraskar)",
            movement = "Hindi-Urdu Classical Sangam",
            biography = "Raghupati Sahay 'Firaq' Allahabad University me professor the. Firaq sahab ne Hindustani sanskriti aur shringar rasa ko shayari me naya roop diya.",
            majorWorks = listOf("Gul-e-Naghma", "Bazm-e-Zindagi", "Shabnamistan"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Ghazal", "Rubai"),
            sourceAttribution = "Gul-e-Naghma (Sahitya Akademi)",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("ghalib", "mir"),
            famousSher = "शाम भी थी धुआँ धुआँ हुस्न भी था उदास उदास\nदिल को कई कहानियाँ याद सी आ के रह गईं"
        ),
        Poet(
            id = "bulleh_shah",
            name = "Bulleh Shah",
            originalName = "बुल्ले शाह",
            transliteration = "Baba Bulleh Shah",
            era = "1680 – 1757 (Sufi Sant)",
            movement = "Sufi Prem aur Bhedbhav Virodh",
            biography = "Baba Bulleh Shah Kasur ke mahan Sufi sant the. Shah Inayat ke shishya Bulleh Shah ne jaat-paat aur dikhawe ke dharmik kando par chot ki aur sachhe prem ka geet gaya.",
            majorWorks = listOf("Kafiyan Bulleh Shah", "Bait", "Dohre"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Kafi", "Doha", "Bait"),
            sourceAttribution = "Kalam Bulleh Shah (Public Domain)",
            rightsStatus = "Public Domain",
            relatedPoetIds = listOf("kabir"),
            famousSher = "बुल्लया की जाणा मैं कौन\nना मैं मोमिन विच मसीत हाँ, ना मैं विच कुफ़्र दी रीत हाँ"
        ),
        Poet(
            id = "kabir",
            name = "Kabir Das",
            originalName = "कबीर दास",
            transliteration = "Sant Kabir Das",
            era = "1398 – 1518 (Bhakti Kaal)",
            movement = "Nirgun Bhakti & Samaj Sudhar",
            biography = "Sant Kabir Das Kashi ke mahan sant the. Kabir ke dohe aatam-gyan, satya, aatam-samman aur prem ka anmol updesh dete hain jo aaj bhi utne hi sachhe hain.",
            majorWorks = listOf("Kabir Bijak", "Kabir Sakhi", "Kabir Granthavali"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Dohe", "Sakhi", "Pada"),
            sourceAttribution = "Kabir Bijak (Public Domain)",
            rightsStatus = "Public Domain",
            relatedPoetIds = listOf("bulleh_shah"),
            famousSher = "पोथी पढ़ि पढ़ि जग मुआ, पंडित भया न कोय ।\nढाई आखर प्रेम का, पढ़े सो पंडित hoy ॥"
        ),
        Poet(
            id = "rahat",
            name = "Rahat Indori",
            originalName = "राहत इन्दौरी",
            transliteration = "Rahat Indori",
            era = "1950 – 2020 (Mushaira Badshah)",
            movement = "Aam Janta ki Aawaz / Be-khauf Shayari",
            biography = "Dr. Rahat Indori mushairo ke dil ki dhadkan the. Unke teekhe, sachhe aur be-khauf ashaar ne desh-videsh ke croro dilon par raaj kiya.",
            majorWorks = listOf("Dhoop Bahut Hai", "Do Kadam Aur Sahi", "Naraz", "Maujood"),
            languages = listOf("Hinglish", "Hindi"),
            forms = listOf("Mushaira Sher", "Ghazal", "Nazm"),
            sourceAttribution = "Dhoop Bahut Hai & Naraz",
            rightsStatus = "Literary Excerpt",
            relatedPoetIds = listOf("faiz", "sahir"),
            famousSher = "सभी का ख़ून है शामिल यहाँ की मिट्टी में\nकिसी के बाप का हिन्दोस्ताँ थोड़ी है"
        )
    )

    val poems: List<Poem> = listOf(
        // Mirza Ghalib 1
        Poem(
            id = "ghalib_1",
            poetId = "ghalib",
            poetName = "Mirza Ghalib",
            title = "Hazaron Khwahishen Aisi (हज़ारों ख़्वाहिशें ऐसी)",
            text = """हज़ारों ख़्वाहिशें ऐसी कि हर ख़्वाहिश पे दम निकले
बहुत निकले मिरे अरमान लेकिन फिर भी कम निकले

डरे क्यों मेरा क़ातिल क्या रहेगा उसकी गर्दन पर
वो ख़ूँ जो चश्म-ए-तर से उम्र भर यूँ दम-ब-दम निकले

निकलना ख़ुल्द से आदम का सुनते आए थे लेकिन
बहुत बे-आबरू होकर तिरे कूचे से हम निकले

मोहब्बत में नहीं है फ़र्क़ जीने और मरने का
उसी को देख कर जीते हैं जिस काफ़िर पे दम निकले

कहाँ मैख़ाने का दरवाज़ा 'ग़ालिब' और कहाँ वाइज़
पर इतना जानते हैं कल वो जाता था कि हम निकले""",
            script = "Hindi",
            transliteration = """Hazāroñ khvāhisheñ aisī ki har khvāhish pe dam nikle
Bahut nikle mire armān lekin phir bhī kam nikle

Dare kyoñ merā qātil kyā rahegā us kī gardan par
Voh ḳhūñ jo chashm-e-tar se umr bhar yūñ dam-ba-dam nikle

Nikalnā ḳhuld se ādam kā sunte aa.e the lekin
Bahut be-ābrū ho kar tire kūche se ham nikle

Muhabbat meñ nahīñ hai farq jiine aur marne kā
Usī ko dekh kar jiite haiñ jis kāfir pe dam nikle

Kahāñ mai-ḳhāne kā darvāza Ghalib aur kahāñ vaa'iz
Par itnā jānte haiñ kal voh jaatā thā ki ham nikle""",
            translation = """Thousands of desires, each one worth dying for;
Many of my longings were fulfilled, yet so few they still seem!

In love there is no difference between living and dying;
We live only by looking at the one for whom we are ready to die!""",
            genre = "Ghazal",
            mood = "Philosophy",
            radif = "निकले (nikle)",
            qafiya = "दम, कम, हम, दम-ब-दम (dam, kam, ham)",
            beher = "Bahr-e-Ramal",
            difficultWords = mapOf(
                "ख़ुल्द (Khuld)" to "Swarg / Jannat (Paradise)",
                "कूचा (Koocha)" to "Mehboob ki gali (Beloved's street)",
                "चश्म-ए-तर (Chashm-e-Tar)" to "Aansuon se bhari aankhein",
                "वाइज़ (Vaa'iz)" to "Dharmik updeshak (Preacher)",
                "मैख़ाना (Maikhana)" to "Madhushala / Tavern"
            ),
            tashreeh = "Ghalib kehte hain ki insaan ki khwahishein anant hain—kitni bhi poori ho jayein, hamesha kam hi lagti hain. Yeh sher insaani aakanksha aur lalsa ka shreshth varnan hai.",
            source = "Diwan-e-Ghalib, Ghazal #189",
            rightsInfo = "Public Domain"
        ),

        // Mirza Ghalib 2
        Poem(
            id = "ghalib_2",
            poetId = "ghalib",
            poetName = "Mirza Ghalib",
            title = "Dil-e-Nadan Tujhe Hua Kya Hai (दिल-ए-नादाँ तुझे हुआ क्या है)",
            text = """दिल-ए-नादाँ तुझे हुआ क्या है
आख़िर इस दर्द की दवा क्या है

हम हैं मुश्ताक़ और वो बेज़ार
या इलाही ये माजरा क्या है

मैं भी मुँह में ज़बान रखता हूँ
काश पूछो कि मुद्दआ क्या है

हम को उनसे वफ़ा की है उम्मीद
जो नहीं जानते वफ़ा क्या है

जान तुम पर निसार करता हूँ
मैं नहीं जानता दुआ क्या है""",
            script = "Hindi",
            transliteration = """Dil-e-nādāñ tujhe huā kyā hai
Aakhir is dard kī davā kyā hai

Ham haiñ mushtāq aur voh bezār
Yā ilāhī yeh mājarā kyā hai

Maiñ bhī muñh meñ zabān rakhtā hūñ
Kaash pūchho ki mudda'ā kyā hai

Ham ko un se vafā kī hai umīd
Jo nahīñ jānte vafā kyā hai

Jān tum par nisār kartā hūñ
Maiñ nahīñ jāntā du'ā kyā hai""",
            translation = """O foolish heart, what has happened to you?
After all, what is the cure for this pain?

We hope for faithfulness from the one
Who doesn't even know what faithfulness means!""",
            genre = "Ghazal",
            mood = "Heartbreak",
            radif = "क्या है (kya hai)",
            qafiya = "हुआ, दवा, माजरा, मुद्दआ, वफ़ा, दुआ",
            beher = "Bahr-e-Hazaj",
            difficultWords = mapOf(
                "मुश्ताक़ (Mushtaq)" to "Bechain, Milne ko aatur",
                "बेज़ार (Bezar)" to "Udaas, Tang aaya hua, Anmanaa",
                "मुद्दआ (Mudda'a)" to "Dil ki baat, Asli maksad",
                "निसार (Nisaar)" to "Qurbaan karna (To sacrifice)"
            ),
            tashreeh = "Ghalib apne bhole dil se mukhatib hokar kehte hain ki aakhir tu aise shakhs se kyu wafa mangta hai jise wafa ka matlab bhi nahi pata.",
            source = "Diwan-e-Ghalib, Ghazal #151",
            rightsInfo = "Public Domain"
        ),

        // Jaun Elia 1
        Poem(
            id = "jaun_1",
            poetId = "jaun_elia",
            poetName = "Jaun Elia",
            title = "Be-Dili Kya Yunhi Din Guzar Jayenge (बे-दिली क्या यूँ ही दिन गुज़र जाएँगे)",
            text = """बे-दिली क्या यूँ ही दिन गुज़र जाएँगे
सिर्फ़ ज़िंदा रहे हम तो मर जाएँगे

ये तमाशा भी देखेंगे इक रोज़ हम
आप अपने ही दामन को कतर जाएँगे

अब तो वो भी नहीं जो कहा करते थे
हम तिरी ज़ुल्फ़ के पेच-ओ-ख़म बन जाएँगे

कितनी वहशत है इस शहर के शोर में
हम किसी शाम तन्हा ही घर जाएँगे""",
            script = "Hindi",
            transliteration = """Be-dilī kyā yūñ hī din guzar jā.eñge
Sirf zinda rahe ham to mar jā.eñge

Yeh tamāshā bhī dekheñge ik roz ham
Aap apne hī dāman ko katar jā.eñge

Ab to voh bhī nahīñ jo kahā karte the
Ham tirī zulf ke pech-o-ḳham ban jā.eñge

Kitnī vahshat hai is shahr ke shor meñ
Ham kisī shām tanhā hī ghar jā.eñge""",
            translation = """In this joylessness, will our days simply pass away?
If we merely keep breathing, we shall surely die!

How much dread there is in this city's noise;
One evening, we shall quietly return home alone.""",
            genre = "Ghazal",
            mood = "Loneliness",
            radif = "जाएँगे (jaenge)",
            qafiya = "गुज़र, मर, कतर, घर",
            beher = "Bahr-e-Mutaqarib",
            difficultWords = mapOf(
                "बे-दिली (Be-dili)" to "Udaasi, bina mann ke jeena",
                "वहशत (Vahshat)" to "Dar, Akelapan, Sannata",
                "पेच-ओ-ख़म (Pech-o-Kham)" to "Ghumavdaar zulfein"
            ),
            tashreeh = "Jaun Elia ka yeh sher batata hai ki sirf saans lene ko jeena nahi kehte; jab dil me koi khushi ya maqsad na ho to insaan zinda hokar bhi murda sa hota hai.",
            source = "Kitab 'Shayad' (1991)",
            rightsInfo = "Literary Excerpt"
        ),

        // Mir Taqi Mir 1
        Poem(
            id = "mir_1",
            poetId = "mir",
            poetName = "Mir Taqi Mir",
            title = "Patta Patta Boota Boota (पत्ता पत्ता बूटा बूटा)",
            text = """पत्ता पत्ता बूटा बूटा हाल हमारा जाने है
जाने न जाने गुल ही न जाने बाग़ तो सारा जाने है

लगी हुई है आग जिगर में काहे को कहिए दिल की बात
सोख़्ता-जाँ हैं इश्क़ के मारे दुनिया क्या पहचाने है

आगे किसू के क्या करें दस्त-ए-तमा' दराज़ हम
एक वो बे-नियाज़ है सबकी जो हाजत-रवाई जाने है""",
            script = "Hindi",
            transliteration = """Pattā pattā būṭā būṭā haal hamārā jāne hai
Jāne na jāne gul hī na jāne baaġh to saarā jāne hai

Lagī huī hai aag jigar meñ kāhe ko kahiye dil kī baat
Sokhta-jāñ haiñ ishq ke maare duniyā kyā pahchāne hai

Aage kisū ke kyā kareñ dast-e-tama' darāz ham
Ek voh be-niyāz hai sab kī jo hājat-ravāī jāne hai""",
            translation = """Every leaf, every little plant knows our state;
Whether the rose knows or not, the whole garden knows!""",
            genre = "Ghazal",
            mood = "Love",
            radif = "जाने है (jaane hai)",
            qafiya = "हमारा, सारा, पहचाने",
            beher = "Bahr-e-Ramal",
            difficultWords = mapOf(
                "बूटा (Boota)" to "Chhota paudha / Shrub",
                "गुल (Gul)" to "Gulaab / Mehboob",
                "सोख़्ता-जाँ (Sokhta-Jaan)" to "Ishq ki aag me jala hua dil",
                "बे-नियाज़ (Be-Niyaz)" to "Khuda jo kisi ka mohtaj nahi"
            ),
            tashreeh = "Mir sahab kehte hain ki hamara ishq aur dard poori qainaat jaanti hai, bas wahi ek mehboob be-khabar hai jiske liye ham tadap rahe hain.",
            source = "Kulliyat-e-Mir",
            rightsInfo = "Public Domain"
        ),

        // Faiz Ahmed Faiz 1
        Poem(
            id = "faiz_1",
            poetId = "faiz",
            poetName = "Faiz Ahmed Faiz",
            title = "Mujh Se Pehli Si Mohabbat (मुझ से पहली सी मोहब्बत)",
            text = """मुझ से पहली सी मोहब्बत मिरे महबूब न माँग
मैंने समझा था कि तू है तो दरख़्शाँ है हयात
तेरा ग़म है तो ग़म-ए-दहर का झगड़ा क्या है
तेरी सूरत से है आलम में बहारों को सबात

और भी दुख हैं ज़माने में मोहब्बत के सिवा
राहतें और भी हैं वस्ल की राहत के सिवा

तू भी दिलकश है मगर क्या कीजिए
मुझ से पहली सी मोहब्बत मिरे महबूब न माँग""",
            script = "Hindi",
            transliteration = """Mujh se pahlī sī muhabbat mire mahbūb na maang
Maiñ ne samjhā thā ki tū hai to daraḳhshāñ hai hayāt
Terā ġham hai to ġham-e-dahr kā jhagṛā kyā hai
Terī sūrat se hai aalam meñ bahāroñ ko sabāt

Aur bhī dukh haiñ zamāne meñ muhabbat ke sivā
Rāhateñ aur bhī haiñ vasl kī rāhat ke sivā

Tū bhī dilkash hai magar kyā kījiye
Mujh se pahlī sī muhabbat mire mahbūb na maang""",
            translation = """Do not ask of me, my love, the love I once gave you!
There are other sorrows in the world beyond love;
There are other comforts beyond the solace of our union!""",
            genre = "Nazm",
            mood = "Rebellion",
            radif = "",
            qafiya = "",
            beher = "Azad Nazm",
            difficultWords = mapOf(
                "दरख़्शाँ (Darakhshan)" to "Chamakdar, Roshan",
                "ग़म-ए-दहर (Gham-e-Dahr)" to "Duniya bhar ke dukh aur dard",
                "वस्ल (Vasl)" to "Milan, Mehboob se mulaqat"
            ),
            tashreeh = "Faiz kahte hain ki sirf aashiqana prem me doobe rehne se duniya ke gareebi, zulm aur bhookh ke dukh gayab nahi ho jaate. Samaj ke dukh bhi hamare hain.",
            source = "Naqsh-e-Faryadi (1941)",
            rightsInfo = "Literary Archive"
        ),

        // Allama Iqbal 1
        Poem(
            id = "iqbal_1",
            poetId = "iqbal",
            poetName = "Allama Iqbal",
            title = "Khudi Ko Kar Buland Itna (ख़ुदी को कर बुलंद इतना)",
            text = """ख़ुदी को कर बुलंद इतना कि हर तक़दीर से पहले
ख़ुदा बंदे से ख़ुद पूछे बता तेरी रज़ा क्या है

सितारों से आगे जहाँ और भी हैं
अभी इश्क़ के इम्तिहाँ और भी हैं

क़नाअत न कर आलम-ए-रंग-ओ-बू पर
चमन और भी आशियाँ और भी हैं

तू शाहीं है परवाज़ है काम तेरा
तिरे सामने आसमाँ और भी हैं""",
            script = "Hindi",
            transliteration = """Khudī ko kar buland itnā ki har taqdīr se pahle
Khudā bande se ḳhud pūchhe batā terī razā kyā hai

Sitāroñ se aage jahāñ aur bhī haiñ
Abhī ishq ke imtihāñ aur bhī haiñ

Qanā'at na kar aalam-e-rang-o-bū par
Chaman aur bhī āshiyāñ aur bhī haiñ

Tū shāhīñ hai parvāz hai kaam terā
Tire sāmne āsmāñ aur bhī haiñ""",
            translation = """Elevate your Selfhood so high that before every decree of destiny,
God Himself asks His servant: Tell me, what is your desire?

You are a royal falcon; soaring in the skies is your purpose;
Before you lie infinite heavens yet to explore!""",
            genre = "Ghazal",
            mood = "Self-Respect",
            radif = "और भी हैं (aur bhi hain)",
            qafiya = "जहाँ, इम्तिहाँ, आशियाँ, आसमाँ",
            beher = "Bahr-e-Hazaj",
            difficultWords = mapOf(
                "ख़ुदी (Khudi)" to "Aatma-samman, Self-respect, Antar-aatam",
                "रज़ा (Raza)" to "Icchha, Marzi (Desire)",
                "शाहीं (Shaheen)" to "Baaz, Uunchi udaan bharne wala parinda",
                "परवाज़ (Parwaaz)" to "Udaan (Flight)"
            ),
            tashreeh = "Iqbal insaan ko kismat ke bharose baithne se mana karte hain. Apne aatam-bal ko itna uncha banao ki kismat tumhare faisle se tay ho.",
            source = "Bal-e-Jibril (1935)",
            rightsInfo = "Public Domain"
        ),

        // Sant Kabir 1
        Poem(
            id = "kabir_1",
            poetId = "kabir",
            poetName = "Kabir Das",
            title = "Pothi Padhi Padhi Jag Mua (पोथी पढ़ि पढ़ि जग मुआ)",
            text = """पोथी पढ़ि पढ़ि जग मुआ, पंडित भया न कोय ।
ढाई आखर प्रेम का, पढ़े सो पंडित hoy ॥

बुरा जो देखन मैं चला, बुरा न मिलिया कोय ।
जो दिल खोजा आपना, मुझसे बुरा न कोय ॥

माटी कहे कुम्हार से, तू क्या रूँदे मोय ।
इक दिन ऐसा आएगा, मैं रूँदूँगी तोय ॥

दुःख में सुमिरन सब करे, सुख में करे न कोय ।
जो सुख में सुमिरन करे, तो दुःख काहे को hoy ॥""",
            script = "Hindi",
            transliteration = """Pothi padhi padhi jag mua, pandit bhaya na koy.
Dhai aakhar prem ka, padhe so pandit hoy.

Bura jo dekhan main chala, bura na miliya koy.
Jo dil khoja aapna, mujhse bura na koy.

Mati kahe kumhar se, tu kya roonde moy.
Ik din aisa aayega, main roondoongi toy.

Dukh mein sumiran sab kare, sukh mein kare na koy.
Jo sukh mein sumiran kare, to dukh kahe ko hoy.""",
            translation = """The world read heavy books and scriptures, yet none became wise;
The one who truly learns love—he alone is the real sage!

When I went looking for evil in the world, I found none;
When I looked within my own heart, no one was more flawed than me!""",
            genre = "Dohe",
            mood = "Healing",
            radif = "होय / कोय",
            qafiya = "मुआ, भया, आपना",
            beher = "Traditional Doha (13-11 Matras)",
            difficultWords = mapOf(
                "पोथी (Pothi)" to "Badi dharmik kitabein",
                "ढाई आखर (Dhai Aakhar)" to "Prem shabd ke 2.5 akshar",
                "सुमिरन (Sumiran)" to "Yaad karna, Dhyan lagana"
            ),
            tashreeh = "Kabir Das ji kehte hain ki kitabi gyan se bada dil ka prem aur aatam-nirikshan hota hai.",
            source = "Kabir Bijak (Kashi)",
            rightsInfo = "Public Domain"
        ),

        // Rahat Indori 1
        Poem(
            id = "rahat_1",
            poetId = "rahat",
            poetName = "Rahat Indori",
            title = "Sabhi Ka Khoon Hai Shamil (सभी का ख़ून है शामिल)",
            text = """अगर ख़िलाफ़ हैं होने दो जान थोड़ी है
ये सब धुआँ है कोई आसमान थोड़ी है

लगेगी आग तो आएँगे घर कई ज़द में
यहाँ पे सिर्फ़ हमारा मकान थोड़ी है

सभी का ख़ून है शामिल यहाँ की मिट्टी में
किसी के बाप का हिन्दोस्ताँ थोड़ी है

जो आज साहिब-ए-मसनद हैं कल नहीं होंगे
किराएदार हैं ज़ाती मकान थोड़ी है""",
            script = "Hindi",
            transliteration = """Agar khilaaf hain hone do jaan thodi hai
Ye sab dhuaan hai koi aasmaan thodi hai

Lagegi aag to aayenge ghar kai zad mein
Yahan pe sirf hamara makaan thodi hai

Sabhi ka khoon hai shamil yahan ki mitti mein
Kisi ke baap ka hindostan thodi hai

Jo aaj sahib-e-masnad hain kal nahi honge
Kirayedaar hain zaati makaan thodi hai""",
            translation = """If they oppose, let them; it is not our life that is taken!
All this is mere smoke, not the eternal sky!
Everyone's blood has nourished this soil;
Hindustan is not anyone's personal property!""",
            genre = "Mushaira Sher",
            mood = "Rebellion",
            radif = "थोड़ी है (thodi hai)",
            qafiya = "जान, आसमान, मकान, हिन्दोस्ताँ",
            beher = "Bahr-e-Ramal",
            difficultWords = mapOf(
                "साहिब-ए-मसनद (Sahib-e-Masnad)" to "Kursi/Satta par baithne wale",
                "ज़द (Zad)" to "Lapet me aana",
                "ज़ाती (Zaati)" to "Niji, Apna khud ka"
            ),
            tashreeh = "Rahat Indori sahab ka yeh sher desh-prem, aazadi aur aam janta ki taaqat ka prateek ban gaya hai.",
            source = "Kitab 'Naraz' / Mushaira Archives",
            rightsInfo = "Literary Excerpt"
        )
    )

    fun getPoetById(id: String): Poet? = poets.find { it.id == id }

    fun getPoemsForPoet(poetId: String): List<Poem> = poems.filter { it.poetId == poetId }

    fun searchPoets(query: String): List<Poet> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return poets
        return poets.filter {
            it.name.lowercase().contains(q) ||
            it.originalName.contains(q) ||
            it.era.lowercase().contains(q) ||
            it.biography.lowercase().contains(q)
        }
    }

    fun searchPoems(query: String): List<Poem> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return poems
        return poems.filter {
            it.title.lowercase().contains(q) ||
            it.text.contains(q) ||
            it.poetName.lowercase().contains(q) ||
            it.transliteration.lowercase().contains(q)
        }
    }
}
