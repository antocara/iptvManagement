package com.anp.commons.data

import com.anp.commons.data.database.daos.ChannelsDao
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.MyChannelsList
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers


class ChannelsRepository {


  fun saveMyChannelsList(channelsList: List<Channel>, myChannelsList: MyChannelsList) {
    ChannelsDao().saveChannelsList(channelsList, myChannelsList)
  }

  fun deleteChannelsList(myChannelsList: MyChannelsList) {
    ChannelsDao().deleteChannels(myChannelsList)
  }


  fun getGroups(myChannelsList: MyChannelsList): List<Channel> {
    return ChannelsDao().getGroupsBySourceList(myChannelsList)
  }

  fun getGroupsBySourceList(myChannelsList: MyChannelsList): Observable<List<Channel>> {

    return Observable.unsafeCreate({ t: Subscriber<in List<Channel>>? ->
      Thread(Runnable {
        val channels = ChannelsRepository().getGroups(myChannelsList)
        t?.onNext(channels)
        t?.onCompleted()
      }).start()
    }).observeOn(AndroidSchedulers.mainThread())

  }

  fun getChannelsByGroup(channel: Channel): Observable<List<Channel>> {


    return Observable.unsafeCreate({ t: Subscriber<in List<Channel>>? ->
      Thread(Runnable {
        val channels = ChannelsDao().getChannelsByGroup(channel)
        t?.onNext(channels)
        t?.onCompleted()
      }).start()
    }).observeOn(AndroidSchedulers.mainThread())


  }
}