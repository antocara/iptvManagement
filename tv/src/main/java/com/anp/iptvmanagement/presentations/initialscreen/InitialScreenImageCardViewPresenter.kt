package com.anp.iptvmanagement.presentations.initialscreen

import android.support.v17.leanback.widget.ImageCardView
import android.support.v17.leanback.widget.Presenter
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import com.anp.iptvmanagement.R
import com.anp.iptvmanagement.data.SettingOption



class InitialScreenImageCardViewPresenter : Presenter() {


  override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
    val imageCardView = ImageCardView(ContextThemeWrapper(parent?.context, R.style.IconCardTheme))
    return InitialScreenSettingsViewholder(imageCardView)
  }

  override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {

    var settingOptions = item as? SettingOption
    val optionViewHolder = viewHolder as? InitialScreenSettingsViewholder

    settingOptions?.let { settingOption ->

      optionViewHolder?.let { initialScreenSettingsViewholder ->
        initialScreenSettingsViewholder.bindData(settingOption)
      }

    }
  }

  override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

  }
}