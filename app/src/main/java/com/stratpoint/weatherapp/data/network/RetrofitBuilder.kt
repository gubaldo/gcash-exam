package com.stratpoint.weatherapp.data.network

import android.content.Context
import com.stratpoint.weatherapp.util.NetworkUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private const val TIMEOUT = 20000//20 seconds

    fun createRetrofitInstance(context: Context, baseUrl: String, isDebug: Boolean): Retrofit =
        Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(baseUrl)

            val client = OkHttpClient.Builder().apply {

                connectTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                readTimeout(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)

                addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
                })

                addInterceptor(NoInternetInterceptor(context))

            }.build()

            client(client)
        }.build()

    class NoInternetInterceptor(private val context: Context) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            return if (!NetworkUtil.isNetworkAvailable(context)) {
                throw NoInternetException()
            } else {
                val builder = chain.request().newBuilder()
                chain.proceed(builder.build())
            }
        }

        inner class NoInternetException : IOException("No internet connection")

    }

}