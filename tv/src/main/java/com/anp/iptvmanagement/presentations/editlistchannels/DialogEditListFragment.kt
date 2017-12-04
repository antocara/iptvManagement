/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.anp.iptvmanagement.presentations.editlistchannels

import android.content.Intent
import android.os.Bundle
import android.support.v17.leanback.app.GuidedStepFragment
import android.support.v17.leanback.widget.GuidanceStylist.Guidance
import android.support.v17.leanback.widget.GuidedAction
import android.widget.Toast

class DialogEditListFragment : GuidedStepFragment() {


  override fun onCreateGuidance(savedInstanceState: Bundle?): Guidance {
    return Guidance("TITLE", "DESCRIPTION", "", null)
  }

  override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {

    val actionEdit = GuidedAction.Builder(activity).id(ACTION_ID_EDIT.toLong()).title(
        "EDIT ").build()
    actions.add(actionEdit)

    val actionDelete = GuidedAction.Builder(activity).id(ACTION_ID_DELETE.toLong()).title(
        "DELETE ").build()
    actions.add(actionDelete)

    val actionCancel = GuidedAction.Builder(activity).id(ACTION_ID_CANCEL.toLong()).title(
        "CANCEL").build()
    actions.add(actionCancel)
  }

  override fun onGuidedActionClicked(action: GuidedAction?) {
    action?.let { guidedAction ->
      when {
        ACTION_ID_EDIT.toLong() == guidedAction.id -> openEditChannel()
        ACTION_ID_DELETE.toLong() == guidedAction.id -> Toast.makeText(activity, "DELETE",
            Toast.LENGTH_SHORT).show()

        else -> {
          Toast.makeText(activity, "CANCEL", Toast.LENGTH_SHORT).show()
          activity.finish()
        }
      }
    }
  }

  private fun openEditChannel(){
    val intent = Intent(activity, EditChannel::class.java)
    activity.startActivity(intent)
  }

  companion object {
    private val ACTION_ID_EDIT = 0
    private val ACTION_ID_DELETE = 1
    private val ACTION_ID_CANCEL = 2
  }
}
