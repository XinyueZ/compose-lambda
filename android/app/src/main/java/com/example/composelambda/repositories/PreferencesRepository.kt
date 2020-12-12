/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.composelambda.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PreferencesRepository {
    val followSystemTheme: Flow<Boolean>
    suspend fun setFollowSystemTheme(value: Boolean)
}

class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
) : PreferencesRepository {

    private val keyFollowSystemTheme: Preferences.Key<Boolean> =
        preferencesKey("key_follow_system_key")

    private val dataStore: DataStore<Preferences> =
        context.createDataStore(
            name = "app_preferences",
        )
    override val followSystemTheme: Flow<Boolean> = dataStore.data
        .map { prefs ->
            prefs[keyFollowSystemTheme] ?: true
        }

    override suspend fun setFollowSystemTheme(value: Boolean) {
        dataStore.edit { prefs ->
            prefs[keyFollowSystemTheme] = value
        }
    }
}
