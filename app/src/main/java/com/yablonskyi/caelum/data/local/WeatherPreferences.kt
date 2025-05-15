package com.yablonskyi.caelum.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "weather_prefs")

@Singleton
class WeatherPreferences @Inject constructor(
    private val context: Context
) {
    companion object {
        private val LAST_FETCH_TIME = longPreferencesKey("last_fetch_time")
    }

    suspend fun saveLastFetchTime(time: Long) {
        context.dataStore.edit { prefs ->
            prefs[LAST_FETCH_TIME] = time
        }
    }

    suspend fun getLastFetchTime(): Long {
        val prefs = context.dataStore.data.first()
        return prefs[LAST_FETCH_TIME] ?: 0L
    }
}