package com.example.data.catalog

import com.example.data.model.LiteraryResource

object ResourcesCatalog {

    val categories: List<String> = listOf(
        "All",
        "Ghazal Structure",
        "Radif & Qafiya",
        "Beher & Meter",
        "Rhyme Dictionaries",
        "Urdu Terminology",
        "Hindi Poetic Vocabulary",
        "Punjabi Vocabulary",
        "Metaphors & Imagery",
        "Songwriting Templates",
        "Literary Devices",
        "Writing Exercises"
    )

    val resources: List<LiteraryResource> = listOf(
        // Ghazal Structure
        LiteraryResource(
            id = "ghazal_anatomy",
            category = "Ghazal Structure",
            title = "Anatomy of the Classical Ghazal",
            subtitle = "Matla, Maqta, Misra-e-Ula, and Misra-e-Sani",
            explanation = "A Ghazal is not a continuous narrative poem; it is an anthology of autonomous two-line poems called Ash'aar (plural of Sher). Each couplet is complete in itself, expressing an entire universe of emotion or thought. The first couplet is known as 'Matla' (where both lines rhyme), and the closing couplet is the 'Maqta' (where the poet weaves their pen name or Takhallus).",
            examples = listOf(
                "Misra-e-Ula (Pehli line): हज़ारों ख़्वाहिशें ऐसी कि हर ख़्वाहिश पे दम निकले",
                "Misra-e-Sani (Doosri line): बहुत निकले मिरे अरमान लेकिन फिर भी कम निकले",
                "Matla: Dono lines ka aakhiri hissa (Qafiya & Radif) match karta hai.",
                "Maqta: Isme shayar ka naam (Takhallus, jaise 'ग़ालिब' / Ghalib) hota hai."
            ),
            keyRules = listOf(
                "Every Sher must be strictly self-contained in meaning.",
                "All lines in the Ghazal must share the exact same Beher (poetic meter).",
                "Minimum length is usually 5 couplets (panj-sher), extending to 7, 9, or 11."
            ),
            practicalTip = "Never force a storyline between couplets in a classical ghazal. Think of each couplet as an independent jewel strung on the common thread of rhythm and rhyme.",
            downloadableTemplate = """[Matla - Opening Couplet]
Line 1: ________________ [Qafiya A] [Radif]
Line 2: ________________ [Qafiya B] [Radif]

[Sher 2]
Line 3: ________________
Line 4: ________________ [Qafiya C] [Radif]

[Sher 3]
Line 5: ________________
Line 6: ________________ [Qafiya D] [Radif]

[Maqta - Closing Couplet with Takhallus]
Line 7: ________________ [Takhallus]
Line 8: ________________ [Qafiya E] [Radif]"""
        ),

        // Radif and Qafiya
        LiteraryResource(
            id = "radif_qafiya",
            category = "Radif & Qafiya",
            title = "Mastering Radif and Qafiya",
            subtitle = "The anchor and echo of poetic music",
            explanation = "Qafiya is the rhyming word family (the changing harmonic root), while Radif is the exact identical word or phrase that repeats verbatim after the Qafiya at the end of rhyming lines. If Qafiya is the melody, Radif is the steady heartbeat.",
            examples = listOf(
                "Ghalib ka udaharan:",
                "दिल-ए-नादाँ तुझे हुआ (क़ाफ़िया / Qafiya) क्या है (रदीफ़ / Radif)",
                "आख़िर इस दर्द की दवा (क़ाफ़िया / Qafiya) क्या है (रदीफ़ / Radif)",
                "Yahan 'हुआ' aur 'दवा' Qafiya hain; 'क्या है' Radif hai."
            ),
            keyRules = listOf(
                "Qafiya hamesha Radif se theek pehle aata hai.",
                "Bina Radif ki ghazal ko 'Ghair Muraddaf' kehte hain jisme Qafiya par hi line khatam hoti hai.",
                "Qafiya ke aakhiri akshar ki aawaz (tukaant) milni chahiye (jaise: hawa, dawa, wafa, ada)."
            ),
            practicalTip = "Ek aisi Radif chunein jo har sher ke bhav ke sath fit baith sake.",
            downloadableTemplate = """Qafiya Bank for 'Aa' sound:
- Dawa (दवा), Hawa (हवा), Wafa (वफ़ा), Sada (सदा), Ada (अदा), Saza (सज़ा)
Radif chosen: 'होता' ya 'क्या है'"""
        ),

        // Beher and Poetic Meter
        LiteraryResource(
            id = "beher_meter",
            category = "Beher & Meter",
            title = "Beher (Meter) and Taqti (Scansion)",
            subtitle = "The mathematical rhythm of Urdu and Persian poetry",
            explanation = "Poetic meter in Urdu/Hindi is based on syllable weights: Short syllables (Sabab-e-Khafif / 1 matra) vs Long syllables (2 matras). Taqti is the process of breaking down a line into rhythmic rhythmic feet (Afā'īl) such as Faa'ilun (فاعلن), Mafaa'eelun (مفاعیلن), and Faa'ilaatun (فاعلاتن).",
            examples = listOf(
                "Bahr-e-Hazaj (مفاعیلن مفاعیلن مفاعیلن مفاعیلن): Long-Short-Long-Long pattern.",
                "Bahr-e-Ramal (فاعلاتن فاعلاتن فاعلاتن فاعلن): Used by Ghalib in 'Hazaron Khwahishen Aisi'.",
                "Chhand in Hindi/Doha: 13-11 matras per charan."
            ),
            keyRules = listOf(
                "Consonant followed by short vowel = Short (1).",
                "Consonant followed by long vowel (Alif, Wao, Ye) = Long (2).",
                "A closed syllable (consonant + vowel + consonant) = Long (2)."
            ),
            practicalTip = "Read your verse aloud tapping your foot or reciting to a steady pulse. If the tongue stumbles or pauses unnaturally, the meter (wazn) has broken.",
            downloadableTemplate = """Meter Test Worksheet:
Line: [Write line here]
Syllable Weight: [ 2 | 1 | 2 | 2 ] [ 2 | 1 | 2 | 2 ]
Target Beher: Bahr-e-Hazaj (Mafā'īlun Mafā'īlun)"""
        ),

        // Rhyme Dictionaries
        LiteraryResource(
            id = "rhyme_dictionary_classic",
            category = "Rhyme Dictionaries",
            title = "Interactive Poetic Rhyme Bank",
            subtitle = "Verified Qafiya families categorized by ending vowel",
            explanation = "A ready reference of high-frequency poetic rhymes in Urdu, Hindustani, and Hindi designed for songwriters and shayars seeking authentic vocabulary.",
            examples = listOf(
                "Family '-Aar' (ار): Yaar (یار), Pyaar (پیار), Bahaar (بہار), Intezaar (انتظار), Qaraar (قرار), Khumaar (خمار), Dildaar (دلدار), Ghubaar (غبار).",
                "Family '-Oon' (وں / ون): Junoon (جنون), Sukoon (سکون), Afsoon (افسون), Gardoon (گردوں), Zaboon (زبوں).",
                "Family '-Ash' (ش): Talaash (تلاش), Khadaash (خراش), Ma'aash (معاش), Paash-Paash (پاش پاش).",
                "Family '-Ina' (ینہ): Aayeena (آئینہ), Safeena (سفینہ), Ganjeena (گنجینہ), Paseena (پسینہ)."
            ),
            keyRules = listOf(
                "Choose rhymes that expand the conceptual palette instead of forcing obvious cliches.",
                "Verify whether the word carries an open or nasalized closing sound."
            ),
            practicalTip = "Don't write the rhyme first and force the thought second. Conceive your emotional point of view, then pick the most resonant rhyme family.",
            downloadableTemplate = ""
        ),

        // Urdu Terminology
        LiteraryResource(
            id = "urdu_terminology",
            category = "Urdu Terminology",
            title = "Farhang-e-Sukhan (Urdu Poetic Glossary)",
            subtitle = "Essential literary terms, pronunciations, and nuanced meanings",
            explanation = "Classical Urdu poetry relies on rich metaphorical terms with centuries of layered associations. Knowing these words unlocks both reading classical masters and writing refined verse.",
            examples = listOf(
                "Firaaq (فراق): The sorrowful pang of separation from the beloved.",
                "Vasl (وصل): The ecstasy of reunion and communion with the beloved.",
                "Rind (رند): The free-spirited rebel who rejects superficial religious piety.",
                "Sayyaad (صیاد): The hunter / captor, often representing fate or worldly traps.",
                "Nashaiman (نشیمن): The delicate nest built with love, often imperiled by lightning (Barq).",
                "Junoon (جنون): Passion driven to the brink of holy madness.",
                "Takhallus (تخلص): The pen-name sealed within the final couplet."
            ),
            keyRules = listOf(
                "Notice double meanings (Ihaam): many words have both an earthly (Majazi) and mystical (Haqeeqi) connotation."
            ),
            practicalTip = "Use delicate words like 'Vasl' and 'Firaaq' with intention; pair them with modern sensory details to avoid sounding archaic.",
            downloadableTemplate = ""
        ),

        // Hindi Poetic Vocabulary
        LiteraryResource(
            id = "hindi_poetic_words",
            category = "Hindi Poetic Vocabulary",
            title = "Bhav & Rasa (Hindi Poetic Lexicon)",
            subtitle = "Classical terms of emotion, memory, and spiritual devotion",
            explanation = "Poetry in Hindi draws from rich Sanskrit roots and folksy colloquial depth to convey deep emotional states (Bhav) and aesthetic flavors (Rasa).",
            examples = listOf(
                "Virah (विरह): The profound yearning of separation and waiting.",
                "Anuraag (अनुराग): Deep, affectionate devotion that colors the heart.",
                "Spandan (स्पंदन): Inner vibration, subtle heart flutter.",
                "Nirjhar (निर्झर): A cascading mountain stream of creative flow.",
                "Trishna (तृष्णा): Insatiable spiritual or worldly thirst.",
                "Smriti (स्मृति): Sacred fragrance of living memory."
            ),
            keyRules = listOf(
                "Balance Tatsam (classical Sanskrit derivatives) with Tadbhav (spoken vernacular) words for effortless musicality."
            ),
            practicalTip = "Pair earthy verbs with elevated nouns to give Hindi lyrics an immediate, heartfelt texture.",
            downloadableTemplate = ""
        ),

        // Punjabi Vocabulary
        LiteraryResource(
            id = "punjabi_sufi_words",
            category = "Punjabi Vocabulary",
            title = "Kalam-e-Punjab (Punjabi Poetic Terms)",
            subtitle = "The earthy, ecstatic vocabulary of Sufi saints and folk bards",
            explanation = "Punjabi poetry carries raw emotional honesty, maternal warmth, and deep spiritual rebellion rooted in the fertile soil of the Five Rivers.",
            examples = listOf(
                "Birha (ਬਿਰਹਾ / برہا): The supreme fire of separation, sanctified as a king of emotions by Shiv Kumar Batalvi.",
                "Murshid (ਮੁਰਸ਼ਿਦ): The spiritual guide who shatters the ego.",
                "Chan (ਚੰਨ): The moon, universally affectionate metaphor for the beloved child or companion.",
                "Dillagi (ਦਿਲਲਗੀ): Playful banter and affection of the heart.",
                "Khed (ਖੇਡ): The transient worldly life seen as an innocent game."
            ),
            keyRules = listOf(
                "Pay attention to tonal inflections and nasalization in Punjabi verse.",
                "Use colloquial idioms (Akhan) to anchor profound philosophical truths."
            ),
            practicalTip = "In Punjabi poetry, never polish away the raw ache. Simplicity and passion take precedence over ornate ornamentation.",
            downloadableTemplate = ""
        ),

        // Metaphors & Imagery
        LiteraryResource(
            id = "metaphors_imagery",
            category = "Metaphors & Imagery",
            title = "Classical Metaphors: Shama, Parwana & Maykhana",
            subtitle = "The visual universe of Eastern lyrical traditions",
            explanation = "Eastern poetic traditions have developed an iconic iconography of symbols that speak volumes without needing literal exposition.",
            examples = listOf(
                "Shama aur Parwāna (The Candle & The Moth): Unconditional self-sacrifice in the fire of truth or passion.",
                "Gul aur Bulbul (The Rose & The Nightingale): The longing of the devoted singer for the silent, thorny rose.",
                "Maykhāna (The Tavern) & Sāqi (The Cupbearer): The place of divine spiritual ecstasy vs the dogmatic pulpit (Masjid).",
                "Barq (The Lightning): Sudden tragedy or destiny that strikes the fragile nest (Aashiyana)."
            ),
            keyRules = listOf(
                "Tashbeeh (Simile): Comparing A to B directly using 'like' or 'as'.",
                "Isti'aara (Metaphor): Referring to A as B directly without comparative words."
            ),
            practicalTip = "Take a classical metaphor and invert its perspective: what does the Candle feel watching the Moth burn? What does the cage feel about the trapped bird?",
            downloadableTemplate = ""
        ),

        // Songwriting Templates
        LiteraryResource(
            id = "songwriting_structures",
            category = "Songwriting Templates",
            title = "Songwriting Architecture: Mukhda & Antra",
            subtitle = "Building memorable hooks and narrative verses",
            explanation = "The Mukhda (Chorus / Hook) is the emotional thesis of the song that recurs and stays in the listener's mind. The Antra (Verse) is the story progression that builds tension and delivers the listener back into the Mukhda with heightened emotional impact.",
            examples = listOf(
                "Mukhda (Hook): 2 to 4 lines with a catchy rhythmic cadence.",
                "Antra 1 (Verse): Explores the cause of the emotion.",
                "Bridge (Sanchari): Sudden melodic or meter shift.",
                "Antra 2: Climax and resolution."
            ),
            keyRules = listOf(
                "The last line of an Antra must smoothly lead the singer's breath back into the first line of the Mukhda.",
                "Keep the syllable count of the Hook tight and easy to hum."
            ),
            practicalTip = "Always test your Mukhda by humming the melody without words first. If the cadence feels memorable even on a whistle, the lyrics will soar.",
            downloadableTemplate = """[MUKHDA / HOOK]
Line 1: ________________ (Catchy hook)
Line 2: ________________ (Echo & resolution)

[ANTRA 1 / VERSE 1]
Line 3: ________________
Line 4: ________________
Line 5: ________________ (Leads back to Hook)

[RETURN TO MUKHDA]
Line 1 & 2 Repeat"""
        ),

        // Literary Devices
        LiteraryResource(
            id = "literary_devices_badi",
            category = "Literary Devices",
            title = "Sanaye Badae: The Art of Rhetorical Elegance",
            subtitle = "Classical poetic figures of speech and rhetorical craft",
            explanation = "Ilm-ul-Badi' is the science of poetic ornamentation. These rhetorical devices allow poets to produce multiple layers of meaning simultaneously.",
            examples = listOf(
                "San'at-e-Tazād (Antithesis): Bringing opposing concepts together (Life/Death, Light/Shadow, Joy/Grief).",
                "Husn-e-Ta'leel (Poetic Etiology): Assigning a poetic, beautiful cause to a natural phenomenon (e.g., 'The sky is blue because it wears mourning robes for the tragic lovers').",
                "Mir'āt-un-Nazeer (Harmonious Imagery): Using words that belong to the same domain (e.g., Bird, Feather, Cage, Nest, Sky)."
            ),
            keyRules = listOf(
                "Rhetorical devices must serve emotional sincerity; when overused, they turn poetry into cold intellectual puzzles."
            ),
            practicalTip = "Try writing a couplet using San'at-e-Tazad: place two opposite words right next to each other at the center of the line.",
            downloadableTemplate = ""
        ),

        // Writing Exercises
        LiteraryResource(
            id = "writing_exercises",
            category = "Writing Exercises",
            title = "Tazmin & Misra-e-Tarh: Classical Writing Drills",
            subtitle = "Practical drills to sharpen meter, rhyme, and lyrical imagination",
            explanation = "In traditional poetry gatherings, masters provided a 'Misra-e-Tarh' (a prompt line with fixed meter and rhyme). Every poet had to compose a couplet ending on that exact phrase.",
            examples = listOf(
                "Exercise 1: Complete this Misra: 'اب کے ہم بچھڑے تو شاید کبھی خوابوں میں ملیں'",
                "Exercise 2: Write 3 couplets using only words with 2 syllables.",
                "Exercise 3: Write an Urdu couplet where neither word contains the letter 'Noon'."
            ),
            keyRules = listOf(
                "Practice writing one couplet every night before sleeping.",
                "Read your draft in the morning with a fresh, critical eye."
            ),
            practicalTip = "Keep a pocket notebook or use Sukhan's Vault to jot down stray phrases overheard in conversations—they make the most authentic first lines.",
            downloadableTemplate = """Daily Writing Sprint:
1. Target Emotion: [e.g. Self-Respect]
2. Seed Word: [e.g. Roshni]
3. First Line (Premise):
4. Second Line (Punchline / Resolution):"""
        )
    )

    fun searchResources(query: String): List<LiteraryResource> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return resources
        return resources.filter {
            it.title.lowercase().contains(q) ||
            it.subtitle.lowercase().contains(q) ||
            it.category.lowercase().contains(q) ||
            it.explanation.lowercase().contains(q) ||
            it.examples.any { ex -> ex.lowercase().contains(q) } ||
            it.practicalTip.lowercase().contains(q)
        }
    }
}
