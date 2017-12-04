package com.anp.commons.data.database.tables

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class ChannelEpgRealm : RealmObject() {


  @PrimaryKey
  var originalNetworkId: String = ""
  var displayName: String? = null
  var displayNumber: String? = null
  var channelLogo: String? = null
  var appLinkText: String? = null
  var appLinkColor: Int = 0
  var appLinkIconUri: String? = null
  var appLinkPosterArtUri: String? = null
  var appLinkIntentUri: String? = null
  var transportStreamId: Int = 0
  var serviceId: Int = 0
  var mInternalProviderData: ByteArray? = null



}