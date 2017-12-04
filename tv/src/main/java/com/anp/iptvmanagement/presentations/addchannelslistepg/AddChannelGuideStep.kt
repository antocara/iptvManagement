package com.anp.iptvmanagement.presentations.addchannelslistepg

import android.os.Bundle
import android.support.v17.leanback.widget.GuidanceStylist
import android.support.v17.leanback.widget.GuidedAction
import android.util.Log
import com.anp.commons.data.AddChannelsListRepository
import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.data.responses.BaseResponse
import com.anp.commons.data.responses.ResponseParseChannelsList
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.presentations.base.BaseGuideStep
import rx.Observer


class AddChannelGuideStep : BaseGuideStep() {

  private var myChannelsList: MyChannelsList? = null

  override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
    val title = getString(R.string.settings_add_channel_title)
    val description = getString(R.string.settings_add_channel_legend)
    val icon = activity.getDrawable(R.drawable.ic_cloud_download_white_48dp)
    return GuidanceStylist.Guidance(title, description, "", icon)
  }

  override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {

    val nameAction = createNameAction()
    val urlAction = createUrlAction()
    val saveAction = GuidedAction.Builder(activity).title(getString(R.string.save)).build()
    val cancelAction = GuidedAction.Builder(activity).title(getString(R.string.cancel)).build()

    actions.add(nameAction)
    actions.add(urlAction)
    actions.add(saveAction)
    actions.add(cancelAction)

  }

  private fun createNameAction(): GuidedAction {
    val nameAction = GuidedAction.Builder(activity)
        .title(getString(R.string.add_channel_name))
        .descriptionEditable(true)
    myChannelsList?.let { myChannelsList ->
      nameAction.description(myChannelsList.name)
    }

    return nameAction.build()
  }

  private fun createUrlAction(): GuidedAction {
    val urlAction = GuidedAction.Builder(activity)
        .title(getString(R.string.add_channel_url))
        .descriptionEditable(true)

    myChannelsList?.let { myChannelsList ->
      urlAction.description(myChannelsList.url)
    }
    return urlAction.build()
  }

  fun refreshActions(myChannelsList: MyChannelsList) {
    this.myChannelsList = myChannelsList
  }

  override fun onGuidedActionClicked(action: GuidedAction?) {

    action?.let { guidedAction ->
      val actionTitle = guidedAction.title

      when (actionTitle) {
        getString(R.string.save) -> {
          checkFom()
        }
        getString(R.string.add_channel_name) -> {
        }
        getString(R.string.add_channel_url) -> {
        }
        else -> {
          popBackStackFragment()
        }
      }
    }
  }

  private fun checkFom() {
    if (actions.size > 0) {
      val nameList = actions[0].description
      val urlList = actions[1].description

      if (!nameList.isNullOrEmpty()
          && !urlList.isNullOrEmpty()) {

        val channelList = MyChannelsList(getMyChannelListId(), nameList.toString(),
            urlList.toString())
        saveFavoriteList(channelList)
      }
    }
  }

  private fun getMyChannelListId(): String {
    myChannelsList?.let { myChannelsList ->
      return myChannelsList.id
    }
    return MyChannelsList.getUniqueId()
  }


  private fun saveFavoriteList(myChannelsList: MyChannelsList) {
    AddChannelsListRepository().saveChannelsList(myChannelsList, activity).subscribe(
        object : Observer<BaseResponse> {
          override fun onNext(t: BaseResponse?) {

            (t as? ResponseParseChannelsList)?.playList?.playlistEntries
            Log.d("", "")
          }

          override fun onError(e: Throwable?) {
            Log.d("", "")
          }

          override fun onCompleted() {
            Log.d("", "")
          }
        })
    popBackStackFragment()
  }


}