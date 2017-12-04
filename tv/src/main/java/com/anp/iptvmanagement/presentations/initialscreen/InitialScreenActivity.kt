package com.anp.iptvmanagement.presentations.initialscreen

import android.app.Activity
import android.os.Bundle
import com.anp.iptvmanagement.R.layout

/**
 * Loads [InitialScreenFragment].
 */
class InitialScreenActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_initial_screen)


  }
}
