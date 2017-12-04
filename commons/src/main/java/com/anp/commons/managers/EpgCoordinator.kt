package com.anp.commons.managers

import android.content.Context
import com.anp.commons.data.PreferencesStorage
import com.anp.commons.data.SharedPreferenceRepository


class EpgCoordinator {

  fun initGetEpgData(context: Context){
    val epgUrl = PreferencesStorage.getEpgUrl(context)
    val isTimeToDownload = SharedPreferenceRepository.isTimeToDownloadEpg(context)
    if (epgUrl.isNotEmpty() && isTimeToDownload){
      EpgManager.initServiceDownloadEpg(context, epgUrl)
    }
  }
}