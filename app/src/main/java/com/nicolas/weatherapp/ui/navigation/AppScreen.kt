package com.nicolas.weatherapp.ui.navigation

sealed class AppScreen(val route: String) {
    data object SplashScreen : AppScreen("splash_screen")

    data object WeatherSearchScreen : AppScreen("weather_search_screen")

}
