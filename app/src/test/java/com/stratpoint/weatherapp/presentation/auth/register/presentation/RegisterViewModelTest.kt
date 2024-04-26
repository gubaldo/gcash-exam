package com.stratpoint.weatherapp.presentation.auth.register.presentation

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
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    private val authRepository: AuthRepository = Mockito.mock()

    private lateinit var registerViewModel: RegisterViewModel

    private val name = "Juan"
    private val email = "test@example.com"
    private val password = "12345678"

    @Before
    fun beforeTest() {
        registerViewModel = RegisterViewModel(authRepository)
    }

    @Test
    fun `test validName`() = runTest {

        // When
        registerViewModel.validateName(name)
        advanceUntilIdle()

        // Then
        val isValidName = registerViewModel.isValidName.value
        assertTrue(isValidName)

    }

    @Test
    fun `test invalidName`() = runTest {

        // When
        registerViewModel.validateName("")
        advanceUntilIdle()

        // Then
        val isValidName = registerViewModel.isValidName.value
        assertFalse(isValidName)

    }

    @Test
    fun `test validEmail`() = runTest {

        // When
        registerViewModel.validateEmail(email)
        advanceUntilIdle()

        // Then
        val isValidEmail = registerViewModel.isValidEmail.value
        assertTrue(isValidEmail)

    }

    @Test
    fun `test invalidEmail`() = runTest {

        // Given
        val invalidEmail = "test"

        // When
        registerViewModel.validateEmail(invalidEmail)
        advanceUntilIdle()

        // Then
        val isValidEmail = registerViewModel.isValidEmail.value
        assertFalse(isValidEmail)

    }

    @Test
    fun `test validPassword`() = runTest {

        // When
        registerViewModel.validatePassword(password)
        advanceUntilIdle()

        // Then
        val isValidPassword = registerViewModel.isValidPassword.value
        assertTrue(isValidPassword)

    }

    @Test
    fun `test invalidPassword`() = runTest {

        // Given
        val invalidPassword = "12345"

        // When
        registerViewModel.validatePassword(invalidPassword)
        advanceUntilIdle()

        // Then
        val isValidPassword = registerViewModel.isValidPassword.value
        assertFalse(isValidPassword)

    }

    @Test
    fun `test registerSuccess`() = runTest {

        // Given
        val mockResponse: Resource<Long> = Mockito.mock()
        whenever(mockResponse.status).thenReturn(Status.SUCCESS)
        whenever(authRepository.register(name, email, password)).thenReturn(mockResponse)

        registerViewModel.validateName(name)
        registerViewModel.validateEmail(email)
        registerViewModel.validatePassword(password)

        // When
        registerViewModel.register()
        advanceUntilIdle()

        // Then
        assertTrue(registerViewModel.isRegisterSuccessful.value)

    }

    @Test
    fun `test registerFailed`() = runTest {

        // Given
        val errorMessage = "The email address is already in use by another account"
        val mockResponse: Resource<Long> = Mockito.mock()
        whenever(mockResponse.status).thenReturn(Status.ERROR)
        whenever(authRepository.register(name, email, password)).thenReturn(mockResponse)
        whenever(mockResponse.message).thenReturn(errorMessage)

        registerViewModel.validateName(name)
        registerViewModel.validateEmail(email)
        registerViewModel.validatePassword(password)

        // When
        registerViewModel.register()
        advanceUntilIdle()

        // Then
        assertEquals(errorMessage, registerViewModel.errorMessage.value)

    }

}