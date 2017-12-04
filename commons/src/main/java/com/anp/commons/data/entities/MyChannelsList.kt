package com.anp.commons.data.entities

import android.os.Parcel
import android.os.Parcelable
import java.util.UUID


open class MyChannelsList(var id: String, var name: String, var url: String?
) : Parcelable {
  var listType = ListTypes.SOURCE
  var channels: List<Channel>? = null

  constructor() : this("", "", "")

  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readString()
  ) {
    listType = source.readInt()
    channels = source.createTypedArrayList(Channel.CREATOR)
  }

  constructor(id: String, name: String, url: String?, channels: List<Channel>?) : this(
      id, name, url
  ) {
    this.channels = channels

  }

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(id)
    writeString(name)
    writeString(url)
    writeInt(listType)
    writeTypedList(channels)
  }

  companion object {
    fun getUniqueId(): String {
      return UUID.randomUUID().toString()
    }

    @JvmField
    val CREATOR: Parcelable.Creator<MyChannelsList> = object : Parcelable.Creator<MyChannelsList> {
      override fun createFromParcel(source: Parcel): MyChannelsList = MyChannelsList(source)
      override fun newArray(size: Int): Array<MyChannelsList?> = arrayOfNulls(size)
    }
  }
}