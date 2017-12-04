package com.anp.commons.managers

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.anp.commons.Constants
import com.anp.commons.R.mipmap

class LocalNotificationManager {

  companion object {
    val NOTIFICATION_ID = 10001
  }


  fun fireLocalNotification(context: Context, resourceTextTitle: Int, resourceTextMessage: Int) {
    val textMessage = context.getString(resourceTextMessage)

    val CHANNEL_ID = "channels_saved"
    val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
      setSmallIcon(mipmap.ic_launcher_round)
      setContentTitle(context.getString(resourceTextTitle))
      setStyle(NotificationCompat.BigTextStyle().bigText(textMessage))
      setContentText(textMessage)
      setAutoCancel(true)
    }

    createPendingIntent(context)?.let { pendingIntent ->
      mBuilder.setContentIntent(pendingIntent)
    }
    val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build())
  }

  private fun createPendingIntent(context: Context): PendingIntent? {
    val activityToFire = getActivityToFire()
    activityToFire?.let { clazz ->
      val resultIntent = Intent(context, clazz)

      val stackBuilder = TaskStackBuilder.create(context)
      stackBuilder.addParentStack(activityToFire)
      stackBuilder.addNextIntent(resultIntent)
      return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    return null
  }

  private fun getActivityToFire(): Class<*>? {
    try {
      return getActivityNameToFire()
    } catch (e: ClassNotFoundException) {
      Log.d(Constants.APP_NAME, "Exception get Class name in LocalNotification")
    }
    return null
  }

  private fun getActivityNameToFire(): Class<*>? {
    try {
      return Class.forName(Constants.packageNamePrincipalActivity)
    } catch (ex: ClassNotFoundException) {
      try {
        return Class.forName(Constants.packageNamePrincipalActivityTv)
      } catch (ex: ClassNotFoundException) {
        Log.d(Constants.APP_NAME, "error getActivityNameToFire")
      }
    }

    return null
  }
}