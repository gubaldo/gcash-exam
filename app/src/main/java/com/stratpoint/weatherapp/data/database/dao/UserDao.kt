package com.stratpoint.weatherapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.stratpoint.weatherapp.data.database.BaseDao
import com.stratpoint.weatherapp.domain.User

@Dao
abstract class UserDao : BaseDao<User> {

    @Query("DELETE FROM user")
    abstract suspend fun deleteUser()

}