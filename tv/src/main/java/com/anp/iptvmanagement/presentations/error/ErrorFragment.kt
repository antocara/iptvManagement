/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.anp.iptvmanagement.presentations.error

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.anp.iptvmanagement.R.drawable
import com.anp.iptvmanagement.R.string

/**
 * This class demonstrates how to extend [android.support.v17.leanback.app.ErrorFragment].
 */
class ErrorFragment : android.support.v17.leanback.app.ErrorFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = resources.getString(string.app_name)
  }

  internal fun setErrorContent() {
    imageDrawable = ContextCompat.getDrawable(activity,
        drawable.lb_ic_sad_cloud)
    message = resources.getString(string.error_fragment_message)
    setDefaultBackground(
        TRANSLUCENT)

    buttonText = resources.getString(string.dismiss_error)
    buttonClickListener = View.OnClickListener {
      fragmentManager.beginTransaction().remove(this@ErrorFragment).commit()
    }
  }

  companion object {
    private val TRANSLUCENT = true
  }
}