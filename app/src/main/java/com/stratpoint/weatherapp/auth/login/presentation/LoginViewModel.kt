package com.stratpoint.weatherapp.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.auth.constant.AuthConstants.PASSWORD_MIN_CHARACTERS
import com.stratpoint.weatherapp.auth.data.AuthRepository
import com.stratpoint.weatherapp.data.network.Status
import com.stratpoint.weatherapp.extensions.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _isValidEmail = MutableStateFlow(true)
    val isValidEmail = _isValidEmail.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isValidPassword = MutableStateFlow(true)
    val isValidPassword = _isValidPassword.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful = _isLoginSuccessful.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    val isLoginButtonEnabled = combine(
        email,
        isValidEmail,
        password,
        isValidPassword
    ) { email, isValidEmail, password, isValidPassword ->
        isValidEmail && email.isNotEmpty() && isValidPassword && password.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun validateEmail(email: String) {
        _email.value = email
        _isValidEmail.value = email.isEmpty() || email.isValidEmail()
    }

    fun validatePassword(password: String) {
        _password.value = password
        _isValidPassword.value = password.isEmpty() || password.length >= PASSWORD_MIN_CHARACTERS
    }

    fun login() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.login(_email.value, _password.value)
            when (result.status) {
                Status.SUCCESS -> {
                    _isLoginSuccessful.value = true
                }

                Status.ERROR -> {
                    _errorMessage.value = result.message
                }
            }

            _isLoading.value = false
        }

    }

    fun dismissErrorDialog() {
        _errorMessage.value = null
    }

}