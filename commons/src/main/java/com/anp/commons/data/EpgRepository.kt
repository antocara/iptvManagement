package com.anp.commons.data

import android.content.Context
import com.anp.commons.data.database.daos.ChannelsEpgDao
import com.anp.commons.data.database.daos.ProgramEpgDao
import com.anp.commons.data.database.tables.ChannelEpgRealm
import com.anp.commons.data.database.tables.ProgramEpgRealm
import com.anp.commons.data.entities.Channel
import com.anp.commons.data.entities.ProgramEpg
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers


class EpgRepository {


  fun saveListChannelsEpg(channelsEpg: List<ChannelEpgRealm>) {
    ChannelsEpgDao().saveListChannels(channelsEpg)
  }

  fun saveListProgramEpg(program: List<ProgramEpgRealm>) {
    ProgramEpgDao().saveListProgram(program)
  }

  private fun getProgramsFromNowByChannel(channel: Channel): List<ProgramEpg> {
    return ProgramEpgDao().getProgramsFromNowByChannel(channel)
  }


  fun getProgramsByChannel(channel: Channel): Observable<List<ProgramEpg>> {

    return Observable.unsafeCreate({ t: Subscriber<in List<ProgramEpg>>? ->
      Thread(Runnable {
        val programs = getProgramsFromNowByChannel(channel)
        t?.onNext(programs)
        t?.onCompleted()
      }).start()
    }).observeOn(AndroidSchedulers.mainThread())

  }

  fun saveEpgList(epgUrl: String, context: Context) {
    PreferencesStorage.saveEpgUrl(epgUrl, context)
  }

  fun getEpgList(context: Context): String = PreferencesStorage.getEpgUrl(context)


  fun deleteEpgAndPrograms(context: Context) {
    ProgramEpgDao().deleteAllProgram()
    PreferencesStorage.deleteEpgData(context)
  }
}