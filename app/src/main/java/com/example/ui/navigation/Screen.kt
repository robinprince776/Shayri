package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Write : Screen("write?draftId={draftId}&promptText={promptText}") {
        fun createRoute(draftId: Long? = null, promptText: String? = null): String {
            val dId = draftId?.toString() ?: ""
            val pText = promptText?.let { java.net.URLEncoder.encode(it, "UTF-8") } ?: ""
            return "write?draftId=$dId&promptText=$pText"
        }
    }
    object Poets : Screen("poets")
    object PoetDetail : Screen("poet_detail/{poetId}") {
        fun createRoute(poetId: String): String = "poet_detail/$poetId"
    }
    object Resources : Screen("resources")
    object ResourceDetail : Screen("resource_detail/{resourceId}") {
        fun createRoute(resourceId: String): String = "resource_detail/$resourceId"
    }
    object Mood : Screen("mood?moodId={moodId}") {
        fun createRoute(moodId: String = "love"): String = "mood?moodId=$moodId"
    }
    object Vault : Screen("vault")
    object CardStudio : Screen("card_studio?poemText={poemText}&poetName={poetName}") {
        fun createRoute(poemText: String = "", poetName: String = ""): String {
            val textEnc = java.net.URLEncoder.encode(poemText, "UTF-8")
            val nameEnc = java.net.URLEncoder.encode(poetName, "UTF-8")
            return "card_studio?poemText=$textEnc&poetName=$nameEnc"
        }
    }
    object Settings : Screen("settings")
}
