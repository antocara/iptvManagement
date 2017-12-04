package com.anp.commons.data.database.daos

import com.anp.commons.data.database.tables.ChannelRealm
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.MyChannelsList
import io.realm.Case
import io.realm.Sort
import io.realm.Sort.ASCENDING


class ChannelsDao : BaseDao() {


  fun saveChannelsList(channelList: List<Channel>, myChannelsList: MyChannelsList) {
    val listRealmChannels = arrayListOf<ChannelRealm>()
    channelList.map { channel ->
      channel.sourceListId = myChannelsList.id
      listRealmChannels.add(ChannelRealm.parseToRealmObject(channel))
    }
    saveChannelsAtRealm(listRealmChannels)
  }

  private fun saveChannelsAtRealm(channels: List<ChannelRealm>) {
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      realm.insertOrUpdate(channels)
    }
    closeRealmInstance(realmInstance)
  }

  fun deleteChannels(myChannelsList: MyChannelsList) {
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val result = realm.where(ChannelRealm::class.java).equalTo(ChannelRealm.SOURCE_LIST_ID_FIELD,
          myChannelsList.id).findFirst()
      result?.deleteFromRealm()
    }
    closeRealmInstance(realmInstance)
  }

  fun deleteAllChannelsChannels() {
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val result = realm.where(ChannelRealm::class.java).findAll()
      result?.deleteAllFromRealm()
    }
    closeRealmInstance(realmInstance)
  }

  fun getGroupsBySourceList(myChannelsList: MyChannelsList): List<Channel> {
    var result = listOf<Channel>()
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val resultQuery = realm.where(ChannelRealm::class.java).equalTo(
          ChannelRealm.SOURCE_LIST_ID_FIELD,
          myChannelsList.id).distinct(ChannelRealm.GROUP_FIELD).sort(ChannelRealm.GROUP_FIELD,
          ASCENDING)

      result = ChannelRealm.parseFromListRealm(resultQuery)

    }
    closeRealmInstance(realmInstance)
    return result
  }


  fun getChannelsByGroup(channel: Channel): List<Channel> {
    var result = listOf<Channel>()
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val resultQuery = realm.where(ChannelRealm::class.java).equalTo(
          ChannelRealm.GROUP_FIELD,
          channel.group).distinct(ChannelRealm.TITLE_CHANNEL_FIELD).sort(
          ChannelRealm.TITLE_CHANNEL_FIELD,
          ASCENDING)

      result = ChannelRealm.parseFromListRealm(resultQuery)

    }
    closeRealmInstance(realmInstance)

    return result
  }


  fun searchChannelByTitle(title: String): List<Channel> {
    var result = listOf<Channel>()
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val searchPattern =  title + "*"

      val resultQuery = realm.where(ChannelRealm::class.java)
          .like(ChannelRealm.TITLE_CHANNEL_FIELD,  searchPattern, Case.INSENSITIVE)
          .findAllSorted(ChannelRealm.TITLE_CHANNEL_FIELD, Sort.ASCENDING)

      result = ChannelRealm.parseFromListRealm(resultQuery)

    }
    closeRealmInstance(realmInstance)
    return result
  }

  fun getChannelRealmById(id: String): ChannelRealm? {
    var result = listOf<ChannelRealm>()
    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->

      val resultQuery = realm.where(ChannelRealm::class.java)
          .equalTo(ChannelRealm.ID_FIELD,  id, Case.INSENSITIVE)
          .findAllSorted(ChannelRealm.ID_FIELD, Sort.ASCENDING)

       result = resultQuery

    }
    closeRealmInstance(realmInstance)
    if (result.isEmpty()){
      return ChannelRealm()
    }
    return result.first()
  }
}