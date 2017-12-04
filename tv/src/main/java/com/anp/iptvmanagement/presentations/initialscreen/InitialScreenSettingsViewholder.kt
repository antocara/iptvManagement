package com.anp.iptvmanagement.presentations.initialscreen

import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.Presenter
import android.view.View
import com.anp.iptvmanagement.data.SettingOption


class InitialScreenSettingsViewholder(private val itemView: View) : Presenter.ViewHolder(itemView) {


  fun bindData(settingOption: SettingOption) {
    val cardView = itemView as? ImageCardView

    cardView?.let { imageCardView ->
      imageCardView.titleText = settingOption.title
      imageCardView.mainImageView.setImageDrawable(
          imageCardView.context.resources.getDrawable(settingOption.iconResource, null))
    }
  }
}