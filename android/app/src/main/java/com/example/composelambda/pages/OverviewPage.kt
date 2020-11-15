package com.example.composelambda.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.example.composelambda.R
import com.example.composelambda.appNav.AppNavigator

@Composable
fun BuildOverviewPage() {
    val data =
        listOf(
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
            LazyColumnFor(
                data,
                contentPadding = PaddingValues(8.dp),
            ) { item ->
                BuildOverviewCard {
                    BuildOverviewCardContent(item)
                }
            }
        }
    )
}

@Composable
fun BuildOverviewCard(content: @Composable () -> Unit) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp)
            .clickable(enabled = true) {
                AppNavigator.navigate("detail")
            },
        content = content,
    )
}


@Composable
fun BuildOverviewCardContent(item: Pair<String, Int>) {
    Row(modifier = Modifier.padding(8.dp)) {
        Image(
            imageResource(item.second),
            Modifier
                .width(120.dp)
                .height(90.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
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