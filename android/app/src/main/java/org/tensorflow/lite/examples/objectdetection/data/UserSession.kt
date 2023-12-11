package org.tensorflow.lite.examples.objectdetection.data

import android.content.Context

object UserSession {
    private const val PREFS_NAME = "UserSessionPrefs"
    private const val USERNAME = "username"

    fun saveUsername(context: Context, username: String) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(USERNAME, username)
            apply()
        }
    }

    fun getUsername(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USERNAME, null)
    }

    fun clearUsername(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(USERNAME)
            apply()
        }
    }
}
