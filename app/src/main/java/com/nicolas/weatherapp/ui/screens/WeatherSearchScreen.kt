package com.nicolas.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nicolas.weatherapp.R
import com.nicolas.weatherapp.ui.navigation.AppScreen
import com.nicolas.weatherapp.ui.viewmodels.WeatherSearchViewModel

@Composable
fun WeatherSearchScreen(
    viewModel: WeatherSearchViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    val recentSearches by viewModel.recentSearches.observeAsState(emptyList())

    val filteredCities = if (searchQuery.isEmpty()) {
        recentSearches.map { "${it.name}, ${it.country}" }
    } else {
        searchResults.map { "${it.name}, ${it.country}" }
    }

    LaunchedEffect(searchQuery) {
        viewModel.onSearchQueryChanged(searchQuery)
        isLoading = false
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.teal_light))
                .padding(16.dp)
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    viewModel.onSearchQueryChanged(query)
                },
                placeholder = { Text(stringResource(id = R.string.text_placeholder_bar_searchbar)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.teal_medium), RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.teal_dark),
                    cursorColor = Color.White,
                    textColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(50.dp)
                    )
                }
            } else {
                LazyColumn {
                    items(filteredCities) { city ->
                        WeatherSearchItem(cityName = city, onClick = {

                            val location =
                                searchResults.find { "${it.name}, ${it.country}" == city }
                                    ?: recentSearches.find { "${it.name}, ${it.country}" == city }

                            location?.let {
                                viewModel.onSearchItemClicked(it)
                                navController.navigate(
                                    AppScreen.ForecastScreen.route.replace(
                                        "{cityName}",
                                        it.name
                                    )
                                )
                            }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherSearchItem(cityName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(id = R.color.teal_medium)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = cityName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherSearchScreenPreview() {
    WeatherSearchScreen(navController = rememberNavController())
}
