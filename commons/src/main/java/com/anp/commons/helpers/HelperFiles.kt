package com.anp.commons.helpers

import android.content.Context
import android.util.Log
import com.anp.commons.Constants
import com.anp.commons.data.entities.DataStream
import java.io.Closeable
import java.io.FileNotFoundException
import java.io.Flushable
import java.io.IOException
import java.io.InputStream


object HelperFiles {


  fun getFileStream(context: Context, fileName: String): InputStream? {
    try {
      return context.openFileInput(fileName)
    } catch (ex: FileNotFoundException) {
      Log.d(Constants.APP_NAME, "Error getting stream internal storage")
    }
    return null
  }


  fun saveFileToIntenalStorage(context: Context, fileName: String,
      dataStream: DataStream): Boolean {
    var result = false

    val fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)
    val inputStream = dataStream.inputStream
    try {
      inputStream?.let { stream ->
        stream.use { input ->
          fos.use { fileOut ->
            input.copyTo(fileOut)
          }
        }
        result = true
      }

    } catch (e: IOException) {
      Log.d(Constants.APP_NAME, "Error saving file internal storage")
    } finally {
      flushStream(fos)
      closeStream(fos)
      closeStream(inputStream)
    }

    return result
  }


  private fun closeStream(stream: Closeable?) {
    try {
      stream?.close()
    } catch (e: IOException) {
      Log.d(Constants.APP_NAME, "Error closing streams file internal storage")
    }
  }

  private fun flushStream(stream: Flushable?) {
    try {
      stream?.flush()
    } catch (e: IOException) {
      Log.d(Constants.APP_NAME, "Error flushing streams file internal storage")
    }
  }


  fun getFileExtension(uri: String): String {
    var fileExtention = ""

    val index = uri.lastIndexOf(".")
    if (index != -1) {
      fileExtention = uri.substring(index, uri.length)
    }

    return fileExtention
  }

}