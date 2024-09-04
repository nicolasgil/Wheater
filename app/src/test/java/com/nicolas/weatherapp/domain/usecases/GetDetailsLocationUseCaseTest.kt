package com.nicolas.weatherapp.domain.usecases

import android.util.Log
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.utils.WeatherForecastObject
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
class GetDetailsLocationUseCaseTest {

    @Mock
    private lateinit var locationRepository: LocationRepository

    private lateinit var getDetailsLocationUseCase: GetDetailsLocationUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getDetailsLocationUseCase = GetDetailsLocationUseCase(locationRepository)


        mockStatic(Log::class.java).use { logMock ->
            logMock.`when`<Int> { Log.e(anyString(), anyString(), any()) }
                .thenReturn(0)
        }
    }

    @Test
    fun `invoke should return WeatherForecast when locationRepository succeeds`(): Unit =
        runBlocking {
            val cityName = "New York"
            val forecast = WeatherForecastObject.SAMPLE
            whenever(locationRepository.getDetailsLocation(cityName)).thenReturn(forecast)

            val result = getDetailsLocationUseCase(cityName)

            assertEquals(forecast, result)
            verify(locationRepository).getDetailsLocation(cityName)
        }

    @Test
    fun `invoke should return EMPTY WeatherForecast when locationRepository throws an exception`(): Unit =
        runBlocking {
            val cityName = "New York"
            whenever(locationRepository.getDetailsLocation(cityName)).thenThrow(RuntimeException("Network error"))

            val result = getDetailsLocationUseCase(cityName)

            assertEquals(WeatherForecastObject.EMPTY, result)
            verify(locationRepository).getDetailsLocation(cityName)
        }
}