package com.example.composelambda.appNav

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.composelambda.pages.BuildDetailPage
import com.example.composelambda.pages.BuildOverviewPage


@Composable
fun NavigationContent() {
    val navCtrl = rememberNavController()
    Crossfade(navCtrl.currentBackStackEntryAsState()) {
        NavHost(navCtrl, startDestination = ROOT) {
            composable(ROOT) { BuildOverviewPage(Actions(navCtrl)) }
            composable(OVERVIEW) { BuildOverviewPage(Actions(navCtrl)) }
            composable(DETAIL) { BuildDetailPage(Actions(navCtrl)) }
        }
    }
}
