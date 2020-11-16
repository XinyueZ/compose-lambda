package com.example.composelambda.appNav

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composelambda.AppTheme
import com.example.composelambda.pages.BuildDetailPage
import com.example.composelambda.pages.BuildOverviewPage


lateinit var AppNavigator: NavHostController

@Composable
fun NavigationContent(child: @Composable () -> Unit) {
    AppNavigator = rememberNavController()
    child()
}


fun NavGraphBuilder.withThemeComposable(
    route: String,
    child: @Composable ((NavBackStackEntry) -> Unit)
) {
    composable(route) {
        AppTheme {
            child(it)
        }
    }
}

@Composable
fun AppRouter() {
    NavHost(AppNavigator, startDestination = ROOT) {
        withThemeComposable(ROOT) {
            Crossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildOverviewPage()
            }
        }
        withThemeComposable(OVERVIEW) {
            Crossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildOverviewPage()
            }
        }
        withThemeComposable(DETAIL) {
            Crossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildDetailPage()
            }
        }
    }
}