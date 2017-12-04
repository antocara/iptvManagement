package com.anp.iptvmanagement.presentations.addchannelslistepg

import android.os.Bundle
import android.support.v17.leanback.widget.GuidanceStylist
import android.support.v17.leanback.widget.GuidedAction
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.presentations.base.BaseGuideStep

class AddEditChannelList : BaseGuideStep() {

  override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
    val title = getString(R.string.settings_add_channel_title)
    val description = getString(R.string.settings_add_channel_legend)
    val icon = activity.getDrawable(R.drawable.ic_cloud_download_white_48dp)
    return GuidanceStylist.Guidance(title, description, "", icon)
  }

  override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {

    val addChannelAction = GuidedAction.Builder(activity).title(
        getString(R.string.settings_add_channel)).build()
    val editChannelAction = GuidedAction.Builder(activity).title(
        getString(R.string.settings_edit_channel)).build()
    val deleteChannelAction = GuidedAction.Builder(activity).title(
        getString(R.string.settings_delete_channel)).build()
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
        getString(R.string.settings_add_channel) -> {
          openAddChannelOption()
        }
        getString(R.string.settings_edit_channel) -> {
          openEditChannelOption()
        }
        getString(R.string.settings_delete_channel) -> openDeleteChannel()
        else -> {
          activity.finishAfterTransition()
        }
      }
    }
  }

  private fun openAddChannelOption() {
    val fragment = AddChannelGuideStep()
    add(fragmentManager, fragment)
  }

  private fun openEditChannelOption() {
    val fragment = EditChannelGuideStep()
    add(fragmentManager, fragment)
  }

  private fun openDeleteChannel(){
    val fragment = DeleteChannelGuideStep()
    add(fragmentManager, fragment)
  }
}