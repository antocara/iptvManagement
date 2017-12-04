package com.anp.commons.data

import android.content.Context


object SharedPreferenceRepository {


  private val OFFSET_TIME_DOWNLOAD_EPG = 2 * 24 * 60 * 60 * 1000 //48 hours
  private val PREFERENCES_NAME = "iptc_management"
  private val LAST_TIME_SAVE_EPG_KEY = "com.anp.iptvmanagement.last_time_save_epg_key"


  fun saveLastTimeSaveEpg(context: Context) {
    val currentTime = System.currentTimeMillis()
    getSharedPreferences(context)
        .edit()
        .putLong(LAST_TIME_SAVE_EPG_KEY, currentTime)
        .apply()
  }

  fun isTimeToDownloadEpg(context: Context): Boolean {
    val lastTime = getLastTimeSaveEpg(context)
    val currentTime = System.currentTimeMillis()

    val offset = currentTime - lastTime
    return offset >= OFFSET_TIME_DOWNLOAD_EPG
  }

  private fun getLastTimeSaveEpg(context: Context) = getSharedPreferences(context).getLong(
      LAST_TIME_SAVE_EPG_KEY, 0L)


  fun deleteSettings(context: Context) {
    getSharedPreferences(context)?.edit()?.clear()?.apply()
  }


  private fun getSharedPreferences(context: Context) = context.getSharedPreferences(
      PREFERENCES_NAME, Context.MODE_PRIVATE)

}