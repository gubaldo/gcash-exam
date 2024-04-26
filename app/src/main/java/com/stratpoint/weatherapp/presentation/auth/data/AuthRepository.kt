package com.stratpoint.weatherapp.presentation.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.stratpoint.weatherapp.data.database.AppDatabase
import com.stratpoint.weatherapp.data.network.BaseRepository
import com.stratpoint.weatherapp.domain.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appDatabase: AppDatabase
) : BaseRepository() {

    suspend fun register(name: String, email: String, password: String) = serviceCall {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        firebaseAuth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder().apply {
                this.displayName = name
            }.build()
        )?.await()


        firebaseAuth.currentUser?.let {
            it.updateProfile(
                UserProfileChangeRequest.Builder().apply {
                    this.displayName = name
                }.build()
            ).await()

            appDatabase.userDao().insert(
                User(
                    it.uid,
                    name,
                    it.email ?: ""
                )
            )

        }

    }

    suspend fun login(email: String, password: String) = serviceCall {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        appDatabase.userDao().insert(
            User(
                authResult.user!!.uid,
                authResult.user!!.displayName ?: "",
                authResult.user!!.email ?: ""
            )
        )
    }

}