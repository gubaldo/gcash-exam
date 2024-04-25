package com.stratpoint.weatherapp.data.network

import com.stratpoint.weatherapp.BuildConfig
import java.net.UnknownHostException

abstract class BaseRepository {

    inline fun <T> serviceCall(callFunction: () -> T): Resource<T> = try {
        Resource.success(callFunction())
    } catch (e: Exception) {

        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        }

        val exception = ServiceException(
            when (e) {
                is UnknownHostException -> {
                    ErrorResponse(
                        exceptionMessage = e.message
                    )
                }

                else -> {
                    ErrorResponse(
                        exceptionMessage = e.message
                    )
                }
            }
        )

        Resource.error(
            null,
            exception.error.exceptionMessage ?: ERROR_SOMETHING_WENT_WRONG,
            exception
        )

    }

    class ServiceException(val error: ErrorResponse) :
        RuntimeException(error.exceptionMessage)

    data class ErrorResponse(
        var errorCode: Int = ERROR_CODE_GENERIC,
        var exceptionMessage: String? = ERROR_SOMETHING_WENT_WRONG
    )

    companion object {
        const val ERROR_SOMETHING_WENT_WRONG = "Something went wrong. Please try again"
        const val ERROR_CODE_GENERIC = 417
    }

}