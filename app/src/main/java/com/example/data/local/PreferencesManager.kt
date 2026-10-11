package com.example.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "sukhan_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        val KEY_DEFAULT_SCRIPT = stringPreferencesKey("default_script")
        val KEY_DEFAULT_LANGUAGE = stringPreferencesKey("default_language")
        val KEY_RTL_ENABLED = booleanPreferencesKey("rtl_enabled")
        val KEY_CUSTOM_API_KEY = stringPreferencesKey("custom_api_key")
        val KEY_PEN_NAME = stringPreferencesKey("pen_name")
        val KEY_WATERMARK_ENABLED = booleanPreferencesKey("watermark_enabled")
    }

    val defaultScript: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_DEFAULT_SCRIPT] ?: "Hindi"
    }

    val defaultLanguage: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_DEFAULT_LANGUAGE] ?: "Hinglish"
    }

    val isRtlEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_RTL_ENABLED] ?: true
    }

    val customApiKey: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_CUSTOM_API_KEY] ?: ""
    }

    val penName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_PEN_NAME] ?: "Prince Thakur"
    }

    val isWatermarkEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_WATERMARK_ENABLED] ?: true
    }

    suspend fun setDefaultScript(script: String) {
        context.dataStore.edit { it[KEY_DEFAULT_SCRIPT] = script }
    }

    suspend fun setDefaultLanguage(language: String) {
        context.dataStore.edit { it[KEY_DEFAULT_LANGUAGE] = language }
    }

    suspend fun setRtlEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_RTL_ENABLED] = enabled }
    }

    suspend fun setCustomApiKey(key: String) {
        context.dataStore.edit { it[KEY_CUSTOM_API_KEY] = key }
    }

    suspend fun setPenName(name: String) {
        context.dataStore.edit { it[KEY_PEN_NAME] = name }
    }

    suspend fun setWatermarkEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_WATERMARK_ENABLED] = enabled }
    }
}
