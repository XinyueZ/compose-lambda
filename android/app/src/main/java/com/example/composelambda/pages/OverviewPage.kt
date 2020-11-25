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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.composelambda.R
import com.example.composelambda.appNav.Actions
import com.example.composelambda.domains.BreakingNews
import com.example.composelambda.pages.viewmodels.BreakingNewsViewModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Suppress("UNCHECKED_CAST")
@Composable
fun BuildOverviewPage(breakingNewsViewModel: BreakingNewsViewModel, actions: Actions) {
    val breakingNews: BreakingNews by breakingNewsViewModel.breakingNews.collectAsState(BreakingNews.default)
    val data =
        listOf(
            breakingNews,
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
                        BuildBreakingNewsContent(item as BreakingNews)
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
fun BuildBreakingNewsContent(breakingNews: BreakingNews) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.error)
    ) {
        CoilImage(
            data = breakingNews.image,
            modifier = Modifier
                .width(120.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.preferredWidth(16.dp))
        Text(
            text = breakingNews.title,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.subtitle1.copy(
                textAlign = TextAlign.Left,
                color = Color.White
            )
        )
    }
}
