package com.touchin.prosto.base.fragment

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.core.presentation.Eventable
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.Renderable
import com.anadolstudio.core.presentation.fragment.CoreContentBaseFragment
import com.anadolstudio.core.viewmodel.BaseController
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.touchin.prosto.di.SharedComponent
import com.touchin.prosto.di.getSharedModule
import com.touchin.prosto.navigation.NavigatableDelegate
import com.touchin.prosto.navigation.NavigateData

abstract class BaseContentFragment<
        ViewState : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>(
    @LayoutRes layoutId: Int
) : CoreContentBaseFragment<ViewState, NavigateData, ViewModel, Controller>(layoutId), Renderable {

    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())
    override val stateMap: MutableMap<String, Int> = mutableMapOf()
    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

}
