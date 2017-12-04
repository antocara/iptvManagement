package com.anp.iptvmanagement.presentations.initialscreen

import android.os.Bundle
import android.support.v17.leanback.app.BrowseFragment
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import android.support.v17.leanback.widget.ListRowPresenter
import android.support.v4.content.ContextCompat
import com.anp.commons.data.MyChannelsListRepository
import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.presentations.base.BaseView.SourceListView
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.R.color
import com.anp.iptvmanagement.R.string
import com.anp.iptvmanagement.data.SettingOption
import com.anp.iptvmanagement.manager.BackgroundManager
import com.anp.iptvmanagement.presentations.base.BaseBrowseFragment


class InitialScreenFragment : BaseBrowseFragment(), SourceListView {


  private lateinit var mRowsAdapter: ArrayObjectAdapter
  private var backgroundManager: BackgroundManager? = null
  private var gridPresenter: InitialScreenPresenter? = null
  private var channelsList: List<MyChannelsList>? = null

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    backgroundManager = BackgroundManager(activity)
    mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    setupUIElements()
    initializePresenter()
    setupEventListeners()
    addSettingsOptions()
  }

  override fun onResume() {
    super.onResume()
    getChannelsList()
  }

  override fun onDestroy() {
    backgroundManager?.stop()
    super.onDestroy()
  }


  private fun setupUIElements() {
    title = getString(string.app_name)
    // over title
    headersState = BrowseFragment.HEADERS_ENABLED
    isHeadersTransitionOnBackEnabled = true

    // set fastLane (or headers) background color
    brandColor = ContextCompat.getColor(activity,
        color.fastlane_background)
    // set search icon color
    searchAffordanceColor = ContextCompat.getColor(activity,
        color.search_opaque)

  }

  private fun initializePresenter() {
    gridPresenter = InitialScreenPresenter(activity, MyChannelsListRepository(),
        this)
  }

  private fun getChannelsList() {
    gridPresenter?.getListWithGroups()
  }


  private fun setupEventListeners() {
//    setOnSearchClickedListener {
//      Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
//          .show()//todo
//    }

    onItemViewClickedListener = ItemViewClickedListener(activity)
    onItemViewSelectedListener = ItemViewSelectedListener(backgroundManager)
  }

  private fun addSettingsOptions() {
    /* settings section */
    createPreferencesSection(channelsList ?: arrayListOf())
    adapter = mRowsAdapter
  }

  /**
   * Callback @{@link SourceListView}
   */

  override fun displaySourceList(myChannelsLists: List<MyChannelsList>) {
    channelsList = myChannelsLists

    val existingList = mRowsAdapter.unmodifiableList<ListRow>()
    channelsList?.forEach { myChannelsList ->

      val exist = existingList.filter { listRow ->
        listRow.headerItem.name == myChannelsList.name
      }.isNotEmpty()
      if (!exist) {
        createListRows()
      }
    }
  }


  private fun createListRows() {
    channelsList?.let { list ->
      /* Header Row */
      var headerItem: HeaderItem? = null
      for (i in list.indices) {
        headerItem = HeaderItem(i.toLong(), list[i].name)
        createListGroups(list[i], headerItem)
      }
    }
  }

  private fun createListGroups(channelsList: MyChannelsList, headerItem: HeaderItem) {
    val groupsList = channelsList.channels
    groupsList?.let { listChannels ->
      val gridRowAdapter = ArrayObjectAdapter(gridPresenter)
      for (j in listChannels.indices) {
        gridRowAdapter.add(listChannels[j])
      }
      mRowsAdapter.add(0, ListRow(headerItem, gridRowAdapter))
    }
  }


  private fun createPreferencesSection(list: List<MyChannelsList>) {

    val headerItem = HeaderItem((list.size + 1).toLong(), getString(R.string.preferences_title))

    val settingsPresenter = InitialScreenImageCardViewPresenter()

    val addChannelSettings = SettingOption(resources.getString(R.string.preferences_channels_list),
        R.drawable.ic_cloud_download_white_48dp)
    val editChannelSettings = SettingOption(resources.getString(R.string.preferences_epg_list),
        R.drawable.ic_add_to_queue_white_48dp)
    val settings = SettingOption(resources.getString(R.string.preferences_settings),
        R.drawable.ic_settings_white_48dp)

    val gridRowAdapter = ArrayObjectAdapter(settingsPresenter)
    gridRowAdapter.add(addChannelSettings)
    gridRowAdapter.add(editChannelSettings)
    gridRowAdapter.add(settings)

    mRowsAdapter.add(ListRow(headerItem, gridRowAdapter))
  }

}
