package com.example.composelambda.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.composelambda.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


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
        actions = {
            BuildSwitchTheme()
        },
        modifier = Modifier
            .background(color = colorResource(R.color.purple_200))
    )
}

@Composable
fun BuildSwitchTheme() {
    var isDarkTheme by remember { mutableStateOf(false) }
    Row() {
        Text(if (isDarkTheme) "Dark" else "Light")
        Spacer(modifier = Modifier.preferredWidth(10.dp))
        Switch(checked = isDarkTheme, onCheckedChange = {
            isDarkTheme = it
        })
    }
}