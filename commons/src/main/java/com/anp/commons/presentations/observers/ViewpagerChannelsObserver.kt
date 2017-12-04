package com.anp.commons.presentations.observers

import com.anp.commons.data.entities.Channel
import com.anp.commons.presentations.base.BaseView.ViewpagerChannelsView
import rx.Observer


class ViewpagerChannelsObserver (val view: ViewpagerChannelsView?) : Observer<List<Channel>> {


  override fun onNext(result: List<Channel>?) {
    result?.let { list -> view?.displayGroups(list) }
  }

  override fun onError(e: Throwable?) {
    view?.hideLoading()
  }

  override fun onCompleted() {
    view?.hideLoading()
  }
}