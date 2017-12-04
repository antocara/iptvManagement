package com.anp.iptvmanagement.presentations.addchannelslistepg

import android.support.v17.leanback.widget.GuidedAction
import com.anp.commons.data.MyChannelsListRepository
import com.anp.commons.data.entities.MyChannelsList


class DeleteChannelGuideStep: EditChannelGuideStep() {



  override fun onGuidedActionClicked(action: GuidedAction?) {

    action?.let { guidedAction ->
      val position = guidedAction.id

      channelsLists?.let { list ->
        val channelList = list[position.toInt()]
        deleteListChannel(channelList)
      }
    }
  }

  private fun deleteListChannel(channelList: MyChannelsList){
    MyChannelsListRepository().deleteMyChannelList(channelList)
    getChannelsLists()
  }


}