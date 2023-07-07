package com.appninjas.recruiterheaven.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkChecker {
    fun checkIsNetworkAvailable(context: Context): Boolean {
        val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false
        return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            true
        } else capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}