package com.anp.iptvmanagement.presentations.initialscreen

import android.app.Activity
import android.content.Intent
import android.support.v17.leanback.widget.OnItemViewClickedListener
import android.support.v17.leanback.widget.Presenter
import android.support.v17.leanback.widget.Row
import android.support.v17.leanback.widget.RowPresenter
import android.support.v4.app.ActivityOptionsCompat
import android.widget.Toast
import com.anp.commons.data.entities.Channel
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.R.string
import com.anp.iptvmanagement.data.SettingOption
import com.anp.iptvmanagement.manager.NavigationManager
import com.anp.iptvmanagement.presentations.editlistchannels.DialogEditListActivity
import com.anp.iptvmanagement.presentations.error.BrowseErrorActivity
import com.anp.iptvmanagement.presentations.verticalgridchannels.VerticalGridChannelsActivity


class ItemViewClickedListener(private var context: Activity) : OnItemViewClickedListener {


  override fun onItemClicked(itemViewHolder: Presenter.ViewHolder, item: Any,
      rowViewHolder: RowPresenter.ViewHolder, row: Row) {

    when (item) {
      is Channel -> openDetailActivity(item, itemViewHolder)
      is String -> checkErrorType(item)
      is SettingOption -> checkSettingOption(item)
    }
  }

  private fun checkErrorType(item: String) {
    if (item.contains(context.getString(string.error_fragment))) {
      openErrorActivity()
    } else if (item.contains(context.getString(string.preferences_add_list))) {
      openAddChannel()
    } else if (item.contains(context.getString(string.preferences_edit_list))) {
      openEditList()
    } else if (item.contains(context.getString(string.preferences_settings))) {
      openEditList()
    } else {
      Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
    }
  }

  private fun checkSettingOption(item: SettingOption) {
    when (item.title) {
      context.getString(R.string.preferences_channels_list) -> openAddChannel()
      context.getString(R.string.preferences_epg_list) -> openEpgSettings()
      context.getString(string.preferences_settings) -> openSettings()
      else -> openErrorActivity()
    }
  }

  private fun openAddChannel() {
    NavigationManager.navigateToAddChannelActivity(context)
  }

  private fun openEpgSettings() {
    NavigationManager.navigateToEpgSettings(context)
  }

  private fun openSettings() {
    NavigationManager.navigateToSettings(context)
  }

  private fun openErrorActivity() {
    val intent = Intent(context, BrowseErrorActivity::class.java)
    context.startActivity(intent)
  }

  private fun openDetailActivity(item: Channel, itemViewHolder: Presenter.ViewHolder) {
    val intent = Intent(context, VerticalGridChannelsActivity::class.java)
    intent.putExtra(VerticalGridChannelsActivity.KEY_INTENT_CHANNEL, item)


    val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(context)
        .toBundle()
    context.startActivity(intent, bundle)
  }


  private fun openEditList() {
    val intent = Intent(context, DialogEditListActivity::class.java)
    context.startActivity(intent)
  }
}