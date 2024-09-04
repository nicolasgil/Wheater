package com.nicolas.weatherapp.domain.usecases

import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.domain.models.Location
import kotlinx.coroutines.runBlocking
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
class GetLocationsUseCaseTest {

    @Mock
    private lateinit var locationRepository: LocationRepository

    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getLocationsUseCase = GetLocationsUseCase(locationRepository)

        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.e(anyString(), anyString(), any()) }
                .thenReturn(0)
        }
    }

    @Test
    fun `invoke should return a list of locations when locationRepository succeeds`(): Unit =
        runBlocking {
            val query = "New York"
            val locations = listOf(Location("New York", "USA"))
            whenever(locationRepository.getLocations(query)).thenReturn(locations)

            val result = getLocationsUseCase(query)

            assertEquals(locations, result)
            verify(locationRepository).getLocations(query)
        }

    @Test
    fun `invoke should return an empty list when locationRepository throws an exception`(): Unit =
        runBlocking {
            val query = "New York"
            whenever(locationRepository.getLocations(query)).thenThrow(RuntimeException("Network error"))

            val result = getLocationsUseCase(query)

            assertTrue(result.isEmpty())
            verify(locationRepository).getLocations(query)
        }
}