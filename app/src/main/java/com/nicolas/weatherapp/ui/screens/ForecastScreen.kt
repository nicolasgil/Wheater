package com.nicolas.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
                            forecast.location.name,
                            forecast.location.country,

                            ),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.deep_purple))
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.deep_purple),
                                colorResource(id = R.color.white)
                            )
                        )
                    )

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    WeatherDayCard(
                        day = stringResource(R.string.text_title_today),
                        temp = "${String.format("%.1f", forecast.current.temp_c)}°C",
                        iconUrl = forecast.current.condition.icon,
                        description = forecast.current.condition.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.5f),
                        tempSize = 60, descSize = 34
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        forecast.forecast.forecastday.take(2).forEachIndexed { index, dayForecast ->
                            WeatherDayCard(
                                day = if (index == 0) stringResource(R.string.text_title_tomorrow) else stringResource(
                                    R.string.text_title_day_after_tomorrow
                                ),
                                temp = "${String.format("%.1f", dayForecast.day.avgtemp_c)}°C",
                                iconUrl = dayForecast.day.condition.icon,
                                description = dayForecast.day.condition.text,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )

                            if (index == 0) {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun WeatherDayCard(
    day: String,
    temp: String,
    iconUrl: String,
    description: String,
    modifier: Modifier = Modifier,
    tempSize: Int = 24,
    descSize: Int = 14
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.light_blue),
                        colorResource(id = R.color.white)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .border(2.dp, colorResource(id = R.color.deep_blue), RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {


            Text(
                text = day,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))


            Image(
                painter = rememberImagePainter(data = "https:$iconUrl"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .graphicsLayer { alpha = 0.5f }
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = temp,
                fontSize = tempSize.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.deep_blue),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = description,
                fontSize = descSize.sp,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@Composable
@Preview(showSystemUi = true)
fun PreviewForecast() {
    ForecastContent(WeatherForecastObject.SAMPLE, dummyNavController())
}
