package com.stratpoint.weatherapp.presentation.home.current_weather

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stratpoint.weatherapp.MainCoroutineRule
import com.stratpoint.weatherapp.data.network.Resource
import com.stratpoint.weatherapp.data.network.Status
import com.stratpoint.weatherapp.domain.User
import com.stratpoint.weatherapp.domain.Weather
import com.stratpoint.weatherapp.presentation.home.data.HomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CurrentWeatherViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val locationTracker: LocationTracker = mock()
    private val homeRepository: HomeRepository = mock()

    private lateinit var viewModel: CurrentWeatherViewModel

    private val fakeUser = User(
        id = "someId",
        name = "Test User",
        email = "test@example.com"
    )

    private val fakeWeather = Weather(userId = fakeUser.id)


    @Before
    fun setup() {

        whenever(homeRepository.getUserFlow()).thenReturn(flowOf(fakeUser))

        viewModel = CurrentWeatherViewModel(
            locationTracker,
            homeRepository
        )
    }

    @Test
    fun ensureCurrentLoggedInUserIsRetrieved() = runTest {

        //Given from the setup

        //When the viewModel is initialized (from setup)

        //Then
        assertEquals(fakeUser, viewModel.user.value)

    }

    @Test
    fun getLocation() = runTest {

        //Given
        val lat = 14.6907
        val long = 121.0455
        val fakeLocation: Location = mock()
        whenever(fakeLocation.latitude).thenReturn(lat)
        whenever(fakeLocation.longitude).thenReturn(long)
        whenever(locationTracker.getCurrentLocation()).thenReturn(fakeLocation)

        //When
        viewModel.getLocation()
        advanceUntilIdle()

        //Then
        assertEquals(fakeLocation, viewModel.currentLocation.value)
        assertEquals(lat, viewModel.currentLocation.value!!.latitude, 14.6907)
        assertEquals(long, viewModel.currentLocation.value!!.longitude, 121.0455)

    }

    @Test
    fun locationReset() {

        viewModel.resetLocation()
        assertNull(viewModel.currentLocation.value)

    }

    @Test
    fun getCurrentWeatherSuccess() = runTest {

        //Given
        val lat = 14.6907
        val long = 121.0455
        val apiKey = "someAPIkey"
        val mockResource: Resource<Weather> = mock()

        whenever(mockResource.status).thenReturn(Status.SUCCESS)
        whenever(mockResource.data).thenReturn(fakeWeather)
        whenever(
            homeRepository.getWeather(
                lat,
                long,
                apiKey,
                fakeUser.id
            )
        ).thenReturn(mockResource)

        //When
        viewModel.getWeather(
            lat,
            long,
            apiKey,
            fakeUser.id
        )
        advanceUntilIdle()

        //Then
        assertEquals(fakeWeather, viewModel.weather.value)

    }


    @Test
    fun getCurrentWeatherError() = runTest {

        //Given
        val lat = 14.6907
        val long = 121.0455
        val apiKey = "someAPIkey"
        val errorMessage = "Invalid API Key"
        val mockResource: Resource<Weather> = mock()

        whenever(mockResource.status).thenReturn(Status.ERROR)
        whenever(mockResource.message).thenReturn(errorMessage)
        whenever(
            homeRepository.getWeather(
                lat,
                long,
                apiKey,
                fakeUser.id
            )
        ).thenReturn(mockResource)

        //When
        viewModel.getWeather(
            lat,
            long,
            apiKey,
            fakeUser.id
        )
        advanceUntilIdle()

        //Then
        assertEquals(errorMessage, viewModel.errorMessage.value)

    }

}