package com.anp.iptvmanagement.presentations.initialscreen

import android.app.Activity
import android.graphics.Color
import android.support.v17.leanback.widget.Presenter
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.anp.commons.data.MyChannelsListRepository
import com.anp.commons.data.entities.Channel
import com.anp.commons.presentations.base.BaseView
import com.anp.commons.presentations.base.BaseView.SourceListView
import com.anp.commons.presentations.observers.MyChannelsListObserver
import com.anp.iptvmanagement.R.color


class InitialScreenPresenter(private var context: Activity,
    private var repository: MyChannelsListRepository,
    var view: BaseView) : Presenter() {

  companion object {
    private val GRID_ITEM_WIDTH = 200
    private val GRID_ITEM_HEIGHT = 200
  }


  override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
    val view = TextView(parent.context)
    view.layoutParams = ViewGroup.LayoutParams(InitialScreenPresenter.GRID_ITEM_WIDTH,
        InitialScreenPresenter.GRID_ITEM_HEIGHT)
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.setBackgroundColor(ContextCompat.getColor(context, color.default_background))
    view.setTextColor(Color.WHITE)
    view.gravity = Gravity.CENTER
    return Presenter.ViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
    var channelSelected = item as? Channel
    channelSelected?.let { channel ->
      (viewHolder.view as TextView).text = channel.group
    }

    //todo
    var title = item as? String
    title?.let { s ->
      (viewHolder.view as TextView).text = s
    }
  }

  override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {}


  fun getListWithGroups() {
    view.showLoading()
    repository.getListWithGroups().subscribe(
        MyChannelsListObserver(view as SourceListView))
  }
}