package com.example.composelambda.pages

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.composelambda.R


@Composable
fun BuildAppBar(
    title: String,
    backNavigateTo: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                title,
                modifier = Modifier
                    .background(color = Color.Transparent),
                style = MaterialTheme.typography.h6.copy(color = Color.White)
            )
        },
        navigationIcon = if (backNavigateTo != null) {
            {
                Icon(
                    vectorResource(R.drawable.ic_back),
                    modifier = Modifier.fillMaxSize().clickable(true) {
                        backNavigateTo()
                    }
                )
            }
        } else null,
        elevation = 0.dp,
        modifier = Modifier
            .background(color = colorResource(R.color.purple_200))
    )
}