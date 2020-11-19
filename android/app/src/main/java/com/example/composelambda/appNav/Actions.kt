package com.example.composelambda.appNav

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

class Actions(navCtrl: NavHostController) {
    val upBack: () -> Unit = {
        navCtrl.popBackStack()
    }
    val selectNews: () -> Unit = {
        navCtrl.navigate(DETAIL)
    }
}