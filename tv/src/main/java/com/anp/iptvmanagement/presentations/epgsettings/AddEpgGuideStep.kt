package com.anp.iptvmanagement.presentations.epgsettings

import android.os.Bundle
import android.support.v17.leanback.widget.GuidanceStylist
import android.support.v17.leanback.widget.GuidedAction
import com.anp.commons.data.EpgRepository
import com.anp.commons.managers.EpgCoordinator
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.presentations.base.BaseGuideStep


open class AddEpgGuideStep : BaseGuideStep() {


  protected var epgStored: String? = null

  private fun getEpgStored(){
    epgStored = EpgRepository().getEpgList(activity)
  }

  override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
    val title = getString(R.string.settings_epg_title)
    val description = getString(R.string.settings_epg_legend)
    val icon = activity.getDrawable(R.drawable.ic_add_to_queue_white_48dp)
    return GuidanceStylist.Guidance(title, description, "", icon)
  }


  override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
    getEpgStored()

    val urlAction = createUrlAction()
    val saveAction = GuidedAction.Builder(activity).title(getString(R.string.save)).build()
    val cancelAction = GuidedAction.Builder(activity).title(getString(R.string.cancel)).build()


    actions.add(urlAction)
    actions.add(saveAction)
    actions.add(cancelAction)

  }


  private fun createUrlAction(): GuidedAction {
    val urlAction = GuidedAction.Builder(activity)
        .title(getString(R.string.add_epg_url))
        .descriptionEditable(true)

    epgStored?.let { epg ->
      urlAction.description(epg)
    }
    return urlAction.build()
  }



  override fun onGuidedActionClicked(action: GuidedAction?) {

    action?.let { guidedAction ->
      val actionTitle = guidedAction.title

      when (actionTitle) {
        getString(R.string.save) -> { checkFom() }
        getString(R.string.add_epg_url) -> { }
        else -> {
          popBackStackFragment()
        }
      }
    }
  }


  private fun checkFom() {
    if (actions.size > 0) {
      val urlList = actions[0].description

      if (!urlList.isNullOrEmpty()) {
        EpgRepository().saveEpgList(urlList.toString(), activity)
        EpgCoordinator().initGetEpgData(activity)
        popBackStackFragment()
      }
    }
  }
}