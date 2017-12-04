package com.anp.iptvmanagement.manager

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.v17.leanback.app.BackgroundManager
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import com.anp.commons.managers.GlideApp
import com.anp.iptvmanagement.R.drawable
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.util.Timer
import java.util.TimerTask


class BackgroundManager(private var activity: Activity) {

  private lateinit var mBackgroundManager: BackgroundManager
  private lateinit var mDefaultBackground: Drawable
  private lateinit var mMetrics: DisplayMetrics
  private var mBackgroundTimer: Timer? = null
  private var mBackgroundUri: String? = null
  private val mHandler = Handler()

  companion object {
    val BACKGROUND_UPDATE_DELAY = 300
  }

  init {
    initializeBackgroundManager()
  }

  private fun initializeBackgroundManager() {

    mBackgroundManager = BackgroundManager.getInstance(activity)
    mBackgroundManager.attach(activity.window)
    mDefaultBackground = ContextCompat.getDrawable(activity,
        drawable.default_background)
    mMetrics = DisplayMetrics()
    activity.windowManager.defaultDisplay.getMetrics(mMetrics)
  }


  fun stop() {
    mBackgroundTimer?.cancel()
  }


  fun startBackgroundTimer(backgroundUri: String?) {
    mBackgroundUri = backgroundUri
    mBackgroundTimer?.cancel()
    mBackgroundTimer = Timer()
    mBackgroundTimer?.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
  }


  private inner class UpdateBackgroundTask : TimerTask() {

    override fun run() {
      mHandler.post { updateBackground(mBackgroundUri) }
    }
  }


  private fun updateBackground(uri: String?) {
    val width = mMetrics.widthPixels
    val height = mMetrics.heightPixels

    GlideApp.with(activity).load(uri).centerCrop().error(
        mDefaultBackground).into<SimpleTarget<Drawable>>(
        object : SimpleTarget<Drawable>(width, height) {
          override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
            mBackgroundManager.drawable = resource
          }
        })


    mBackgroundTimer?.cancel()
  }

}