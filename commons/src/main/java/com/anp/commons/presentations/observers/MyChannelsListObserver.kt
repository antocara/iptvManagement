package com.anp.commons.presentations.observers

import com.anp.commons.data.entities.MyChannelsList
import com.anp.commons.presentations.base.BaseView.SourceListView
import rx.Observer

class MyChannelsListObserver(val view: SourceListView?) : Observer<List<MyChannelsList>> {


  override fun onNext(result: List<MyChannelsList>?) {
    result?.let { list -> view?.displaySourceList(list) }
  }

  override fun onError(e: Throwable?) {
    view?.hideLoading()
  }

  override fun onCompleted() {
    view?.hideLoading()
  }
}