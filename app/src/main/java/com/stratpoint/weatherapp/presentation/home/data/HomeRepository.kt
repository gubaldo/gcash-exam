package com.stratpoint.weatherapp.presentation.home.data

import com.google.firebase.auth.FirebaseAuth
import com.stratpoint.weatherapp.data.network.BaseRepository
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseRepository() {

    fun isUserLoggedIn() = firebaseAuth.currentUser != null

}