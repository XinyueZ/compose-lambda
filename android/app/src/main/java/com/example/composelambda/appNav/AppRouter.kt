package com.example.composelambda.appNav

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composelambda.pages.BuildDetailPage
import com.example.composelambda.pages.BuildOverviewPage


lateinit var AppNavigator: NavHostController

@Composable
fun NavigationContent(child: @Composable () -> Unit) {
    AppNavigator = rememberNavController()
    child()
}

@Composable
fun AppRouter() {
    NavHost(AppNavigator, startDestination = ROOT) {
        composable(ROOT) {
            Crossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildOverviewPage()
            }
        }
        composable(OVERVIEW) {
            Crossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildOverviewPage()
            }
        }
        composable(DETAIL) {
            Crossfade(AppNavigator.currentBackStackEntryAsState()) {
                BuildDetailPage()
            }
        }
    }

}