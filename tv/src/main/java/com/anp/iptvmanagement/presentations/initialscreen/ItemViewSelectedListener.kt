package com.anp.iptvmanagement.presentations.initialscreen

import android.support.v17.leanback.widget.OnItemViewSelectedListener
import android.support.v17.leanback.widget.Presenter.ViewHolder
import android.support.v17.leanback.widget.Row
import android.support.v17.leanback.widget.RowPresenter
import com.anp.commons.data.entities.Channel
import com.anp.iptvmanagement.manager.BackgroundManager


class ItemViewSelectedListener(
    private var backgroundManager: BackgroundManager?) : OnItemViewSelectedListener {


  override fun onItemSelected(itemViewHolder: ViewHolder?, item: Any?,
      rowViewHolder: RowPresenter.ViewHolder?, row: Row?) {

    if (item is Channel) {
      backgroundManager?.startBackgroundTimer(item.logo)
    }
  }

}