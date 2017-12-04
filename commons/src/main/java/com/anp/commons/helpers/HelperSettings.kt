package com.anp.commons.helpers

import android.app.Activity
import com.anp.commons.R
import com.anp.commons.data.entities.Player


object HelperSettings {

   fun createPlayers(activity: Activity): ArrayList<Player> {
     val players = arrayListOf<Player>()

    val playerVlcName = activity.getString(R.string.player_vlc)
    val playerVlcPackageName = activity.getString(R.string.package_name_vlc)
    players.add(Player(playerVlcName, playerVlcPackageName))

    val playerMxProName = activity.getString(R.string.player_mx)
    val playerMxProPackageName = activity.getString(R.string.package_name_mx_pro)
    players.add(Player(playerMxProName, playerMxProPackageName))

    val playerMxName = activity.getString(R.string.player_mx_free)
    val playerMxPackageName = activity.getString(R.string.package_name_mx_ad)
    players.add(Player(playerMxName, playerMxPackageName))

    return players
  }

}

