package com.stratpoint.weatherapp.presentation.auth.login.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.testIn
import com.stratpoint.weatherapp.MainCoroutineRule
import com.stratpoint.weatherapp.presentation.auth.data.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val authRepository: AuthRepository = mock()

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun beforeTest() {
        mainCoroutineRule.dispatcher.run {
            Dispatchers.setMain(this)
        }
        loginViewModel = LoginViewModel(authRepository)
    }

    @After
    fun afterTest() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test validateEmail`() = runTest {

        // Given
        val email = "test@example.com"

        // When
        loginViewModel.validateEmail(email)
        advanceUntilIdle()

        // Then
        val isValidEmail = loginViewModel.isValidEmail.value
        assertTrue(isValidEmail)

    }
    
}