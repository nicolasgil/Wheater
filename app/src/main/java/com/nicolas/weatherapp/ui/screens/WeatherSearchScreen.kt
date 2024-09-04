package com.nicolas.weatherapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    val recentSearches by viewModel.recentSearches.observeAsState(emptyList())

    val filteredCities = if (searchQuery.isEmpty()) {
        recentSearches.map { "${it.name}, ${it.country}" }
    } else {
        searchResults.map { "${it.name}, ${it.country}" }
    }

    LaunchedEffect(searchQuery) {
        try {
            isLoading = true
            viewModel.onSearchQueryChanged(searchQuery)
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Unknown Error"
        } finally {
            isLoading = false
        }
    }

    WeatherSearchContent(
        searchQuery = searchQuery,
        onSearchQueryChange = { query ->
            searchQuery = query
            viewModel.onSearchQueryChanged(query)
        },
        isLoading = isLoading,
        errorMessage = errorMessage,
        filteredCities = filteredCities,
        onCityClick = { city ->
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
        }
    )
}

@Composable
fun WeatherSearchContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    filteredCities: List<String>,
    onCityClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorResource(id = R.color.light_blue),
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
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        stringResource(id = R.string.text_placeholder_bar_searchbar),
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorResource(id = R.color.white),
                    cursorColor = Color.Black,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    placeholderColor = Color.Gray,
                    leadingIconColor = Color.Black
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.text_description_content_search),
                        tint = colorResource(id = R.color.black)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(
                    R.string.text_title_recent_searches,
                ),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Loading()
                }

                LazyColumn {
                    items(filteredCities) { city ->
                        WeatherSearchItem(cityName = city, onClick = { onCityClick(city) })
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
        backgroundColor = colorResource(id = R.color.deep_purple)
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Color.DarkGray,
                    spotColor = Color.Red
                )
                .background(
                    color = colorResource(id = R.color.light_purple),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = cityName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorResource(id = R.color.light_blue),
                        colorResource(id = R.color.white)
                    )
                )
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = colorResource(id = R.color.deep_blue),
            strokeWidth = 4.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherSearchContentPreview() {
    WeatherSearchContent(
        searchQuery = "",
        onSearchQueryChange = {},
        isLoading = false,
        errorMessage = null,
        filteredCities = listOf("New York, USA", "Los Angeles, USA", "Chicago, USA"),
        onCityClick = {}
    )
}
