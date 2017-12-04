package com.anp.commons.managers

import android.widget.ImageView
import com.anp.commons.R

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class ImageLoader : AppGlideModule() {

  fun downloadImageInto(urlImage: String?, imageView: ImageView?) {
    if (imageView == null){
      return
    }
    GlideApp
        .with(imageView.context)
        .load(urlImage ?: "")
        .placeholder(R.drawable.ic_placeholder)
        .into(imageView)
  }

  fun downloadImageIntoAndCenterCrop(urlImage: String?, imageView: ImageView){
    GlideApp
        .with(imageView.context)
        .load(urlImage ?: "")
        .centerCrop()
        .placeholder(R.drawable.ic_placeholder)
        .into(imageView)
  }

}