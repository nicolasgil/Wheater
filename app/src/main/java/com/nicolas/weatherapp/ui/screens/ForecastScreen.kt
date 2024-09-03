package com.nicolas.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.nicolas.weatherapp.R
import com.nicolas.weatherapp.domain.models.WeatherForecast
import com.nicolas.weatherapp.ui.viewmodels.ForecastViewModel
import com.nicolas.weatherapp.utils.WeatherForecastObject
import com.nicolas.weatherapp.utils.dummyNavController

@Composable
fun ForecastScreen(
    cityName: String,
    viewModel: ForecastViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(cityName) {
        viewModel.loadForecast(cityName)
        isLoading = false
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(id = R.color.white)
    ) {
        val forecast by viewModel.detailsLocation.observeAsState()

        when {
            isLoading -> {
                Loading()
            }

            forecast != null -> {
                ForecastContent(forecast = forecast!!, navController = navController)
            }

            else -> {
                Loading()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastContent(forecast: WeatherForecast, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.text_title_top_bar_details,
                            forecast.location.name
                        ),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.text_description_content_back),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.sky_blue))
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                ) {

                    Text(
                        text = stringResource(
                            R.string.text_title_location_details_screen,
                            forecast.location.name,
                            forecast.location.country
                        ),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.deep_blue)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    WeatherDayCard(
                        day = "Today",
                        temp = "${String.format("%.1f", forecast.current.temp_c)}°C",
                        iconUrl = forecast.current.condition.icon,
                        description = forecast.current.condition.text
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    forecast.forecast.forecastday.take(2).forEach { dayForecast ->
                        WeatherDayCard(
                            day = dayForecast.date,
                            temp = "${String.format("%.1f", dayForecast.day.avgtemp_c)}°C",
                            iconUrl = dayForecast.day.condition.icon,
                            description = dayForecast.day.condition.text
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}

@Composable
fun WeatherDayCard(day: String, temp: String, iconUrl: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = colorResource(id = R.color.light_blue),
                shape = RoundedCornerShape(12.dp)
            )
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = day,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.deep_blue)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.text_title_aver_tempe),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.deep_blue)
            )
            Text(
                text = temp,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = rememberImagePainter(data = "https:$iconUrl"),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer { alpha = 0.8f }
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewForecast() {
    ForecastContent(WeatherForecastObject.SAMPLE, dummyNavController())
}
