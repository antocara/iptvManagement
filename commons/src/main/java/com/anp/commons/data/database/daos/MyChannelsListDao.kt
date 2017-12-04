package com.anp.commons.data.database.daos

import com.anp.commons.data.database.tables.ChannelRealm
import com.anp.commons.data.database.tables.MyChannelsListRealm
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.ListTypes
import com.anp.commons.data.entities.MyChannelsList


class MyChannelsListDao : BaseDao() {


  fun saveMyChannelsList(myChannelsList: MyChannelsList) {
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val sourceListRealm = MyChannelsListRealm.parseToRealmObject(myChannelsList)
      realm.insertOrUpdate(sourceListRealm)
    }
    realmInstance.close()
  }

  fun getAllMyChannelsList(): List<MyChannelsList> {
    val realmInstance = getRealmInstance()
    val result = realmInstance.where(MyChannelsListRealm::class.java).findAll()
    val listRealmObjects = realmInstance.copyFromRealm(result)
    closeRealmInstance(realmInstance)
    return MyChannelsListRealm.parseListRealmToModelObject(listRealmObjects)
  }


  fun getAllMyFavoriteList(): List<MyChannelsList> {
    val realmInstance = getRealmInstance()

    val result = realmInstance.where(MyChannelsListRealm::class.java).equalTo(
        MyChannelsListRealm.LIST_TYPE_FIELD, ListTypes.FAVORITES).findAll()

    val listRealmObjects = realmInstance.copyFromRealm(result)
    closeRealmInstance(realmInstance)
    return MyChannelsListRealm.parseListRealmToModelObject(listRealmObjects)
  }

  fun deleteMyChannelsList(myChannelsList: MyChannelsList) {
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val result = realm.where(MyChannelsListRealm::class.java).equalTo(
          MyChannelsListRealm.ID_FIELD,
          myChannelsList.id).findFirst()
      result?.deleteFromRealm()
    }
    closeRealmInstance(realmInstance)
  }

  fun deleteAllMyChannelsList() {
    getRealmInstance().executeTransactionAsync { realm ->
      val result = realm.where(MyChannelsListRealm::class.java).findAll()
      result.deleteAllFromRealm()
    }
  }

  /** favorites **/
  fun saveChannelIntoMyList(channel: Channel, myFavoriteList: MyChannelsList) {
    getRealmInstance().executeTransactionAsync { realm ->
      val myFavListSaved = realm.where(MyChannelsListRealm::class.java).equalTo(
          MyChannelsListRealm.ID_FIELD,
          myFavoriteList.id).findFirst()

      val channelsSaved = myFavListSaved?.channels

      channelsSaved?.let { realmList ->
        val existChannel = realmList.filter { channelRealm ->
          channelRealm.titleChannel == channel.titleChannel
        }

        if (existChannel.isEmpty()) {
          realmList.add(ChannelRealm.parseToRealmObject(channel))
        }
      }
    }

  }

  fun deleteChannelFromMyList(channel: Channel, myFavoriteList: MyChannelsList) {
    getRealmInstance().executeTransactionAsync { realm ->
      val myFavListSaved = realm.where(MyChannelsListRealm::class.java).equalTo(
          MyChannelsListRealm.ID_FIELD,
          myFavoriteList.id).findFirst()


      val channelsSaved = myFavListSaved?.channels
      channelsSaved?.let { realmList ->

        val channelsFinded = realmList.filter { channelRealm ->
          channelRealm.titleChannel == channel.titleChannel
        }
        if (channelsFinded.isNotEmpty()) {
          realmList.remove(channelsFinded[0])
        }
      }
    }

  }

  fun getChannelsByFavoriteList(myFavoriteList: MyChannelsList): MyChannelsList {
    var myFavList = MyChannelsList()
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val result = realm.where(MyChannelsListRealm::class.java).equalTo(
          MyChannelsListRealm.ID_FIELD,
          myFavoriteList.id).findFirst()

      val favChannelsList = MyChannelsListRealm.parseFromRealmObject(
          result ?: MyChannelsListRealm())
      myFavList = favChannelsList
    }
    realmInstance.close()
    return myFavList
  }
}