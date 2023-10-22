package com.touchin.prosto.base.activity

import androidx.annotation.IdRes
import androidx.navigation.findNavController
import com.touchin.prosto.di.SharedComponent
import com.touchin.prosto.di.getSharedModule
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.activity.CoreActionActivity
import com.anadolstudio.core.viewmodel.BaseController
import com.touchin.prosto.base.viewmodel.BaseActionViewModel
import com.touchin.prosto.navigation.NavigatableDelegate
import com.touchin.prosto.navigation.NavigateData

abstract class BaseActionActivity<
        ViewModel : BaseActionViewModel,
        Controller : BaseController>(
    @IdRes val navigateContainerId: Int,
) : CoreActionActivity<Controller, NavigateData, ViewModel>() {

    override val navigatableDelegate: Navigatable<NavigateData>
        get() = NavigatableDelegate(
            findNavController(navigateContainerId)
        )

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

    override fun createViewModel(): ViewModel = createViewModelLazy().value

    protected abstract fun createViewModelLazy(): Lazy<ViewModel>
}
