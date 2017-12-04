package com.anp.iptvmanagement.presentations.settings

import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.support.v17.preference.LeanbackPreferenceFragment
import android.support.v7.preference.ListPreference
import com.anp.commons.data.PreferencesStorage
import com.anp.commons.data.entities.Player
import com.anp.commons.helpers.HelperApp

import com.anp.commons.helpers.HelperSettings
import com.anp.iptvmanagement.R
import java.util.Stack


class PreferencesFragment : LeanbackPreferenceFragment(), OnSharedPreferenceChangeListener {

  private var fragments: Stack<Fragment>? = null
  private var players : ArrayList<Player>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    players = HelperSettings.createPlayers(activity)
    setVersionApp()

    setPlayer()
    openAcknowledgment()
  }

  private fun setVersionApp() {
    val myPref = findPreference(getString(R.string.key_version_app))
    myPref.summary = HelperApp.getVersionInfo(activity).versionName
  }

  private fun openAcknowledgment() {
    val myPref = findPreference(getString(R.string.key_acknowledgment))
    myPref.setOnPreferenceClickListener { openDialogAcknowledgment() }
  }

  private fun openDialogAcknowledgment(): Boolean {
    //HelperDialogs.displayDialogAcknowledgment(activity)
    return false
  }

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    val root = arguments.getString("root", null)

    val prefResId = arguments.getInt("preferenceResource")

    if (root == null) {
      addPreferencesFromResource(prefResId)
    } else {
      setPreferencesFromResource(prefResId, root)
    }
  }

//  override fun onPreferenceTreeClick(preference: Preference?): Boolean {
//
//    val keys = arrayOf("prefs_wifi_connect_wps", "prefs_date", "prefs_time",
//        "prefs_date_time_use_timezone", "app_banner_sample_app", "pref_force_stop",
//        "pref_uninstall", "pref_more_info")
//    if (Arrays.asList(*keys).contains(preference?.key)) {
//      Toast.makeText(activity, "Implement your own action handler.", Toast.LENGTH_SHORT).show()
//      return true
//    }
//
//
//    return super.onPreferenceTreeClick(preference)
//  }

  override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences,
      key: String) {
    if (key == getString(R.string.key_player)) {
      savePlayerSelected()
    }
  }

  private fun savePlayerSelected() {
    val myPref = findPreference(getString(R.string.key_player)) as ListPreference
    val entryName = myPref.entry

    val playerSelected = players?.filter { player -> player.name == entryName }

    playerSelected?.let { list ->
      if (list.isNotEmpty()){
        PreferencesStorage.savePlayerSettings(list[0], activity)
      }
    }

    setPlayer()
  }

  private fun setPlayer() {
    val selectedPlayer = PreferencesStorage.getPlayerSettings(activity)
    val myPref = findPreference(getString(R.string.key_player)) as ListPreference
    myPref.summary = selectedPlayer.name
  }

  fun setFragmentStack(fragments: Stack<Fragment>) {
    this.fragments = fragments
  }

  override fun onAttach(context: Context?) {
    fragments?.push(this)
    super.onAttach(context)
  }


  override fun onDetach() {
    fragments?.let { stack ->
      if (stack.isNotEmpty()){
        fragments?.pop()
      }
    }

    preferenceScreen.sharedPreferences
        .unregisterOnSharedPreferenceChangeListener(this)
    super.onDetach()
  }

  override fun onResume() {
    super.onResume()
    preferenceScreen.sharedPreferences
        .registerOnSharedPreferenceChangeListener(this)
  }

}
