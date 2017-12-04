package com.anp.commons.data.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.anp.commons.Constants
import com.anp.commons.R.string
import com.anp.commons.data.ChannelsRepository
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.managers.LocalNotificationManager


class SaveChannelsService(serviceName: String = "SaveChannelsService") : IntentService(
    serviceName) {


  companion object {
    val INTENT_SAVE_CHANNEL_LIST = "com.anp.commons.save.channels.list"
    val INTENT_SOURCE_LIST = "com.anp.commons.save.source.list"

    var CHANNELS_LIST = listOf<Channel>()
  }

  override fun onHandleIntent(intent: Intent?) {

    val sourceList = intent?.getParcelableExtra<MyChannelsList>(INTENT_SOURCE_LIST)

    if (sourceList != null) {
      Log.d(Constants.APP_NAME, "Init Save Channels Service")
      ChannelsRepository().saveMyChannelsList(
          CHANNELS_LIST, sourceList)
      CHANNELS_LIST = listOf<Channel>()
      Log.d(Constants.APP_NAME, "End save channels")
      fireNotification()
    }
  }

  private fun fireNotification() {
    LocalNotificationManager().fireLocalNotification(applicationContext,
        string.notification_save_channels_title, string.notification_ended_save)
  }

  override fun onDestroy() {
    Log.d(Constants.APP_NAME, "onDestroy SaveChannelsService")
    super.onDestroy()
  }
}