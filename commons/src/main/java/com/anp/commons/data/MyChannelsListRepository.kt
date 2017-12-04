package com.anp.commons.data

import com.anp.commons.data.database.daos.MyChannelsListDao
import com.anp.commons.data.entities.MyChannelsList
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers


class MyChannelsListRepository {

  fun saveMyChannelsList(myChannelsList: MyChannelsList) {
    MyChannelsListDao().saveMyChannelsList(myChannelsList)
  }


  fun getAllMyChannelsList(): Observable<List<MyChannelsList>> {

    return Observable.unsafeCreate({ t: Subscriber<in List<MyChannelsList>>? ->
      Thread(Runnable {
        val dataList = MyChannelsListDao().getAllMyChannelsList()
        t?.onNext(dataList)
        t?.onCompleted()
      }).start()
    }).observeOn(AndroidSchedulers.mainThread())
  }

  fun deleteMyChannelList(myChannelsList: MyChannelsList) {
    ChannelsRepository().deleteChannelsList(myChannelsList)
    MyChannelsListDao().deleteMyChannelsList(myChannelsList)
  }


  fun getListWithGroups(): Observable<List<MyChannelsList>> {

    return Observable.unsafeCreate({ t: Subscriber<in List<MyChannelsList>>? ->
      Thread(Runnable {

        val myLists = MyChannelsListDao().getAllMyChannelsList()

        myLists.map { myChannelsList ->
          val groups = ChannelsRepository().getGroups(myChannelsList)
          myChannelsList.channels = groups
        }

        t?.onNext(myLists)
        t?.onCompleted()
      }).start()
    }).observeOn(AndroidSchedulers.mainThread())


  }

}