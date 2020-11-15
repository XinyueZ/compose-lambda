package com.example.composelambda.appNav

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
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

@Composable
fun <T> WithThemeCrossfade(
    current: T,
    child: @Composable (() -> Unit),
) {
    Crossfade(current) {
        AppTheme {
            child()
        }
    }
}

@Composable
fun AppRouter() {
    NavHost(AppNavigator, startDestination = ROOT) {
        composable(ROOT) {
            WithThemeCrossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildOverviewPage()
            }
        }
        composable(OVERVIEW) {
            WithThemeCrossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildOverviewPage()
            }
        }
        composable(DETAIL) {
            WithThemeCrossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildDetailPage()
            }
        }
    }
}