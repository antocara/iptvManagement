package com.anp.iptvmanagement.presentations.settings

import android.app.Fragment
import android.os.Bundle
import android.support.v14.preference.PreferenceFragment
import android.support.v17.preference.LeanbackSettingsFragment
import android.support.v7.preference.DialogPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceScreen
import com.anp.iptvmanagement.R
import java.util.Stack


class SettingsFragment : LeanbackSettingsFragment(), DialogPreference.TargetFragment {


  private val fragments = Stack<Fragment>()

  override fun onPreferenceStartInitialScreen() {
    startPreferenceFragment(buildPreferenceFragment(R.xml.settings, null))
  }

  override fun onPreferenceStartScreen(caller: PreferenceFragment?,
      pref: PreferenceScreen?): Boolean {

    val frag = buildPreferenceFragment(R.xml.settings, pref?.key)
    startPreferenceFragment(frag)
    return true
  }

  override fun onPreferenceStartFragment(caller: PreferenceFragment?, pref: Preference?): Boolean {
    return false
  }


  /**
   * Callback @{@link DialogPreference.TargetFragment}
   */
  override fun findPreference(key: CharSequence?): Preference {
    return (fragments.peek() as PreferenceFragment).findPreference(key)
  }


  private fun buildPreferenceFragment(preferenceResId: Int, root: String?): PreferenceFragment {
    val fragment = PreferencesFragment()
    val args = Bundle()
    args.putInt("preferenceResource", preferenceResId)
    args.putString("root", root)
    fragment.arguments = args

    fragment.setFragmentStack(fragments)
    return fragment
  }


}