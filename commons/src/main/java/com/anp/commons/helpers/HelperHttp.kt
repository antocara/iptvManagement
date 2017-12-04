package com.anp.commons.helpers

import android.util.Log
import com.anp.commons.Constants
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection


object HelperHttp {

  fun closeInputStream(inputStream: InputStream?) {
    try {
      inputStream?.close()
    } catch (e: IOException) {
      Log.d(Constants.APP_NAME, "Error close inputStream")
    }
  }

  fun disconnectConnection(conn: HttpURLConnection?) {
    conn?.disconnect()
  }

}