package com.touchin.prosto

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.touchin.data.repository.common.PreferencesStorage
import com.touchin.prosto.di.DI
import com.touchin.prosto.di.SharedComponent
import com.touchin.prosto.di.SharedComponentProvider
import javax.inject.Inject

class App : Application(), SharedComponentProvider {

    @Inject
    lateinit var preferences: PreferencesStorage

    override fun onCreate() {
        super.onCreate()

        DI.init(applicationContext)
        DI.getComponent().inject(this)
        AppCompatDelegate.setDefaultNightMode(preferences.nightMode)
    }

    override fun getModule(): SharedComponent = DI.getComponent()
}
