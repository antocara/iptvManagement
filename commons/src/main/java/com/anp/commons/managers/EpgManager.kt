package com.anp.commons.managers

import android.content.Context
import android.content.Intent
import com.anp.commons.data.service.SaveEpgService


object EpgManager {

  fun initServiceDownloadEpg(context: Context, urlEpg: String) {
    Intent(context, SaveEpgService::class.java)
        .apply { putExtra(SaveEpgService.INTENT_URL_EPG, urlEpg) }
        .run { context.startService(this) }
  }

}