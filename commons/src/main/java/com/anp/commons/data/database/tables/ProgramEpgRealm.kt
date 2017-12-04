package com.anp.commons.data.database.tables

import com.anp.commons.data.entities.ProgramEpg
import io.realm.RealmList
import io.realm.RealmObject


open class ProgramEpgRealm : RealmObject() {

  var channelId: String = ""
  var title: String? = null
  var description: String? = null
  var posterArtUri: String? = null
  open var canonicalGenres: RealmList<String>? = null
  var startTimeUtcMillis: Long = 0
  var endTimeUtcMillis: Long = 0
  var internalProviderData: ByteArray? = null


  companion object {
    val CHANNEL_ID_FIELD_NAME = "channelId"
    val START_TIME_FIELD_NAME = "startTimeUtcMillis"
    val END_TIME_FIELD_NAME = "endTimeUtcMillis"

    fun toListProgram(programsEpgRealm: RealmList<ProgramEpgRealm>?): List<ProgramEpg> {

      val programList = arrayListOf<ProgramEpg>()
      programsEpgRealm?.map { programEpgRealm ->
        programList.add(toProgram(programEpgRealm))
      }
      return programList
    }

    fun toListProgramEpgRealm(programsEpg: List<ProgramEpg>?): RealmList<ProgramEpgRealm> {

      val programList = RealmList<ProgramEpgRealm>()
      programsEpg?.map { programEpg ->
        programList.add(toProgramRealm(programEpg))
      }
      return programList
    }

    fun toProgram(programEpgRealm: ProgramEpgRealm): ProgramEpg {
      return ProgramEpg().apply {
        channelId = programEpgRealm.channelId
        title = programEpgRealm.title
        description = programEpgRealm.description
        posterArtUri = programEpgRealm.posterArtUri
        canonicalGenres = programEpgRealm.canonicalGenres?.toList()
        startTimeUtcMillis = programEpgRealm.startTimeUtcMillis
        endTimeUtcMillis = programEpgRealm.endTimeUtcMillis
      }

    }

    fun toProgramRealm(programEpg: ProgramEpg): ProgramEpgRealm {
      return ProgramEpgRealm().apply {
        channelId = programEpg.channelId
        title = programEpg.title
        description = programEpg.description
        posterArtUri = programEpg.posterArtUri
        startTimeUtcMillis = programEpg.startTimeUtcMillis
        endTimeUtcMillis = programEpg.endTimeUtcMillis
      }
    }
  }

}