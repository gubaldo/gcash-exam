package com.stratpoint.weatherapp.presentation.home

import androidx.lifecycle.ViewModel
import com.stratpoint.weatherapp.presentation.home.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    val isUserLoggedIn by lazy { repository.isUserLoggedIn() }

}