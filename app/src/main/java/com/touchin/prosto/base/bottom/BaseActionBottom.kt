package com.touchin.prosto.base.bottom

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.core.presentation.Eventable
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.dialogs.bottom_sheet.CoreActionBottom
import com.anadolstudio.core.viewmodel.BaseController
import com.touchin.prosto.base.viewmodel.BaseActionViewModel
import com.touchin.prosto.di.SharedComponent
import com.touchin.prosto.di.getSharedModule
import com.touchin.prosto.navigation.NavigatableDelegate
import com.touchin.prosto.navigation.NavigateData

abstract class BaseActionBottom<ViewModel : BaseActionViewModel, Controller : BaseController>(
    @LayoutRes layoutId: Int
) : CoreActionBottom<Controller, NavigateData, ViewModel>(layoutId) {

    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()
}
