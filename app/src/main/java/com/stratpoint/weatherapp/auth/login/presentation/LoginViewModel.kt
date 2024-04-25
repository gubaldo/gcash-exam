package com.stratpoint.weatherapp.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.auth.constant.AuthConstants.PASSWORD_MIN_CHARACTERS
import com.stratpoint.weatherapp.extensions.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _isValidEmail = MutableStateFlow(true)
    val isValidEmail = _isValidEmail.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isValidPassword = MutableStateFlow(true)
    val isValidPassword = _isValidPassword.asStateFlow()

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

}