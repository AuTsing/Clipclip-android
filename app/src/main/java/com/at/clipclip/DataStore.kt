package com.at.clipclip

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "configs")
private val ADDR: Preferences.Key<String> = stringPreferencesKey("addr")

suspend fun Context.getAddr(): Result<String> = runCatching {
    dataStore.data.map { preferences ->
        preferences[ADDR] ?: "http://"
    }.first()
}

suspend fun Context.setAddr(addr: String): Result<Unit> = runCatching {
    dataStore.updateData {
        it.toMutablePreferences().also { preferences ->
            preferences[ADDR] = addr
        }
    }
}
