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

package com.example.composelambda.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composelambda.Logger
import com.example.composelambda.appNav.Actions
import com.example.composelambda.pages.viewmodels.PreferencesViewModel

@Composable
fun BuildPreferencesPage(
    vm: PreferencesViewModel,
    enableSwitchTheme: Boolean,
    actions: Actions
) {
    onCommit {
        Logger("BuildPreferencesPage / onCommit")
    }

    onDispose {
        Logger("BuildPreferencesPage / onDispose")
    }

    Scaffold(
        topBar = {
            BuildAppBar(
                title = "Preferences",
                enablePreferences = false,
                enableSwitchTheme = enableSwitchTheme,
            ) {
                actions.upBack()
            }
        },
        bodyContent = {
            BuildContent(vm)
        }
    )
}

@Composable
fun BuildContent(vm: PreferencesViewModel) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        val isFollowSystemTheme by vm.followSystemTheme.collectAsState(initial = true)

        ListItem(
            text = {
                Text(
                    text = "Follow system theme",
                    style = MaterialTheme.typography.subtitle1.copy(
                        textAlign = TextAlign.Left,
                    )
                )
            },
            trailing = {
                Switch(
                    checked = isFollowSystemTheme,
                    onCheckedChange = {
                        vm.setFollowSystemTheme(!isFollowSystemTheme)
                    }
                )
            }
        )
    }
}
