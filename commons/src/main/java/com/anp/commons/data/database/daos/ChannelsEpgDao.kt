package com.anp.commons.data.database.daos

import com.anp.commons.data.database.tables.ChannelEpgRealm


class ChannelsEpgDao : BaseDao() {


  fun saveChannel(channel: ChannelEpgRealm) {
    getRealmInstance().executeTransaction { realm ->
      realm.insertOrUpdate(channel)
    }
  }

  fun saveListChannels(channels: List<ChannelEpgRealm>) {
    getRealmInstance().executeTransaction { realm ->
      realm.insertOrUpdate(channels)
    }
  }
}