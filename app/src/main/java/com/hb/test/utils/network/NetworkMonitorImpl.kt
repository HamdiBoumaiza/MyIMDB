package com.hb.test.utils.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * LiveNetworkMonitor is a class meant to check the application's network connectivity
 *
 * @param context a [Context] object. This is what is used to get the system's connectivity manager
 * */
class NetworkMonitorImpl @Inject constructor(private val context: Context) : NetworkMonitor {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected() = connectivityManager.activeNetwork != null
}
