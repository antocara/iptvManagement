package com.anp.iptvmanagement.presentations.addchannelslistepg

import android.os.Bundle
import android.support.v17.leanback.widget.GuidanceStylist
import android.support.v17.leanback.widget.GuidedAction
import com.anp.commons.data.MyChannelsListRepository
import com.anp.commons.data.entities.MyChannelsList
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.presentations.base.BaseGuideStep
import rx.Observer


open class EditChannelGuideStep : BaseGuideStep() {

  protected var channelsLists: List<MyChannelsList>? = null


  protected fun getChannelsLists() {
    MyChannelsListRepository().getAllMyChannelsList().subscribe(
        object : Observer<List<MyChannelsList>> {
          override fun onNext(t: List<MyChannelsList>?) {
            channelsLists = t
            actions = createActions()
          }

          override fun onError(e: Throwable?) {}

          override fun onCompleted() {

          }
        })
  }

  private fun createActions(): MutableList<GuidedAction> {
    val actions = arrayListOf<GuidedAction>()

    var counter = 0

    channelsLists?.map { myChannelsList ->
      val listAction = GuidedAction.Builder(activity).title(
          myChannelsList.name).build()
      listAction.id = counter.toLong()
      actions.add(listAction)
      counter += 1
    }

    return actions
  }


  override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
    val title = getString(R.string.settings_add_channel_title)//todo
    val description = getString(R.string.settings_add_channel_legend)
    val icon = activity.getDrawable(R.drawable.ic_cloud_download_white_48dp)
    return GuidanceStylist.Guidance(title, description, "", icon)
  }

  override fun onGuidedActionClicked(action: GuidedAction?) {

    action?.let { guidedAction ->
      val position = guidedAction.id

      channelsLists?.let { list ->
        val channelList = list[position.toInt()]
        openAddChannel(channelList)
      }

    }
  }

  private fun openAddChannel(channelList: MyChannelsList) {
    val fragment = AddChannelGuideStep()
    fragment.refreshActions(channelList)
    add(fragmentManager, fragment)
  }

  override fun onResume() {
    super.onResume()
    getChannelsLists()
  }
}