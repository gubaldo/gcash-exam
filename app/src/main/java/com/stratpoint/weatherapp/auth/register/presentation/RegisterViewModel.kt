package com.stratpoint.weatherapp.auth.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.auth.constant.AuthConstants
import com.stratpoint.weatherapp.auth.data.AuthRepository
import com.stratpoint.weatherapp.data.network.Status
import com.stratpoint.weatherapp.extensions.isValidEmail
import com.stratpoint.weatherapp.util.combineRegisterForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _isValidName = MutableStateFlow(true)
    val isValidName = _isValidName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _isValidEmail = MutableStateFlow(true)
    val isValidEmail = _isValidEmail.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isValidPassword = MutableStateFlow(true)
    val isValidPassword = _isValidPassword.asStateFlow()

    val isRegisterButtonEnabled = combineRegisterForm(
        name,
        isValidName,
        email,
        isValidEmail,
        password,
        isValidPassword
    ) { name, isValidName, email, isValidEmail, password, isValidPassword ->
        name.isNotEmpty() && isValidName && isValidEmail && email.isNotEmpty() && isValidPassword && password.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isRegisterSuccessful = MutableStateFlow(false)
    val isRegisterSuccessful = _isRegisterSuccessful.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun validateName(name: String) {
        _name.value = name
        _isValidName.value = name.isNotEmpty() || name.isNotBlank()
    }

    fun validateEmail(email: String) {
        _email.value = email
        _isValidEmail.value = email.isEmpty() || email.isValidEmail()
    }

    fun validatePassword(password: String) {
        _password.value = password
        _isValidPassword.value =
            password.isEmpty() || password.length >= AuthConstants.PASSWORD_MIN_CHARACTERS
    }

    fun register() {
        _isLoading.value = true

        viewModelScope.launch {
            val result = repository.register(_name.value, _email.value, _password.value)

            when (result.status) {
                Status.SUCCESS -> {
                    _isRegisterSuccessful.value = true
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