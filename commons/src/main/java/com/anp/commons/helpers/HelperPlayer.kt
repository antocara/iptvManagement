package com.anp.commons.helpers

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

import android.util.Log
import com.anp.commons.Constants
import com.anp.commons.data.PreferencesStorage
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.Player


object HelperPlayer {


  private val PATH_AMAZON_STORE = "amzn://apps/android?p="
  private val PATH_GOOGLE_STORE = "https://play.google.com/store/apps/details?id="

  fun isPlayerInstalled(activity: Activity): Boolean {
    val playerSelected = PreferencesStorage.getPlayerSettings(activity)
    return HelperPackage.isPackageInstalled(activity, playerSelected.packageName)
  }

  fun openPlayerAndPlay(channel: Channel, context: Context): Intent {

    val playerSelected = PreferencesStorage.getPlayerSettings(context)
    return getIntentOpenPlayer(playerSelected, channel)
  }

  private fun getIntentOpenPlayer(player: Player, channel: Channel): Intent {
    val uri = Uri.parse(channel.urlStream)
    return Intent(Intent.ACTION_VIEW).apply {
      `package` = player.packageName
      setDataAndTypeAndNormalize(uri, "video/*")
      putExtra("title", channel.titleChannel)
      putExtra("from_start", false)
      putExtra("position", 0L)
    }
  }

  fun openGooglePlayToDownloadPlayer(context: Context) {
    val playerSelected = PreferencesStorage.getPlayerSettings(context)

    try {
      context.startActivity(Intent(Intent.ACTION_VIEW,
          Uri.parse(getUrlStore(context) + playerSelected.packageName)))
    } catch (ex: ActivityNotFoundException) {
      Log.d(Constants.APP_NAME, "Google play not installed")
    }
  }


  private fun getUrlStore(context: Context) =
      if (HelperPackage.isAmazonTvDevice(context))
        HelperPlayer.PATH_AMAZON_STORE
      else HelperPlayer.PATH_GOOGLE_STORE

}