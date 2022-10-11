package com.edu.labs.digiapp.common.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.edu.labs.digiapp.common.Constants.CONNECTION_AVAILABLE
import com.edu.labs.digiapp.common.Constants.CONNECTION_NOT_AVAILABLE
import org.greenrobot.eventbus.EventBus


class NetworkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        isNetworkAvailable(context)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (connectivity == null) {
            EventBus.getDefault().post(NetworkEvent(CONNECTION_NOT_AVAILABLE))
            return false
        }

        val activeNetwork = connectivity.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isAvailable && activeNetwork.isConnected &&
            activeNetwork.detailedState == NetworkInfo.DetailedState.CONNECTED) {
            EventBus.getDefault().post(NetworkEvent(CONNECTION_AVAILABLE))
            true
        } else {
            EventBus.getDefault().post(NetworkEvent(CONNECTION_NOT_AVAILABLE))
            false
        }
    }

}