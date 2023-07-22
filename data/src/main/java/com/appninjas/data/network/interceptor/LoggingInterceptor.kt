package com.appninjas.data.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {

    companion object {
        const val LOG_TAG = "Logging Interceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(LOG_TAG, "Going to proceed request to ${request.url}\nWith headers ${request.headers}")

        val response = chain.proceed(request)
        Log.d(LOG_TAG, "Got response ${response.body}")
        return response
    }

}