package com.anp.iptvmanagement.presentations.base

import android.support.v17.leanback.app.GuidedStepFragment
import com.anp.iptvmanagement.R


open class BaseGuideStep : GuidedStepFragment() {

  override fun onProvideTheme(): Int =
      R.style.Theme_Example_Leanback_GuidedStep_First


  protected fun popBackStackFragment() {
    fragmentManager.popBackStack()
  }
}