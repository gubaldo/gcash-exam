package com.stratpoint.weatherapp.presentation.home.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stratpoint.weatherapp.MainCoroutineRule
import com.stratpoint.weatherapp.domain.Weather
import com.stratpoint.weatherapp.presentation.home.data.HomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HistoryScreenViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val homeRepository: HomeRepository = Mockito.mock()

    private lateinit var historyScreenViewModel: HistoryScreenViewModel

    private val weatherData = listOf(
        Weather(
            id = 1,
            userId = "1",
            weatherStatus = "Cloudy",
            temperature = "32.5",
            date = 1714128753,
            timezone = 28800,
            city = "Pasong Tamo",
            sunrise = 1714080926,
            sunset = 1714080926
        )
    )
    private val weatherFlow = flowOf(weatherData)

    @Before
    fun beforeTest() = runTest {
        whenever(homeRepository.getWeatherHistoryFlow()).thenReturn(weatherFlow)
        historyScreenViewModel = HistoryScreenViewModel(homeRepository)
    }

    @Test
    fun `test initialization`() = runTest {

        // Then
        assertEquals(weatherData, historyScreenViewModel.weatherList.value)

    }

}