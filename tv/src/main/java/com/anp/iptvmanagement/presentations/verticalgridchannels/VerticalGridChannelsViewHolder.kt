package com.anp.iptvmanagement.presentations.verticalgridchannels

import android.annotation.SuppressLint
import android.content.Context
import android.support.v17.leanback.widget.Presenter
import android.view.View
import com.anp.commons.data.EpgRepository
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.ProgramEpg
import com.anp.commons.managers.ImageLoader
import com.anp.iptvmanagement.presentations.widgets.EpgImageCardView
import rx.Subscriber


class VerticalGridChannelsViewHolder(private val itemView: View,
    var context: Context?) : Presenter.ViewHolder(itemView) {


  companion object {
    val CARD_WIDTH = 313
    val CARD_HEIGHT = 200
  }

  var channel: Channel? = null
  private val cardView: EpgImageCardView = itemView as EpgImageCardView

  fun bindData(channel: Channel) {

    cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)
    setChannelName(channel)
    ImageLoader().downloadImageInto(channel.logo,
        cardView.mainImageView)

    getEpgData(channel)
  }

  private fun setChannelName(channel: Channel){
    if (channel.logo.isEmpty()){
      cardView.titleText?.text = channel.titleChannel
      cardView.titleText?.visibility = View.VISIBLE
    }else{
      cardView.titleText?.visibility = View.INVISIBLE
    }
  }


  private fun getEpgData(data: Channel) {
    EpgRepository().getProgramsByChannel(data).subscribe(
        object : Subscriber<List<ProgramEpg>>() {
          override fun onError(e: Throwable?) {}

          override fun onCompleted() {}

          override fun onNext(t: List<ProgramEpg>?) {
            t?.let { list ->
              setEpgData(list)
            }
          }
        })
  }

  @SuppressLint("RestrictedApi")
  private fun setEpgData(programList: List<ProgramEpg>) {
    if (programList.isNotEmpty()) {

      cardView.seekBar?.visibility = View.VISIBLE
//
//      cardView.seekBar?.setPadding(10,0,10,0)
      val program = programList.first()
      cardView.seekBar?.max = program.getProgramDuration()
      cardView.seekBar?.progress = program.getProgramCurrentTime()
      cardView.epgProgramTitle?.text = program.title
    } else {
      cardView.seekBar?.visibility = View.INVISIBLE
    }
  }


}