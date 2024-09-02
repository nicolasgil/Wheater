package com.nicolas.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.nicolas.weatherapp.R
import kotlinx.coroutines.delay


@Composable
fun ScreenSplash(navController: NavHostController) {
    Splash()
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.popBackStack()

    }
}

@Composable
fun Splash() {
    Image(
        painter = painterResource(id = R.drawable.ic_splash),
        contentDescription = stringResource(R.string.text_description_image_splash),
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    Splash()
}