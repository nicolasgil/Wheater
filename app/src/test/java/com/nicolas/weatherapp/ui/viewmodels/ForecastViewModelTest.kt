package com.nicolas.weatherapp.ui.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nicolas.weatherapp.domain.models.WeatherForecast
import com.nicolas.weatherapp.domain.usecases.GetDetailsLocationUseCase
import com.nicolas.weatherapp.utils.WeatherForecastObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ForecastViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getDetailsLocationUseCase: GetDetailsLocationUseCase

    @Mock
    private lateinit var observer: Observer<WeatherForecast>

    private lateinit var forecastViewModel: ForecastViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastViewModel = ForecastViewModel(getDetailsLocationUseCase)

        forecastViewModel.detailsLocation.observeForever(observer)
    }

    @Test
    fun `loadForecast should update LiveData when useCase returns data`() =
        testDispatcher.runBlockingTest {
            val cityName = "New York"
            val weatherForecast = WeatherForecastObject.SAMPLE
            whenever(getDetailsLocationUseCase(cityName)).thenReturn(weatherForecast)

            forecastViewModel.loadForecast(cityName)

            verify(observer).onChanged(weatherForecast)
        }

    @Test
    fun `loadForecast should handle empty result from useCase`() = testDispatcher.runBlockingTest {
        val cityName = "New York"
        val emptyWeatherForecast = WeatherForecastObject.EMPTY
        whenever(getDetailsLocationUseCase(cityName)).thenReturn(emptyWeatherForecast)

        forecastViewModel.loadForecast(cityName)

        verify(observer).onChanged(emptyWeatherForecast)
    }
}