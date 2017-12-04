package com.anp.iptvmanagement.presentations.epgsettings

import android.app.Activity
import android.os.Bundle
import android.support.v17.leanback.app.GuidedStepFragment


class EpgSettingsActivity: Activity()  {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (null == savedInstanceState) {
      GuidedStepFragment.addAsRoot(this, AddEditEpgSettings(), android.R.id.content)
    }
  }
}