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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.onDispose
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composelambda.Logger
import com.example.composelambda.R
import com.example.composelambda.appNav.Actions
import com.example.composelambda.async.OnResult.OnError
import com.example.composelambda.async.OnResult.OnSuccess
import com.example.composelambda.pages.viewmodels.BreakingNewsViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Suppress("UNCHECKED_CAST")
@Composable
fun BuildOverviewPage(vm: BreakingNewsViewModel, actions: Actions) {
    onCommit {
        Logger("BuildOverviewPage / onCommit")
        vm.fetchBreakingNews()
    }

    onDispose {
        Logger("BuildOverviewPage / onDispose")
    }
    val data =
        listOf(
            vm,
            Pair("The guy, occupying the Oval", R.drawable.trump_dump),
            Pair("Loser or winner ?", R.drawable.trump_dump),
        )

    Scaffold(
        topBar = {
            BuildAppBar(
                title = "News report"
            )
        },
        bodyContent = {
            LazyColumnForIndexed(
                data,
                contentPadding = PaddingValues(8.dp),
            ) { index, item ->
                BuildOverviewCard(actions) {
                    if (index == 0) {
                        BuildBreakingNewsContent(vm)
                    } else {
                        BuildOverviewCardContent(item as Pair<String, Int>)
                    }
                }
            }
        }
    )
}

@Composable
fun BuildOverviewCard(actions: Actions, content: @Composable () -> Unit) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp)
            .clickable(enabled = true) {
                actions.selectNews()
            },
        content = content,
    )
}

@Composable
fun BuildOverviewCardContent(item: Pair<String, Int>) {
    Row {
        Image(
            imageResource(item.second),
            Modifier
                .width(120.dp)
                .height(90.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.preferredWidth(16.dp))
        Text(
            text = item.first,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.subtitle1.copy(
                textAlign = TextAlign.Left,
            )
        )
    }
}

@Composable
fun BuildBreakingNewsContent(vm: BreakingNewsViewModel) = with(vm.breakingNewsState) {
    onCommit {
        Logger("BuildBreakingNewsContent / onCommit")
    }

    onDispose {
        Logger("BuildBreakingNewsContent / onDispose")
    }
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.error)
    ) {
        when (this@with) {
            is OnSuccess -> {
                CoilImage(
                    data = data.image,
                    modifier = Modifier
                        .width(120.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                )
                Spacer(modifier = Modifier.preferredWidth(16.dp))
                Text(
                    text = data.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.subtitle1.copy(
                        textAlign = TextAlign.Left,
                        color = Color.White
                    )
                )
            }
            is OnError -> {
                Spacer(modifier = Modifier.preferredWidth(16.dp))
                IconButton(onClick = { vm.fetchBreakingNews() }) {
                    Icon(
                        vectorResource(R.drawable.ic_refresh),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "${exception.message}",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.subtitle1.copy(
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator(
                        strokeWidth = 1.5.dp,
                        modifier = Modifier.size(
                            50.dp
                        ).align(Alignment.Center).padding(10.dp),
                    )
                }
            }
        }
    }
}
