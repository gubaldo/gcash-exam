package com.stratpoint.weatherapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stratpoint.weatherapp.data.network.Status
import com.stratpoint.weatherapp.presentation.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    val isUserLoggedIn by lazy { repository.isUserLoggedIn() }

    private val _isLoggingOut = MutableStateFlow(false)
    val isLoggingOut = _isLoggingOut.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess = _logoutSuccess.asStateFlow()

    fun logout() {

        _isLoggingOut.value = true

        viewModelScope.launch {

            val result = repository.logout()

            when (result.status) {
                Status.SUCCESS -> {
                    _logoutSuccess.value = true
                }

                Status.ERROR -> {
                    result.message?.let {
                        _errorMessage.emit(it)
                    }
                }
            }

            _isLoggingOut.value = false
        }
    }

    fun dismissErrorDialog() {
        _errorMessage.value = null
    }

}