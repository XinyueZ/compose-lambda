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

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.composelambda.Logger
import com.example.composelambda.appNav.Actions
import com.example.composelambda.appNav.NewsType
import com.example.composelambda.async.OnResult
import com.example.composelambda.pages.viewmodels.NewsViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun BuildDetailPage(
    vm: NewsViewModel,
    enableSwitchTheme: Boolean,
    newsType: NewsType,
    actions: Actions,
) {

    onCommit {
        Logger("BuildDetailPage / onCommit")
        fetchNewsDetail(vm, newsType)
    }

    onDispose {
        Logger("BuildDetailPage / onDispose")
    }

    var fontSizeDelta by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            BuildAppBar(
                parseNewsDetail(vm = vm, newsType = newsType).first(),
                enablePreferences = true,
                enableSwitchTheme = enableSwitchTheme,
                gotoPreferences = actions.gotoPreferences
            ) {
                actions.upBack()
            }
        },
        bodyContent = {
            ScrollableColumn(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                if (parseNewsDetail(vm = vm, newsType = newsType).last().isNotEmpty()) {
                    CoilImage(
                        data = parseNewsDetail(vm = vm, newsType = newsType).last(),
                        Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .clip(
                                shape = RoundedCornerShape(10.dp),
                            ),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            strokeWidth = 1.5.dp,
                            modifier = Modifier.size(
                                50.dp
                            ).align(Alignment.Center).padding(10.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.align(alignment = Alignment.End)) {
                    TextButton(
                        onClick = {
                            if (fontSizeDelta > 0) fontSizeDelta -= 1
                        }
                    ) {
                        Text(
                            "smaller",
                            style = MaterialTheme.typography.h6.copy(
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                    TextButton(
                        onClick = {
                            fontSizeDelta += 1
                        }
                    ) {
                        Text(
                            "larger",
                            style = MaterialTheme.typography.h6.copy(
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }
                Text(
                    text = parseNewsDetail(vm = vm, newsType = newsType).first(),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = (MaterialTheme.typography.h5.fontSize.value + fontSizeDelta).sp,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = parseNewsDetail(vm = vm, newsType = newsType)[1],
                    style = MaterialTheme.typography.body1
                        .copy(
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 0.12.em,
                            fontSize = (MaterialTheme.typography.body1.fontSize.value + fontSizeDelta).sp,
                        )

                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
fun parseNewsDetail(vm: NewsViewModel, newsType: NewsType): Array<String> {
    when (newsType) {
        NewsType.BreakingNews -> {
            with(vm.breakingNewsDetail) {
                if (this@with is OnResult.OnSuccess) {
                    return arrayOf(data.title, data.description, data.image)
                }
            }
        }
        else -> {
            with(vm.premiumNewsDetail) {
                if (this@with is OnResult.OnSuccess) {
                    return arrayOf(data.title, data.description, data.image)
                }
            }
        }
    }
    return arrayOf("", "", "")
}

fun fetchNewsDetail(vm: NewsViewModel, newsType: NewsType) {
    when (newsType) {
        NewsType.BreakingNews -> {
            vm.fetchBreakingNewsDetail()
        }
        else -> {
            vm.fetchPremiumNewsNewsDetail()
        }
    }
}
