package com.anp.commons.helpers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object HelperDate {

  private val FORMATTER_MM_dd_yyyy_HH_mm = "MM/dd/yyyy HH:mm"
  private val FORMATTER_EPG_TIME = "HH:mm"

  fun parseLongTimeToDate(longDateToParse: Long): String {
    return SimpleDateFormat(FORMATTER_EPG_TIME, Locale.getDefault())
        .format(Date(longDateToParse))
  }

  fun millisecondsToSeconds(milliseconds: Long): Int {
    val seconds = (milliseconds / 1000) % 60
    return seconds.toInt()
  }

  fun millisecondsToMinutes(milliseconds: Long): Int {
    val minutes = milliseconds / 1000 / 60
    return minutes.toInt()
  }

}