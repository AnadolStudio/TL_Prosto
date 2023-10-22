package com.touchin.data.repository.common

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import com.touchin.domain.repository.common.NightModeRepository
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor

class NightModeRepositoryImpl(
    private val resources: Resources,
    private val preferencesStorage: PreferencesStorage
) : NightModeRepository {

    private val nightModeChanges: BehaviorProcessor<Int> = BehaviorProcessor.create()

    override var nightMode: Int
        get() = preferencesStorage.nightMode
        set(value) {
            preferencesStorage.nightMode = value
            nightModeChanges.onNext(value)
        }

    override fun toggleNightMode() {
        val mode = when (getCurrentNightMode(resources) == AppCompatDelegate.MODE_NIGHT_NO) {
            true -> AppCompatDelegate.MODE_NIGHT_YES
            false -> AppCompatDelegate.MODE_NIGHT_NO
        }

        nightMode = mode
    }

    override fun observeNightModeChanges(): Observable<Int> = nightModeChanges.toObservable()

    private fun getCurrentNightMode(resources: Resources): Int = getCurrentNightMode(
        resources, AppCompatDelegate.getDefaultNightMode()
    )

    private fun getCurrentNightMode(resources: Resources, systemNightMode: Int): Int =
        when (systemNightMode) {
            AppCompatDelegate.MODE_NIGHT_NO, AppCompatDelegate.MODE_NIGHT_YES -> systemNightMode
            else -> getNightModeFromSystem(resources)
        }

    private fun getNightModeFromSystem(resources: Resources): Int =
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_YES
        }
}
