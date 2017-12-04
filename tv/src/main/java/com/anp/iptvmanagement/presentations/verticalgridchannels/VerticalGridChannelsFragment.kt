package com.anp.iptvmanagement.presentations.verticalgridchannels

import android.os.Bundle
import android.support.v17.leanback.app.VerticalGridFragment
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.FocusHighlight
import android.support.v17.leanback.widget.OnItemViewClickedListener
import android.support.v17.leanback.widget.Presenter.ViewHolder
import android.support.v17.leanback.widget.Row
import android.support.v17.leanback.widget.RowPresenter
import android.support.v17.leanback.widget.VerticalGridPresenter
import com.anp.commons.data.ChannelsRepository
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.helpers.HelperPlayer
import com.anp.commons.presentations.base.BaseView.GridChannelsView
import com.anp.iptvmanagement.manager.BackgroundManager
import com.anp.iptvmanagement.manager.NavigationManager
import com.anp.iptvmanagement.presentations.initialscreen.ItemViewSelectedListener


class VerticalGridChannelsFragment : VerticalGridFragment(), GridChannelsView {

  private val NUM_COLUMNS = 4
  private var mAdapter: ArrayObjectAdapter? = null
  private var channelGroup: Channel? = null
  private var presenter: VerticalGridChannelsPresenter? = null
  private var backgroundManager: BackgroundManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    backgroundManager = BackgroundManager(activity)

    initializePresenter()
    interceptData()

    createVerticalAdapter()
    getChannelsList()

    setupEventListeners()
  }

  override fun onDestroy() {
    backgroundManager?.stop()
    super.onDestroy()
  }


  private fun interceptData() {
    if (activity != null && activity.intent != null) {
      channelGroup = activity.intent.extras.getParcelable<Channel>(
          VerticalGridChannelsActivity.KEY_INTENT_CHANNEL)
    }
  }

  private fun initializePresenter() {
    presenter = VerticalGridChannelsPresenter(this, ChannelsRepository())
  }

  private fun createVerticalAdapter() {
    val gridPresenter = VerticalGridPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM)
    gridPresenter.numberOfColumns = NUM_COLUMNS
    setGridPresenter(gridPresenter)
  }

  private fun getChannelsList() {
    channelGroup?.let { it ->
      title = it.group
      presenter?.getChannelsByGroup(it)
    }
  }

  /**
   * Callback @{@link GridChannelsView}//todo
   */
  override fun showLoading() {

  }

  override fun hideLoading() {
  }

  override fun displayChannels(channels: List<Channel>) {
    createAdapter(channels)
  }

  override fun displayFavoritesList(favoritesList: List<MyChannelsList>) {

  }

  private fun createAdapter(channels: List<Channel>) {
    mAdapter = ArrayObjectAdapter(presenter)

    channels.map { channel ->
      mAdapter?.add(channel)
    }

    adapter = mAdapter
  }

  private fun setupEventListeners() {
    onItemViewClickedListener = ItemViewClickedListener()
    setOnItemViewSelectedListener(ItemViewSelectedListener(backgroundManager))
  }

  private inner class ItemViewClickedListener : OnItemViewClickedListener {


    override fun onItemClicked(itemViewHolder: ViewHolder?, item: Any?,
        rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {

      if (item is Channel) {
        openPlayer(item)
      }
    }
  }

  private fun openPlayer(channel: Channel) {
    if (HelperPlayer.isPlayerInstalled(activity)) {
      NavigationManager.openPlayer(activity, channel)
    } else {
      HelperPlayer.openGooglePlayToDownloadPlayer(activity)
    }
  }

}