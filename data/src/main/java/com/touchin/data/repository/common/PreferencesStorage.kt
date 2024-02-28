package com.touchin.data.repository.common

import androidx.appcompat.app.AppCompatDelegate
import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.PreferencesEditor

@Suppress("TooManyFunctions")
class PreferencesStorage(private val preferences: Preferences) {

    private companion object {
        const val NIGHT_MODE = "NIGHT_MODE"
        const val FAVORITE_OFFERS = "FAVORITE_OFFERS"
        const val EMAIL = "EMAIL"
        const val SUBJECT = "SUBJECT"
        const val BODY = "BODY"
    }

    var nightMode: Int
        set(value) = preferences.modify { putInt(NIGHT_MODE, value) }
        get() = preferences.getInt(NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    var favoriteSet: Set<String>
        set(value) = preferences.modify { putStringSet(FAVORITE_OFFERS, value) }
        get() = preferences.getStringSet(FAVORITE_OFFERS, emptySet()).orEmpty()

    var email: String
        set(value) = preferences.modify { putString(EMAIL, value) }
        get() = preferences.getString(EMAIL, "").orEmpty()

    var subject: String
        set(value) = preferences.modify { putString(SUBJECT, value) }
        get() = preferences.getString(SUBJECT, "").orEmpty()

    var body: String
        set(value) = preferences.modify { putString(BODY, value) }
        get() = preferences.getString(BODY, "").orEmpty()

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
