package fr.pcohen.app.services

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import javax.inject.Inject

class SharedPreferencesService @Inject constructor(context: Context) {

    companion object {
        private const val AUTH_TOKEN = "AUTH_TOKEN"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    internal var token: String?
        get() = prefs.getString(AUTH_TOKEN, null)
        set(value) = prefs.edit { putString(AUTH_TOKEN, value) }

}