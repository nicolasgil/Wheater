package com.nicolas.weatherapp.ui.viewmodels


import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nicolas.weatherapp.domain.models.Location
import com.nicolas.weatherapp.domain.usecases.GetLocationsUseCase
import com.nicolas.weatherapp.domain.usecases.SearchHistoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class WeatherSearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Mock
    private lateinit var manageSearchHistoryUseCase: SearchHistoryUseCase

    @Mock
    private lateinit var searchResultsObserver: Observer<List<Location>>

    @Mock
    private lateinit var recentSearchesObserver: Observer<List<Location>>

    private lateinit var weatherSearchViewModel: WeatherSearchViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherSearchViewModel =
            WeatherSearchViewModel(getLocationsUseCase, manageSearchHistoryUseCase)

        weatherSearchViewModel.searchResults.observeForever(searchResultsObserver)
        weatherSearchViewModel.recentSearches.observeForever(recentSearchesObserver)
    }

    @Test
    fun `onSearchQueryChanged with empty query should load recent searches`() =
        testDispatcher.runBlockingTest {
            val recentLocations =
                listOf(Location("New York", "USA"), Location("Los Angeles", "USA"))
            whenever(manageSearchHistoryUseCase.getRecentSearches()).thenReturn(recentLocations)

            weatherSearchViewModel.onSearchQueryChanged("")

            verify(recentSearchesObserver).onChanged(recentLocations)
            verify(searchResultsObserver).onChanged(emptyList())
        }


    @Test
    fun `onSearchItemClicked should save search and reload recent searches`() =
        testDispatcher.runBlockingTest {
            val location = Location("New York", "USA")
            val recentLocations = listOf(location, Location("Los Angeles", "USA"))

            whenever(manageSearchHistoryUseCase.getRecentSearches()).thenReturn(recentLocations)

            weatherSearchViewModel.onSearchItemClicked(location)

            verify(manageSearchHistoryUseCase).saveSearch(location)
            verify(recentSearchesObserver).onChanged(recentLocations)
        }
}