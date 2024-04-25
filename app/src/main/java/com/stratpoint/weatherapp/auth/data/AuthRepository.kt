package com.stratpoint.weatherapp.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.stratpoint.weatherapp.data.network.BaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : BaseRepository() {

    suspend fun register(email: String, password: String) = serviceCall {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        firebaseAuth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder().apply {
                this.displayName = displayName
            }.build()
        )?.await()

        firebaseAuth.currentUser?.let {
            it.updateProfile(
                UserProfileChangeRequest.Builder().apply {
                    this.displayName = displayName
                }.build()
            ).await()
        }
    }

    suspend fun login(email: String, password: String) = serviceCall {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

}