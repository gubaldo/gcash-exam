package com.stratpoint.weatherapp.presentation.auth.login.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.stratpoint.weatherapp.MainCoroutineRule
import com.stratpoint.weatherapp.data.network.Resource
import com.stratpoint.weatherapp.data.network.Status
import com.stratpoint.weatherapp.presentation.auth.data.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val authRepository: AuthRepository = mock()

    private lateinit var loginViewModel: LoginViewModel

    private val email = "test@example.com"
    private val password = "12345678"
    private val incorrectPassword = "123456789"

    @Before
    fun beforeTest() {
        loginViewModel = LoginViewModel(authRepository)
    }

    @Test
    fun `test validEmail`() = runTest {

        // When
        loginViewModel.validateEmail(email)
        advanceUntilIdle()

        // Then
        val isValidEmail = loginViewModel.isValidEmail.value
        assertTrue(isValidEmail)

    }

    @Test
    fun `test invalidEmail`() = runTest {

        // Given
        val invalidEmail = "test"

        // When
        loginViewModel.validateEmail(invalidEmail)
        advanceUntilIdle()

        // Then
        val isValidEmail = loginViewModel.isValidEmail.value
        assertFalse(isValidEmail)

    }

    @Test
    fun `test validPassword`() = runTest {

        // When
        loginViewModel.validatePassword(password)
        advanceUntilIdle()

        // Then
        val isValidPassword = loginViewModel.isValidPassword.value
        assertTrue(isValidPassword)

    }

    @Test
    fun `test invalidPassword`() = runTest {

        // Given
        val invalidPassword = "12345"

        // When
        loginViewModel.validatePassword(invalidPassword)
        advanceUntilIdle()

        // Then
        val isValidPassword = loginViewModel.isValidPassword.value
        assertFalse(isValidPassword)

    }

    @Test
    fun `test loginSuccess`() = runTest {

        // Given
        val mockResponse: Resource<Long> = mock()
        whenever(mockResponse.status).thenReturn(Status.SUCCESS)
        whenever(authRepository.login(email, password)).thenReturn(mockResponse)

        loginViewModel.validateEmail(email)
        loginViewModel.validatePassword(password)

        // When
        loginViewModel.login()
        advanceUntilIdle()

        // Then
        assertTrue(loginViewModel.isLoginSuccessful.value)

    }

    @Test
    fun `test loginFailed`() = runTest {

        // Given
        val errorMessage = "The supplied auth credential is incorrect, malformed or has expired."
        val mockResponse: Resource<Long> = mock()
        whenever(mockResponse.status).thenReturn(Status.ERROR)
        whenever(authRepository.login(email, incorrectPassword)).thenReturn(mockResponse)
        whenever(mockResponse.message).thenReturn(errorMessage)

        loginViewModel.validateEmail(email)
        loginViewModel.validatePassword(incorrectPassword)

        // When
        loginViewModel.login()
        advanceUntilIdle()

        // Then
        assertEquals(errorMessage, loginViewModel.errorMessage.value)

    }

}