package com.anp.commons.helpers

import android.app.Activity
import com.anp.commons.R


object HelperTablets {


  fun isTablet(activity: Activity) = activity.resources.getBoolean(R.bool.is_tablet)


}