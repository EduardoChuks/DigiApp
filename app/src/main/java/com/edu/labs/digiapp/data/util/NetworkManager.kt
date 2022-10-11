package com.edu.labs.digiapp.data.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkManager(private val context: Context) {

    fun checkForInternet(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected

    }

}