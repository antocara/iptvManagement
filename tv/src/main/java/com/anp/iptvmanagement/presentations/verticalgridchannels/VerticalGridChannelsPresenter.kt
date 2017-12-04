package com.anp.iptvmanagement.presentations.verticalgridchannels

import android.support.v17.leanback.widget.Presenter
import android.view.ViewGroup
import com.anp.commons.data.ChannelsRepository
import com.anp.commons.data.entities.Channel
import com.anp.commons.presentations.base.BaseView
import com.anp.commons.presentations.base.BaseView.GridChannelsView
import com.anp.commons.presentations.observers.GridChannelsFragmentObserver
import com.anp.iptvmanagement.presentations.widgets.EpgImageCardView


class VerticalGridChannelsPresenter(var view: BaseView,
    private var repository: ChannelsRepository) : Presenter() {


  override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
    val cardView = EpgImageCardView(parent.context)

    return VerticalGridChannelsViewHolder(cardView, parent.context)
  }

  override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {

    val channelSelected = item as? Channel
    val viewholderVerticalGrid = viewHolder as? VerticalGridChannelsViewHolder

    viewholderVerticalGrid?.let { verticalGridChannelsViewHolder ->
      verticalGridChannelsViewHolder.channel = channelSelected
      channelSelected?.let { channel ->
        verticalGridChannelsViewHolder.bindData(channel)
      }
    }
  }


  override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {}


  fun getChannelsByGroup(channel: Channel) {
    view.showLoading()
    repository.getChannelsByGroup(channel).subscribe(
        GridChannelsFragmentObserver(
            view as GridChannelsView))
  }
}