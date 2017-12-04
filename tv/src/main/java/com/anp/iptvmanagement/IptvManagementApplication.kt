package com.anp.iptvmanagement

import android.app.Application
import com.anp.commons.data.database.DataBaseManager

class IptvManagementApplication : Application() {


  override fun onCreate() {
    super.onCreate()

    DataBaseManager.initialize(this)
  }

}
