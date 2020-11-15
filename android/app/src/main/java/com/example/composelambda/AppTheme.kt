package com.example.composelambda

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


@Composable
fun AppTheme(
    child: @Composable (() -> Unit),
) {
    val appThemeModel: AppThemeModel = viewModel()
    when (appThemeModel.isDark) {
        true -> AppDarkTheme {
            child()
        }
        else -> AppLightTheme {
            child()
        }
    }
}

@Composable
fun AppLightTheme(child: @Composable (() -> Unit)) {
    val colors = lightColors(
        primary = colorResource(R.color.indigo),
        primaryVariant = colorResource(R.color.indigo_deep),
        secondary = colorResource(R.color.teal_deep),
        secondaryVariant = colorResource(R.color.teal_deep),
        background = colorResource(R.color.white),
        surface = colorResource(R.color.white),
        error = colorResource(R.color.red),
        onPrimary = colorResource(R.color.white),
        onSecondary = colorResource(R.color.black),
        onBackground = colorResource(R.color.black),
        onSurface = colorResource(R.color.black),
        onError = colorResource(R.color.white),
    )
    MaterialTheme(colors = colors) {
        child()
    }
}

@Composable
fun AppDarkTheme(child: @Composable (() -> Unit)) {
    val colors = darkColors(
        primary = colorResource(R.color.indigo),
        primaryVariant = colorResource(R.color.indigo_deep),
        secondary = colorResource(R.color.teal_deep),
        background = colorResource(R.color.black),
        surface = colorResource(R.color.black),
        error = colorResource(R.color.red_deep),
        onPrimary = colorResource(R.color.black),
        onSecondary = colorResource(R.color.white),
        onBackground = colorResource(R.color.white),
        onSurface = colorResource(R.color.white),
        onError = colorResource(R.color.black),
    )
    MaterialTheme(colors = colors) {
        child()
    }
}

class AppThemeModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    var isDark: Boolean by mutableStateOf(  savedStateHandle["isDark"] ?: false )
        private set

    fun onThemeChanged(isDarkTheme: Boolean) {
        isDark = isDarkTheme
    }
}




