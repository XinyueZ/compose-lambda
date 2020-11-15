package com.example.composelambda.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.example.composelambda.AppThemeModel
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
        actions = {
            BuildSwitchTheme(
            )
        }
    )
}

@Composable
fun BuildSwitchTheme() {
    val appThemeModel: AppThemeModel = viewModel()
    Row() {
        Text(if (appThemeModel.isDark) "Dark" else "Light")
        Spacer(modifier = Modifier.preferredWidth(10.dp))
        Switch(checked = appThemeModel.isDark, onCheckedChange = {
            appThemeModel.onThemeChanged(it)
        })
    }
}