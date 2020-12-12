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

package com.example.composelambda.appNav

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composelambda.Logger
import com.example.composelambda.pages.BuildDetailPage
import com.example.composelambda.pages.BuildOverviewPage
import com.example.composelambda.pages.BuildPreferencesPage
import com.example.composelambda.pages.viewmodels.NewsViewModel
import com.example.composelambda.pages.viewmodels.PreferencesViewModel

@Composable
fun NavigationContent(
    newsViewModel: NewsViewModel,
    preferencesViewModel: PreferencesViewModel,
) {
    onCommit {
        Logger("Navigation-Content onCommit")
    }
    val navCtrl: NavHostController = rememberNavController()
    val isFollowSystemTheme by preferencesViewModel.followSystemTheme.collectAsState(initial = true)

    Crossfade(navCtrl.currentBackStackEntryAsState()) {
        NavHost(navCtrl, startDestination = OVERVIEW) {
            composable(OVERVIEW) {
                Logger("OVERVIEW: ${it.destination.id}")
                BuildOverviewPage(
                    vm = newsViewModel,
                    enableSwitchTheme = !isFollowSystemTheme,
                    actions = Actions(navCtrl)
                )
            }

            composable(PREFERENCES) {
                Logger("PREFERENCES: ${it.destination.id}")
                BuildPreferencesPage(
                    vm = preferencesViewModel,
                    enableSwitchTheme = !isFollowSystemTheme,
                    actions = Actions(navCtrl)
                )
            }

            composable("$DETAIL/${NewsType.BreakingNews}") {
                Logger("DETAIL BreakingNews: ${it.destination.id}")
                BuildDetailPage(
                    vm = newsViewModel,
                    enableSwitchTheme = !isFollowSystemTheme,
                    newsType = NewsType.BreakingNews,
                    actions = Actions(navCtrl)
                )
            }

            composable("$DETAIL/${NewsType.PremiumNews}") {
                Logger("DETAIL PremiumNews: ${it.destination.id}")
                BuildDetailPage(
                    vm = newsViewModel,
                    enableSwitchTheme = !isFollowSystemTheme,
                    newsType = NewsType.PremiumNews,
                    actions = Actions(navCtrl)
                )
            }
        }
    }
    onDispose {
        Logger("Navigation-Content onDispose")
    }
}
