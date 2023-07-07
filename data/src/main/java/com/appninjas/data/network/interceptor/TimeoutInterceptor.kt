package com.appninjas.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class TimeoutInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch(e: SocketTimeoutException) {
            throw SocketTimeoutException("Connection timeout")
        }
    }

}