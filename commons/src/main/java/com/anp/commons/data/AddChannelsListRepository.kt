package com.anp.commons.data

import android.content.Context
import android.content.Intent
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.ChannelsList
import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.data.responses.BaseResponse
import com.anp.commons.data.responses.ResponseParseChannelsList
import com.anp.commons.data.service.SaveChannelsService
import com.anp.commons.parserlib.ParseChannelsFilesManager
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers


class AddChannelsListRepository {

  fun saveChannelsList(myChannelsList: MyChannelsList, context: Context): Observable<BaseResponse> {

    return Observable.unsafeCreate({ t: Subscriber<in BaseResponse>? ->
      Thread({
        ParseChannelsFilesManager
            .downloadChannels(myChannelsList)
            .subscribe { channelsList: ChannelsList? ->

              handleParseChannelsEvent(channelsList, myChannelsList, context)
              t?.onNext(getResponseCallback(channelsList))
              t?.onCompleted()
            }
      }).start()
    }).observeOn(AndroidSchedulers.mainThread())
  }

  private fun handleParseChannelsEvent(channelsList: ChannelsList?, myChannelsList: MyChannelsList,
      context: Context) {
    val channels = channelsList?.playlistEntries

    if (channels != null && channels.size > 0) {
      MyChannelsListRepository().saveMyChannelsList(myChannelsList)
      saveChannels(myChannelsList, channels, context)
    }
  }

  private fun getResponseCallback(channelsList: ChannelsList?): BaseResponse {
    var response = BaseResponse(BaseResponse.PARSER_FILE_ERROR)
    if (channelsList?.playlistEntries != null) {
      response = ResponseParseChannelsList(channelsList)
    }

    return response
  }

  private fun saveChannels(myChannelsList: MyChannelsList, channelsList: List<Channel>,
      context: Context) {
    val intent = Intent(context, SaveChannelsService::class.java)
    SaveChannelsService.CHANNELS_LIST = channelsList
    intent.putExtra(SaveChannelsService.INTENT_SOURCE_LIST, myChannelsList)
    context.startService(intent)
  }


  fun saveFavoriteList(myChannelsList: MyChannelsList) {
    MyChannelsListRepository().saveMyChannelsList(myChannelsList)
  }

}