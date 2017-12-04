package com.anp.commons.data

import android.content.Context
import com.anp.commons.R
import com.anp.commons.data.entities.Player
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException


object PreferencesStorage {

  private val PREFERENCES_NAME = "iptc_management"
  private val PLAYER_KEY = "player_key"
  private val EPG_URL_KEY = "epg_url_key"
  private val WIZARD_KEY = "wizard_key"


  fun savePlayerSettings(player: Player, context: Context) {
    val gson = Gson().toJson(player)
    getSharedPreferences(context).edit().putString(PLAYER_KEY, gson).apply()
  }

  fun getPlayerSettings(context: Context): Player {
    val playerSaved = getSharedPreferences(context).getString(
        PLAYER_KEY, null)
    val playerName = context.getString(R.string.player_vlc)
    val playerPackageName = context.getString(R.string.package_name_vlc)

    if (playerSaved != null) {
      return try {
        Gson().fromJson<Player>(playerSaved, Player::class.java)
      } catch (ex: JsonSyntaxException) {
        Player(playerName, playerPackageName)
      }

    }
    return Player(playerName, playerPackageName)
  }

  fun deleteSettings(context: Context) {
    getSharedPreferences(context)?.edit()?.clear()?.apply()
  }


  /** epg **/
  fun saveEpgUrl(epgUrl: String, context: Context) {
    getSharedPreferences(context).edit().putString(EPG_URL_KEY, epgUrl).apply()
  }

  fun getEpgUrl(context: Context)
      = getSharedPreferences(context).getString(EPG_URL_KEY, "")


  fun deleteEpgData(context: Context) {
    getSharedPreferences(context).edit().remove(EPG_URL_KEY).apply()
  }

  /** wizard **/
  fun saveWizardDisplay(context: Context) {
    getSharedPreferences(context)
        .edit()
        .putBoolean(WIZARD_KEY, true)
        .apply()

  }

  fun isWizardDisplay(context: Context) = getSharedPreferences(context).getBoolean(WIZARD_KEY,
      false)


  private fun getSharedPreferences(
      context: Context) = context.getSharedPreferences(PREFERENCES_NAME,
      Context.MODE_PRIVATE)


}