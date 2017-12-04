package com.anp.iptvmanagement.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.anp.commons.Constants
import com.anp.commons.data.entities.Channel
import com.anp.commons.helpers.HelperPlayer
import com.anp.iptvmanagement.presentations.addchannelslistepg.AddChannelsListActivity
import com.anp.iptvmanagement.presentations.epgsettings.EpgSettingsActivity
import com.anp.iptvmanagement.presentations.settings.SettingsActivity


object NavigationManager {

  fun navigateToAddChannelActivity(context: Context){
    val intent = Intent(context, AddChannelsListActivity::class.java)
    context.startActivity(intent)
  }

  fun navigateToEpgSettings(context: Context){
    val intent = Intent(context, EpgSettingsActivity::class.java)
    context.startActivity(intent)
  }

  fun navigateToSettings(context: Context){
    val intent = Intent(context, SettingsActivity::class.java)
    context.startActivity(intent)
  }

  fun openPlayer(activity: Fragment, channel: Channel) {
    activity.startActivityForResult(
        HelperPlayer.openPlayerAndPlay(channel, activity.activity),
        Constants.REQUEST_CODE_VLC)
  }

  fun openPlayer(activity: Activity, channel: Channel) {
    activity.startActivityForResult(
        HelperPlayer.openPlayerAndPlay(channel, activity),
        Constants.REQUEST_CODE_VLC)
  }
}