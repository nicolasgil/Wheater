package com.nicolas.weatherapp.domain.usecases


import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.domain.models.Location
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class SearchHistoryUseCaseTest {

    @Mock
    private lateinit var locationRepository: LocationRepository

    private lateinit var searchHistoryUseCase: SearchHistoryUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchHistoryUseCase = SearchHistoryUseCase(locationRepository)

        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.e(anyString(), anyString(), any()) }
                .thenReturn(0)
        }
    }

    @Test
    fun `saveSearch should call locationRepository saveSearch`() {
        val location = Location("New York", "USA")

        searchHistoryUseCase.saveSearch(location)

        verify(locationRepository).saveSearch(location)
    }

    @Test
    fun `getRecentSearches should return a list of recent searches from locationRepository`() {
        val locations = listOf(Location("New York", "USA"), Location("Los Angeles", "USA"))
        whenever(locationRepository.loadRecentSearches()).thenReturn(locations)

        val result = searchHistoryUseCase.getRecentSearches()

        assertEquals(locations, result)
        verify(locationRepository).loadRecentSearches()
    }
}