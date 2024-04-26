package com.stratpoint.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stratpoint.weatherapp.data.database.BaseDao
import com.stratpoint.weatherapp.domain.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao : BaseDao<User> {


    @Query("SELECT * FROM user LIMIT 1")
    abstract fun getLoggedInUser(): Flow<User?>

    @Query("DELETE FROM user")
    abstract suspend fun deleteUser()

    @Query("SELECT id FROM user LIMIT 1")
    abstract suspend fun getCurrentLoggedInUserId(): String

}