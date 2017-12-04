package com.anp.commons.presentations.base

import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.MyChannelsList


interface BaseView {

  fun showLoading()

  fun hideLoading()


  interface AddChannelsListView : BaseView {
    fun addedListSuccess()

    fun addedListFail()
  }


  interface SourceListView : BaseView {
    fun displaySourceList(myChannelsLists: List<MyChannelsList>)
  }

  interface ViewpagerChannelsView : BaseView {
    fun displayGroups(groups: List<Channel>)
  }

  interface GridChannelsView : BaseView {
    fun displayChannels(channels: List<Channel>)

    fun displayFavoritesList(favoritesList: List<MyChannelsList>)
  }

  interface SearchView: BaseView {
    fun displaySearch(channels: List<Channel>)
  }

  interface GridChannelsFavoriteListView: BaseView {
    fun displayFavorites(channelsFavorites: List<Channel>)
  }


  interface InitialScreenTvView: BaseView{
    fun displaySourceList(myChannelsLists: List<MyChannelsList>)
    fun displayGroups(groups: List<Channel>)
  }
}