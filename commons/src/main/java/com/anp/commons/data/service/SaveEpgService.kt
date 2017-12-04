package com.anp.commons.data.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.anp.commons.Constants
import com.anp.commons.R
import com.anp.commons.R.string
import com.anp.commons.data.SharedPreferenceRepository
import com.anp.commons.data.entities.DataStream
import com.anp.commons.data.net.HttpConnection
import com.anp.commons.helpers.HelperFiles
import com.anp.commons.helpers.HelperNet
import com.anp.commons.managers.LocalNotificationManager
import com.anp.commons.parserlib.EpgParseSave
import java.io.IOException
import java.io.InputStream
import java.util.zip.GZIPInputStream


class SaveEpgService(serviceName: String = "SaveEpgService") : IntentService(
    serviceName) {


  companion object {
    val EPG_FILE_NAME = "iptvmanagement_epg"
    val EPG_EXTENSION_GZ = ".gz"
    val EPG_EXTENSION_XML = ".xml"
    val INTENT_URL_EPG = "com.anp.commons.save.epg"

  }

  override fun onHandleIntent(intent: Intent?) {
    if (HelperNet.isConnectedWifi(applicationContext)) {
      val urlEpg = intent?.getStringExtra(
          INTENT_URL_EPG)
      urlEpg?.let { s ->
        Log.d(Constants.APP_NAME, "Init SaveEpg Service")
        downloadEpgData(s)
      }
    }
  }

  private fun downloadEpgData(urlEpg: String) {
    Log.d(Constants.APP_NAME, "Init download EPG ==>")
    val dataStream = HttpConnection(urlEpg).downloadDataStream()

    dataStream.inputStream?.let { _ ->
      saveEpgFile(dataStream)
    }

  }

  private fun saveEpgFile(dataStream: DataStream) {
    Log.d(Constants.APP_NAME, "Init save file EPG ==>")
    val fileName = getFileName(dataStream)
    if (fileName.isNotEmpty()) {
      HelperFiles.saveFileToIntenalStorage(applicationContext, fileName,
          dataStream)
    }

    parseEpgFile(fileName)
  }

  private fun parseEpgFile(fileName: String) {
    Log.d(Constants.APP_NAME,
        "Init parse file EPG ==> " + System.currentTimeMillis())
    val fileEpgStream = HelperFiles.getFileStream(applicationContext, fileName)
    fileEpgStream?.let { inputStream ->
      if (fileName.endsWith("gz")) {
        parseGizFile(inputStream)
      } else {
        parseXmlFile(inputStream)
      }
      SharedPreferenceRepository.saveLastTimeSaveEpg(applicationContext)
      fireNotification()
    }
  }

  private fun parseXmlFile(inputStream: InputStream) {
    EpgParseSave().parse(inputStream)
    Log.d(Constants.APP_NAME, "Finish parse xml file EPG ==>")
  }

  private fun parseGizFile(inputStream: InputStream) {
    try {
      val gzipStream = GZIPInputStream(inputStream)
      EpgParseSave().parse(gzipStream)
      Log.d(Constants.APP_NAME,
          "Finish parse Gzip file EPG ==> " + System.currentTimeMillis())
    } catch (ex: IOException) {
      Log.d(Constants.APP_NAME, "Error parsing Gzip file")
    }
  }

  private fun getFileName(dataStream: DataStream): String {
    if (dataStream.contentType == "application/x-gzip") {
      return EPG_FILE_NAME + EPG_EXTENSION_GZ
    } else if (dataStream.contentType == "text/xml") {
      return EPG_FILE_NAME + EPG_EXTENSION_XML
    }
    return ""
  }


  override fun onDestroy() {
    Log.d(Constants.APP_NAME, "onDestroy SaveEpgService")
    super.onDestroy()
  }

  private fun fireNotification() {
    LocalNotificationManager().fireLocalNotification(applicationContext,
        string.notification_save_epg_title, R.string.notification_ended_save_epg)
  }

}