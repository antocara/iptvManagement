package com.anp.iptvmanagement.presentations.verticalgridchannels

import android.app.Activity
import android.os.Bundle
import com.anp.iptvmanagement.R

class VerticalGridChannelsActivity : Activity() {

  companion object {
    const val KEY_INTENT_CHANNEL = "Channel"
    const val SHARED_ELEMENT_NAME = "channel_shared"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_vertical_grid_channels)
  }
}
