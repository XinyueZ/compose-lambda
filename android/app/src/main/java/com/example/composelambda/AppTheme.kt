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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Composable
fun AppTheme(
    child: @Composable (() -> Unit),
) {
    val appThemeModel: AppThemeModel = viewModel(factory = AppThemeModelFactory)
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

object AppThemeModelFactory : ViewModelProvider.Factory {
    private lateinit var appThemeModel: AppThemeModel

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (this::appThemeModel.isInitialized) return appThemeModel as T
        appThemeModel = AppThemeModel()
        return appThemeModel as T
    }
}

class AppThemeModel : ViewModel() {

    var isDark: Boolean by mutableStateOf(false)
        private set

    fun onThemeChanged(isDarkTheme: Boolean) {
        isDark = isDarkTheme
    }
}
