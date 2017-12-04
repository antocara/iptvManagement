package com.anp.commons.data.entities

import com.anp.commons.helpers.HelperDate


class ProgramEpg {


  var channelId: String = ""
  var title: String? = null
  var description: String? = null
  var posterArtUri: String? = null
  var canonicalGenres: List<String>? = null
  var startTimeUtcMillis: Long = 0
  var endTimeUtcMillis: Long = 0
  var duration: Long = 0
  var currentTimeProgram: Long = 0

  fun startTimeUtcMillis(): String {
    return HelperDate.parseLongTimeToDate(startTimeUtcMillis)
  }

  fun endTimeUtcMillis(): String {
    return HelperDate.parseLongTimeToDate(endTimeUtcMillis)
  }

  fun getProgramDuration(): Int {
    val durationMilliseconds = (endTimeUtcMillis - startTimeUtcMillis)
    return HelperDate.millisecondsToMinutes(durationMilliseconds)
  }

  fun getProgramCurrentTime(): Int {
    val current = System.currentTimeMillis()
    val timePlayed = (current - startTimeUtcMillis)
    return HelperDate.millisecondsToMinutes(timePlayed)
  }

}