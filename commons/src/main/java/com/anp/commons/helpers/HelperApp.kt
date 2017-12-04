package com.anp.commons.helpers

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.anp.commons.Constants
import com.anp.commons.data.entities.AppInfo


object HelperApp {


  fun getVersionInfo(context: Context): AppInfo {
    var versionName = ""
    var versionCode = -1
    try {
      val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
      versionName = packageInfo.versionName
      versionCode = packageInfo.versionCode
    } catch (e: PackageManager.NameNotFoundException) {
      Log.d(Constants.APP_NAME, "Error retrieving app info")
    }

    return AppInfo(versionName, versionCode)
  }


}