package com.anp.commons.data.net

import com.anp.commons.data.entities.DataStream
import com.anp.commons.helpers.HelperHttp
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class HttpConnection(private val urlSource: String) {


  fun downloadDataStream(): DataStream {
    val dataStream = DataStream()

    val url: URL
    var conn: HttpURLConnection? = null
    var inputStream: InputStream? = null
    var contentType: String? = ""

    try {
      url = URL(urlSource)
      conn = url.openConnection() as HttpURLConnection
      conn.apply {
        connectTimeout = 6000
        readTimeout = 6000
        requestMethod = "GET"
      }

      val result = conn.responseCode
      if (result == 200) {
        contentType = conn.contentType
        inputStream = BufferedInputStream(conn.inputStream)
      }

    } catch (e: MalformedURLException) {
      e.printStackTrace()
      HelperHttp.disconnectConnection(conn)
      HelperHttp.closeInputStream(inputStream)
    } catch (e: IOException) {
      HelperHttp.disconnectConnection(conn)
      HelperHttp.closeInputStream(inputStream)
    }


    dataStream.contentType = contentType
    dataStream.inputStream = inputStream
    return dataStream
  }

}