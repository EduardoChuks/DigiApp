package com.edu.labs.digiapp.presentation.common

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.edu.labs.digiapp.common.Constants
import com.edu.labs.digiapp.common.network.NetworkEvent
import com.edu.labs.digiapp.data.util.NetworkManager
import com.edu.labs.digiapp.util.extensions.gone
import com.edu.labs.digiapp.util.extensions.visible
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject

open class BaseNetworkActivity : AppCompatActivity() {

    protected val networkManager: NetworkManager by inject()

    var offlineView: View? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkEvent(event: NetworkEvent) {
        offlineView?.apply {
            when (event.event) {
                Constants.CONNECTION_AVAILABLE -> this.gone()
                Constants.CONNECTION_NOT_AVAILABLE -> this.visible()
                else -> this.gone()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        if (!networkManager.checkForInternet()) offlineView?.visible()
        else offlineView?.gone()
    }

}