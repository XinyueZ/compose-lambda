package com.example.composelambda.appNav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composelambda.pages.BuildDetailPage
import com.example.composelambda.pages.BuildOverviewPage


lateinit var AppNavigator: NavHostController

@Composable
fun NavigationContent(child: @Composable () -> Unit) {
    if (!::AppNavigator.isInitialized) {
        AppNavigator = rememberNavController()
    }
    child()
}

@Composable
fun AppRouter() {
    NavHost(AppNavigator, startDestination = ROOT) {
        composable(ROOT) {
            BuildOverviewPage()
        }
        composable(OVERVIEW) {
            BuildOverviewPage()
        }
        composable(DETAIL) {
            BuildDetailPage()
        }
    }
}