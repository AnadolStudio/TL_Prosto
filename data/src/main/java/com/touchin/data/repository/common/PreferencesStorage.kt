package com.touchin.data.repository.common

import androidx.appcompat.app.AppCompatDelegate
import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.PreferencesEditor

@Suppress("TooManyFunctions")
class PreferencesStorage(private val preferences: Preferences) {

    private companion object {
        const val NIGHT_MODE = "NIGHT_MODE"
    }

    var nightMode: Int
        set(value) = preferences.modify { putInt(NIGHT_MODE, value) }
        get() = preferences.getInt(NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    private inline fun Preferences.modify(
        commit: Boolean = false,
        action: PreferencesEditor.() -> Unit
    ) {
        with(edit()) {
            action(this)
            if (commit) commit() else apply()
        }
    }
}
