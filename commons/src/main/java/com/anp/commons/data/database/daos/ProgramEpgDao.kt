package com.anp.commons.data.database.daos

import com.anp.commons.data.database.tables.ProgramEpgRealm
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.ProgramEpg


class ProgramEpgDao : BaseDao() {

  fun saveProgram(program: ProgramEpgRealm) {
    getRealmInstance().executeTransaction { realm ->
      realm.insert(program)
    }
  }

  fun saveListProgram(programs: List<ProgramEpgRealm>) {
    getRealmInstance().executeTransaction { realm ->
      realm.insert(programs)
    }
  }

  fun getProgramsFromNowByChannel(channel: Channel): List<ProgramEpg> {
    val result = arrayListOf<ProgramEpg>()

    val realmInstance = getRealmInstance()
    realmInstance.executeTransaction { realm ->
      val currentTime = System.currentTimeMillis()
      val resultQuery = realm.where(ProgramEpgRealm::class.java)
          .equalTo(ProgramEpgRealm.CHANNEL_ID_FIELD_NAME, channel.id)
          .lessThan(ProgramEpgRealm.START_TIME_FIELD_NAME, currentTime)
          .greaterThan(ProgramEpgRealm.END_TIME_FIELD_NAME, currentTime)
          .findFirst()

      resultQuery?.let { programEpgRealm ->
        val program = ProgramEpgRealm.toProgram(programEpgRealm)
        result.add(program)
      }

    }
    closeRealmInstance(realmInstance)
    return result

  }

  fun deleteAllProgram(){
    val realmInstance = getRealmInstance()
    getRealmInstance().executeTransaction { realm ->
      val resultQuery = realm.where(ProgramEpgRealm::class.java).findAll()
      resultQuery.deleteAllFromRealm()
    }
    closeRealmInstance(realmInstance)
  }

}