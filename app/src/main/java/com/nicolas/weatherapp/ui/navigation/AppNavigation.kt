package com.nicolas.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolas.weatherapp.ui.screens.ScreenSplash

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreen.SplashScreen.route) {
        composable(AppScreen.SplashScreen.route) {
            ScreenSplash(navController)
        }

    }
}
