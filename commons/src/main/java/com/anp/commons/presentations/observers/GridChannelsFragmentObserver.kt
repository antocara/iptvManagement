package com.anp.commons.presentations.observers

import com.anp.commons.data.entities.Channel
import com.anp.commons.presentations.base.BaseView.GridChannelsView
import rx.Observer


class GridChannelsFragmentObserver(val view: GridChannelsView?) : Observer<List<Channel>> {


  override fun onNext(result: List<Channel>?) {
    result?.let { list -> view?.displayChannels(list) }
  }

  override fun onError(e: Throwable?) {
    view?.hideLoading()
  }

  override fun onCompleted() {
    view?.hideLoading()
  }
}