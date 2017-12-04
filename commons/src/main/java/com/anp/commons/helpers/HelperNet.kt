package com.anp.commons.helpers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager


object HelperNet {

  @SuppressLint("MissingPermission")
  fun isConnectedWifi(context: Context): Boolean {

    val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    var isWifiConnected = false
    val networks = connectivityManager.allNetworks
    networks?.let { arrayOfNetworks ->
      arrayOfNetworks.map { network ->
        val infoNetwork = connectivityManager.getNetworkInfo(network)
        if (
        (infoNetwork.type == ConnectivityManager.TYPE_WIFI
            || infoNetwork.type == ConnectivityManager.TYPE_ETHERNET)
            && infoNetwork.isAvailable
            && infoNetwork.isConnected) {
          isWifiConnected = true
        }
      }
    }

    return isWifiConnected
  }
}