package com.anp.iptvmanagement.presentations.epgsettings

import android.support.v17.leanback.widget.GuidedAction
import com.anp.commons.data.EpgRepository


class DeleteEpgGuideStep : AddEpgGuideStep() {

  override fun onGuidedActionClicked(action: GuidedAction?) {

    action?.let { guidedAction ->

      epgStored?.let { epg ->
        deleteEpg()
      }
    }
  }

  private fun deleteEpg() {
    EpgRepository().deleteEpgAndPrograms(activity)
    popBackStackFragment()
  }

}