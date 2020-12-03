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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.example.composelambda.AppThemeModel
import com.example.composelambda.AppThemeModelFactory

@Composable
fun BuildAppBar(
    title: String,
    backNavigateTo: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                title,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                maxLines = 1,
                modifier = Modifier
                    .background(color = Color.Transparent),
                style = MaterialTheme.typography.h6
            )
        },
        navigationIcon = if (backNavigateTo != null) {
            {
                IconButton(onClick = backNavigateTo) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        } else null,
        elevation = 0.dp,
        actions = {
            BuildSwitchTheme()
        }
    )
}

@Composable
fun BuildSwitchTheme() {
    val appThemeModel: AppThemeModel = viewModel(factory = AppThemeModelFactory)
    Row() {
        Switch(
            checked = appThemeModel.isDark,
            onCheckedChange = {
                appThemeModel.onThemeChanged(it)
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(if (appThemeModel.isDark) "Light" else "Dark")

        Spacer(modifier = Modifier.width(16.dp))
    }
}
