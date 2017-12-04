package com.anp.commons.data.database.tables

import com.anp.commons.data.entities.ListTypes
import com.anp.commons.data.entities.MyChannelsList
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class MyChannelsListRealm : RealmObject() {

  @PrimaryKey
  var id: String = ""
  var name: String? = null
  var sourceUrl: String? = null
  var listType: Int = ListTypes.SOURCE
  var channels: RealmList<ChannelRealm>? = null


  companion object {
    val ID_FIELD = "id"
    val LIST_TYPE_FIELD = "listType"


    fun parseToRealmObject(myChannelsList: MyChannelsList): MyChannelsListRealm {
      return MyChannelsListRealm().apply {
        id = myChannelsList.id
        name = myChannelsList.name
        sourceUrl = myChannelsList.url
        listType = myChannelsList.listType
      }

    }

    fun parseFromRealmObject(myChannelsListRealm: MyChannelsListRealm): MyChannelsList {

      return MyChannelsList().apply {
        id = myChannelsListRealm.id
        name = myChannelsListRealm.name ?: ""
        url = myChannelsListRealm.sourceUrl
        listType = myChannelsListRealm.listType
        channels = ChannelRealm.parseFromListRealm(
            myChannelsListRealm.channels?.toList() ?: listOf())
      }

    }


    fun parseListRealmToModelObject(listRealm: List<MyChannelsListRealm>): List<MyChannelsList> {
      val arrayMyChannelsList = arrayListOf<MyChannelsList>()

      listRealm.map { sourceListRealm ->
        val myFavoriteList = parseFromRealmObject(sourceListRealm)
        arrayMyChannelsList.add(myFavoriteList)
      }

      return arrayMyChannelsList
    }
  }

}