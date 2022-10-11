package com.edu.labs.digiapp

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.edu.labs.digiapp.common.network.NetworkReceiver
import com.edu.labs.digiapp.di.appModule
import com.edu.labs.digiapp.di.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DigiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DigiApplication)
            androidLogger()
            modules(
                dbModule,
                appModule
            )
        }
        initNetworkReceiver()
    }

    private fun initNetworkReceiver() {
        val networkReceiver = NetworkReceiver()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

}