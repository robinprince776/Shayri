package com.example.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.data.ai.GeminiAiService
import com.example.data.local.SukhanRepository
import com.example.ui.components.SukhanBottomBar
import com.example.ui.screens.CardStudioScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MoodScreen
import com.example.ui.screens.PoetDetailScreen
import com.example.ui.screens.PoetsScreen
import com.example.ui.screens.ResourceDetailScreen
import com.example.ui.screens.ResourcesScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.VaultScreen
import com.example.ui.screens.WriteScreen
import com.example.ui.theme.SukhanBackground
import java.net.URLDecoder

@Composable
fun SukhanNavHost(
    navController: NavHostController,
    repository: SukhanRepository,
    aiService: GeminiAiService,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Splash.route

    val baseRoute = when {
        currentRoute.startsWith("write") -> "write"
        currentRoute.startsWith("home") -> "home"
        currentRoute.startsWith("poets") -> "poets"
        currentRoute.startsWith("vault") -> "vault"
        currentRoute.startsWith("settings") -> "settings"
        else -> ""
    }

    val showBottomBar = baseRoute.isNotEmpty() && currentRoute != Screen.Splash.route

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SukhanBackground,
        bottomBar = {
            if (showBottomBar) {
                SukhanBottomBar(
                    currentRoute = baseRoute,
                    onNavigateToTab = { targetTab ->
                        if (baseRoute != targetTab) {
                            navController.navigate(targetTab) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Splash Screen
            composable(Screen.Splash.route) {
                SplashScreen(
                    onSplashFinished = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }

            // Home Screen
            composable(Screen.Home.route) {
                HomeScreen(
                    repository = repository,
                    onNavigateToWrite = { draftId, promptText ->
                        navController.navigate(Screen.Write.createRoute(draftId, promptText))
                    },
                    onNavigateToPoets = {
                        navController.navigate(Screen.Poets.route)
                    },
                    onNavigateToPoetDetail = { poetId ->
                        navController.navigate(Screen.PoetDetail.createRoute(poetId))
                    },
                    onNavigateToMood = { moodId ->
                        navController.navigate(Screen.Mood.createRoute(moodId))
                    },
                    onNavigateToResources = {
                        navController.navigate(Screen.Resources.route)
                    },
                    onNavigateToCardStudio = { poemText, poetName ->
                        navController.navigate(Screen.CardStudio.createRoute(poemText, poetName))
                    }
                )
            }

            // Write Screen
            composable(
                route = Screen.Write.route,
                arguments = listOf(
                    navArgument("draftId") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("promptText") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val draftIdStr = backStackEntry.arguments?.getString("draftId")
                val draftId = draftIdStr?.toLongOrNull()
                val promptTextRaw = backStackEntry.arguments?.getString("promptText") ?: ""
                val promptText = if (promptTextRaw.isNotBlank()) URLDecoder.decode(promptTextRaw, "UTF-8") else null

                WriteScreen(
                    repository = repository,
                    aiService = aiService,
                    initialDraftId = draftId,
                    initialPromptText = promptText,
                    onNavigateToCardStudio = { text, poet ->
                        navController.navigate(Screen.CardStudio.createRoute(text, poet))
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            // Poets Catalogue Screen
            composable(Screen.Poets.route) {
                PoetsScreen(
                    onSelectPoet = { poetId ->
                        navController.navigate(Screen.PoetDetail.createRoute(poetId))
                    }
                )
            }

            // Poet Detail Screen
            composable(
                route = Screen.PoetDetail.route,
                arguments = listOf(navArgument("poetId") { type = NavType.StringType })
            ) { backStackEntry ->
                val poetId = backStackEntry.arguments?.getString("poetId") ?: "ghalib"
                PoetDetailScreen(
                    poetId = poetId,
                    repository = repository,
                    onBackClick = { navController.popBackStack() },
                    onSelectRelatedPoet = { relId ->
                        navController.navigate(Screen.PoetDetail.createRoute(relId))
                    },
                    onOpenCardStudio = { poem ->
                        navController.navigate(Screen.CardStudio.createRoute(poem.text, poem.poetName))
                    }
                )
            }

            // Resources Screen
            composable(Screen.Resources.route) {
                ResourcesScreen(
                    onSelectResource = { resourceId ->
                        navController.navigate(Screen.ResourceDetail.createRoute(resourceId))
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Resource Detail Screen
            composable(
                route = Screen.ResourceDetail.route,
                arguments = listOf(navArgument("resourceId") { type = NavType.StringType })
            ) { backStackEntry ->
                val resId = backStackEntry.arguments?.getString("resourceId") ?: "ghazal_anatomy"
                ResourceDetailScreen(
                    resourceId = resId,
                    onBackClick = { navController.popBackStack() },
                    onUseTemplateInEditor = { templateText ->
                        navController.navigate(Screen.Write.createRoute(null, templateText))
                    }
                )
            }

            // Mood Discovery Screen
            composable(
                route = Screen.Mood.route,
                arguments = listOf(navArgument("moodId") {
                    type = NavType.StringType
                    defaultValue = "love"
                })
            ) { backStackEntry ->
                val moodId = backStackEntry.arguments?.getString("moodId") ?: "love"
                MoodScreen(
                    initialMoodId = moodId,
                    repository = repository,
                    onBackClick = { navController.popBackStack() },
                    onNavigateToWriteWithPrompt = { prompt ->
                        navController.navigate(Screen.Write.createRoute(null, prompt))
                    },
                    onOpenCardStudio = { poem ->
                        navController.navigate(Screen.CardStudio.createRoute(poem.text, poem.poetName))
                    }
                )
            }

            // Vault Screen
            composable(Screen.Vault.route) {
                VaultScreen(
                    repository = repository,
                    onOpenDraft = { dId ->
                        navController.navigate(Screen.Write.createRoute(dId, null))
                    },
                    onCreateNewDraft = {
                        navController.navigate(Screen.Write.createRoute(null, null))
                    }
                )
            }

            // Poetry Card Studio Screen
            composable(
                route = Screen.CardStudio.route,
                arguments = listOf(
                    navArgument("poemText") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("poetName") {
                        type = NavType.StringType
                        defaultValue = "Prince Thakur"
                    }
                )
            ) { backStackEntry ->
                val rawText = backStackEntry.arguments?.getString("poemText") ?: ""
                val rawPoet = backStackEntry.arguments?.getString("poetName") ?: "Prince Thakur"
                val poemText = if (rawText.isNotBlank()) URLDecoder.decode(rawText, "UTF-8") else ""
                val poetName = if (rawPoet.isNotBlank()) URLDecoder.decode(rawPoet, "UTF-8") else "Prince Thakur"

                CardStudioScreen(
                    initialPoemText = poemText,
                    initialPoetName = poetName,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Settings / Profile Screen
            composable(Screen.Settings.route) {
                SettingsScreen(
                    repository = repository,
                    aiService = aiService
                )
            }
        }
    }
}
