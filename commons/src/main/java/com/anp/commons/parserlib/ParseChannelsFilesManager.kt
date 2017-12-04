package com.anp.commons.parserlib

import android.os.AsyncTask
import com.anp.commons.data.entities.ChannelsList
import com.anp.commons.data.entities.DataStream
import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.data.net.HttpConnection
import com.anp.commons.parserlib.exception.JPlaylistParserException
import com.anp.commons.parserlib.parser.AutoDetectParser
import org.xml.sax.SAXException
import rx.Observable
import java.io.IOException


object ParseChannelsFilesManager {


  fun downloadChannels(myChannelsList: MyChannelsList): Observable<ChannelsList> {

    return Observable.create<ChannelsList> { t ->
      GetChannelsFromListTask(myChannelsList,
          object : OnGetChannelsListener {
            override fun getChannelEnded(playList: ChannelsList) {
              t.onNext(playList)
            }
          }).execute()
    }
  }

  private class GetChannelsFromListTask(
      val myChannelsList: MyChannelsList,
      val callback: OnGetChannelsListener) : AsyncTask<Void, Void, ChannelsList>() {


    override fun doInBackground(vararg params: Void?): ChannelsList? {

      myChannelsList.url?.let { s ->
        val dataStream = HttpConnection(s).downloadDataStream()
        return parseChannels(dataStream, myChannelsList)
      }

      return ChannelsList()
    }

    override fun onPostExecute(result: ChannelsList) {
      callback.getChannelEnded(result)
    }

  }


  private fun parseChannels(dataStream: DataStream, myChannelsList: MyChannelsList): ChannelsList {
    val parser = AutoDetectParser()
    val playlist = ChannelsList()


    if (dataStream.inputStream != null) {
      try {
        parser.parse(myChannelsList.url,
            dataStream.contentType,
            dataStream.inputStream,
            playlist)
      } catch (e: IOException) {
      } catch (e: SAXException) {
      } catch (e: JPlaylistParserException) {
      }

    }

    return playlist
  }

}

interface OnGetChannelsListener {
  fun getChannelEnded(playList: ChannelsList)
}