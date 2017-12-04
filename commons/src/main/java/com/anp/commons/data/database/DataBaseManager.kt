package com.anp.commons.data.database

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration


object DataBaseManager {

  val DATABASE_NAME = "iptv_management"

  fun initialize(context: Context) {
    Realm.init(context)
    configRealm()
  }

  private fun configRealm() {
//    var config = RealmConfiguration.Builder()
//        .name("myrealm.realm")
//        .schemaVersion(1)
//        .modules(MySchemaModule())
//        .migration(MyMigration())
//        .build()


//    val config = RealmConfiguration.Builder()
//        .name(DATABASE_NAME)
//        .schemaVersion(1)
//        .build()
    //FIXME only development
    val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
    Realm.setDefaultConfiguration(config)
  }

}