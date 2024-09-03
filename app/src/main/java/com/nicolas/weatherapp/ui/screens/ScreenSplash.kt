package com.nicolas.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nicolas.weatherapp.R
import com.nicolas.weatherapp.ui.navigation.AppScreen
import kotlinx.coroutines.delay


@Composable
fun ScreenSplash(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_light))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_splash),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier
                    .size(350.dp)
                    .padding(bottom = 16.dp)
            )


            Text(
                text = stringResource(R.string.app_name),
                fontSize = 42.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }


    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.popBackStack()
        navController.navigate(AppScreen.WeatherSearchScreen.route)
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenSplashPreview() {
    ScreenSplash(navController = rememberNavController())
}
