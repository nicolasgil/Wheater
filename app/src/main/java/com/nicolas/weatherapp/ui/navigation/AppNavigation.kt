package com.nicolas.weatherapp.ui.navigation

import WeatherSearchScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolas.weatherapp.ui.screens.ScreenSplash
import com.nicolas.weatherapp.ui.viewmodels.WeatherSearchViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreen.SplashScreen.route) {
        composable(AppScreen.SplashScreen.route) {
            ScreenSplash(navController)
        }

        composable(AppScreen.WeatherSearchScreen.route) {
            val viewModel = hiltViewModel<WeatherSearchViewModel>()
            WeatherSearchScreen(viewModel, navController)
        }

    }
}
