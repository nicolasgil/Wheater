package com.nicolas.weatherapp.data.repositories

import android.util.Log
import com.nicolas.weatherapp.data.datasources.local.SearchHistoryDataSource
import com.nicolas.weatherapp.data.datasources.remote.RemoteDataSource
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.utils.WeatherForecastObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class LocationRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var searchHistoryDataSource: SearchHistoryDataSource

    private lateinit var locationRepository: LocationRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        locationRepository = LocationRepositoryImpl(remoteDataSource, searchHistoryDataSource)

        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.e(anyString(), anyString(), any()) }
                .thenReturn(0)
        }
    }

    @Test
    fun `getLocations should return a list of locations when remoteDataSource succeeds`() {
        runBlocking {
            val query = "New York"
            val locations = listOf(Location("New York", "USA"))
            `when`(remoteDataSource.getAllLocations(query)).thenReturn(locations)

            val result = locationRepository.getLocations(query)

            assertEquals(locations, result)
            verify(remoteDataSource).getAllLocations(query)
        }
    }

    @Test
    fun `getLocations should return an empty list when remoteDataSource throws an exception`() {
        runBlocking {
            val query = "New York"
            `when`(remoteDataSource.getAllLocations(query)).thenThrow(RuntimeException("Network error"))

            val result = locationRepository.getLocations(query)

            assertTrue(result.isEmpty())
            verify(remoteDataSource).getAllLocations(query)
        }
    }

    @Test
    fun `getDetailsLocation should return a WeatherForecast when remoteDataSource succeeds`() {
        runBlocking {
            val cityName = "New York"
            val forecast = WeatherForecastObject.SAMPLE
            `when`(remoteDataSource.getDetailsLocation(cityName)).thenReturn(forecast)

            val result = locationRepository.getDetailsLocation(cityName)

            assertEquals(forecast, result)
            verify(remoteDataSource).getDetailsLocation(cityName)
        }
    }

    @Test
    fun `getDetailsLocation should return EMPTY WeatherForecast when remoteDataSource throws an exception`() {
        runBlocking {
            val cityName = "New York"
            `when`(remoteDataSource.getDetailsLocation(cityName)).thenThrow(RuntimeException("Network error"))

            val result = locationRepository.getDetailsLocation(cityName)

            assertEquals(WeatherForecastObject.EMPTY, result)
            verify(remoteDataSource).getDetailsLocation(cityName)
        }
    }

    @Test
    fun `saveSearch should call saveSearch on searchHistoryDataSource`() {
        val location = Location("New York", "USA")

        locationRepository.saveSearch(location)

        verify(searchHistoryDataSource).saveSearch(location)
    }

    @Test
    fun `saveSearch should log an error when searchHistoryDataSource throws an exception`() {
        val location = Location("New York", "USA")
        doThrow(RuntimeException("Error")).`when`(searchHistoryDataSource)
            .saveSearch(location)

        locationRepository.saveSearch(location)

        verify(searchHistoryDataSource).saveSearch(location)

    }

    @Test
    fun `loadRecentSearches should return a list of recent searches when searchHistoryDataSource succeeds`() {
        val recentSearches = listOf(Location("New York", "USA"))
        `when`(searchHistoryDataSource.loadRecentSearches()).thenReturn(recentSearches)

        val result = locationRepository.loadRecentSearches()

        assertEquals(recentSearches, result)
        verify(searchHistoryDataSource).loadRecentSearches()
    }

    @Test
    fun `loadRecentSearches should return an empty list when searchHistoryDataSource throws an exception`() {
        `when`(searchHistoryDataSource.loadRecentSearches()).thenThrow(RuntimeException("Error"))

        val result = locationRepository.loadRecentSearches()

        assertTrue(result.isEmpty())
        verify(searchHistoryDataSource).loadRecentSearches()
    }
}
