package com.stratpoint.weatherapp.data.network

import com.stratpoint.weatherapp.data.network.Status.ERROR
import com.stratpoint.weatherapp.data.network.Status.SUCCESS

data class Resource<out T>(
    val status: Status,
    val data: T?,
    var message: String?,
    val exception: BaseRepository.ServiceException?
) {
    companion object {
        fun <T> success(data: T, message: String? = null): Resource<T> =
            Resource(status = SUCCESS, data = data, message = message, exception = null)

        fun <T> error(
            data: T?,
            message: String,
            exception: BaseRepository.ServiceException
        ): Resource<T> =
            Resource(status = ERROR, data = data, message = message, exception = exception)
    }
}