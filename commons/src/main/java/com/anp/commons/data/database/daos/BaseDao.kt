package com.anp.commons.data.database.daos

import io.realm.Realm


open class BaseDao {

  protected fun closeRealmInstance(realmInstance: Realm) {
    realmInstance.close()
  }

  protected fun getRealmInstance(): Realm {
    return Realm.getDefaultInstance()
  }
}