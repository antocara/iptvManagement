package com.anp.commons.data.database.tables

import com.anp.commons.data.entities.Channel
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class ChannelRealm : RealmObject() {


  var id: String = ""
  @PrimaryKey
  var titleChannel: String = ""
  var urlStream: String = ""
  var group: String = ""
  var sourceListId: String = ""
  var logoUrl: String = ""
  var programEpgList: RealmList<ProgramEpgRealm>? = null


  companion object {
    val ID_FIELD = "id"
    val SOURCE_LIST_ID_FIELD = "sourceListId"
    val GROUP_FIELD = "group"
    val SOURCE_LIST_FIELD = "sourceListId"
    val TITLE_CHANNEL_FIELD = "titleChannel"


    fun parseToRealmObject(channel: Channel): ChannelRealm {
      return ChannelRealm().apply {
        id = channel.id
        titleChannel = channel.titleChannel
        urlStream = channel.urlStream
        group = channel.group ?: Channel.GROUP
        sourceListId = channel.sourceListId
        logoUrl = channel.logo ?: ""
        programEpgList = ProgramEpgRealm.toListProgramEpgRealm(channel.programEpgList)
      }

    }


    fun parseFromListRealm(listRealm: List<ChannelRealm>): List<Channel> {
      val arrayChannels = arrayListOf<Channel>()

      listRealm.map { channelRealm ->

        Channel().apply {
          id = channelRealm.id
          titleChannel = channelRealm.titleChannel
          urlStream = channelRealm.urlStream
          group = channelRealm.group
          sourceListId = channelRealm.sourceListId
          logo = channelRealm.logoUrl
          programEpgList = ProgramEpgRealm.toListProgram(channelRealm.programEpgList)
        }.run { arrayChannels.add(this) }

      }

      return arrayChannels
    }

  }


  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other?.javaClass != javaClass) {
      return false
    }

    other as ChannelRealm
    if (this.titleChannel == other.titleChannel) {
      return true
    }

    return false
  }

}