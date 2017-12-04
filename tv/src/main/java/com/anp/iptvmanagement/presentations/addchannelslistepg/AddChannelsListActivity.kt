package com.anp.iptvmanagement.presentations.addchannelslistepg

import android.app.Activity
import android.os.Bundle
import android.support.v17.leanback.app.GuidedStepFragment


class AddChannelsListActivity : Activity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (null == savedInstanceState) {
      GuidedStepFragment.addAsRoot(this, AddEditChannelList(), android.R.id.content)
    }
  }

}