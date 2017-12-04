package com.anp.iptvmanagement.presentations.epgsettings

import android.os.Bundle
import android.support.v17.leanback.widget.GuidanceStylist
import android.support.v17.leanback.widget.GuidedAction
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.presentations.base.BaseGuideStep


class AddEditEpgSettings : BaseGuideStep() {

  override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
    val title = getString(R.string.settings_epg_title)
    val description = getString(R.string.settings_epg_legend)
    val icon = activity.getDrawable(R.drawable.ic_add_to_queue_white_48dp)
    return GuidanceStylist.Guidance(title, description, "", icon)
  }

  override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {

    val addChannelAction = GuidedAction.Builder(activity).title(
        getString(R.string.settings_add_epg)).build()
    val editChannelAction = GuidedAction.Builder(activity).title(
        getString(R.string.settings_edit_epg)).build()
    val deleteChannelAction = GuidedAction.Builder(activity).title(
        getString(R.string.settings_delete_epg)).build()
    val cancelAction = GuidedAction.Builder(activity).title(getString(R.string.cancel)).build()

    actions.add(addChannelAction)
    actions.add(editChannelAction)
    actions.add(deleteChannelAction)
    actions.add(cancelAction)
  }

  override fun onGuidedActionClicked(action: GuidedAction?) {

    action?.let { guidedAction ->
      val actionTitle = guidedAction.title

      when (actionTitle) {
        getString(R.string.settings_add_epg) -> {
          openAddEpgOption()
        }
        getString(R.string.settings_edit_epg) -> {
          openEditEpgOption()
        }
        getString(R.string.settings_delete_epg) -> openDeleteEpg()
        else -> {
          activity.finishAfterTransition()
        }
      }
    }
  }

  private fun openAddEpgOption() {
    val fragment = AddEpgGuideStep()
    add(fragmentManager, fragment)
  }

  private fun openEditEpgOption() {
    val fragment = AddEpgGuideStep()
    add(fragmentManager, fragment)
  }

  private fun openDeleteEpg(){
    val fragment = DeleteEpgGuideStep()
    add(fragmentManager, fragment)
  }
}