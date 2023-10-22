package com.touchin.prosto.feature

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.touchin.prosto.R
import com.touchin.prosto.base.activity.BaseActionActivity
import com.touchin.prosto.databinding.ActivityMainBinding

class SingleActivity :
    BaseActionActivity<SingleViewModel, SingleController>(R.id.nav_host_fragment_content_main) {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun createViewModelLazy(): Lazy<SingleViewModel> = viewModels { viewModelFactory }

    override fun provideRootView(): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is SingleEvents.ChangeNightModeEvent -> AppCompatDelegate.setDefaultNightMode(event.mode)
        else -> super.handleEvent(event)
    }

    override fun recreate() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
