package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.data.ai.GeminiAiService
import com.example.data.local.PreferencesManager
import com.example.data.local.SukhanDatabase
import com.example.data.local.SukhanRepository
import com.example.ui.navigation.SukhanNavHost
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = SukhanDatabase.getDatabase(applicationContext)
        val preferences = PreferencesManager(applicationContext)
        val repository = SukhanRepository(database.sukhanDao(), preferences)

        val aiService = GeminiAiService {
            runBlocking {
                try {
                    preferences.customApiKey.first()
                } catch (e: Exception) {
                    ""
                }
            }
        }

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                SukhanNavHost(
                    navController = navController,
                    repository = repository,
                    aiService = aiService
                )
            }
        }
    }
}
