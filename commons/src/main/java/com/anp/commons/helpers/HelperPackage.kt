package com.anp.commons.helpers

import android.content.Context
import android.content.pm.PackageManager


object HelperPackage {


  fun isPackageInstalled(context: Context, packageName: String): Boolean {
    val packageManager = context.packageManager
    val intent = packageManager.getLaunchIntentForPackage(packageName) ?: return false
    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return list.size > 0
  }

  fun isAmazonTvDevice(context: Context): Boolean =
      context.packageManager.hasSystemFeature("amazon.hardware.fire_tv")


}