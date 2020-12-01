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
import com.example.composelambda.pages.viewmodels.NewsViewModel

@Composable
fun NavigationContent(newsViewModel: NewsViewModel) {
    val navCtrl: NavHostController = rememberNavController()
    onCommit {
        Logger("Navigation-Content onCommit")
    }
    Crossfade(navCtrl.currentBackStackEntryAsState()) {
        NavHost(navCtrl, startDestination = OVERVIEW) {
            composable(OVERVIEW) {
                Logger("OVERVIEW: ${it.destination.id}")
                BuildOverviewPage(newsViewModel, Actions(navCtrl))
            }

            composable("${NewsType.BreakingNews}") {
                Logger("DETAIL BreakingNews: ${it.destination.id}")
                BuildDetailPage(newsViewModel, NewsType.BreakingNews, Actions(navCtrl))
            }

            composable("${NewsType.PremiumNews}") {
                Logger("DETAIL PremiumNews: ${it.destination.id}")
                BuildDetailPage(newsViewModel, NewsType.PremiumNews, Actions(navCtrl))
            }
        }
    }
    onDispose {
        Logger("Navigation-Content onDispose")
    }
}
