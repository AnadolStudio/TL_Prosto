package com.touchin.prosto.base.bottom

import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import com.anadolstudio.core.presentation.Eventable
import com.anadolstudio.core.presentation.Navigatable
import com.anadolstudio.core.presentation.Renderable
import com.anadolstudio.core.presentation.dialogs.bottom_sheet.CoreContentBottom
import com.anadolstudio.core.viewmodel.BaseController
import com.touchin.prosto.base.viewmodel.BaseContentViewModel
import com.touchin.prosto.di.SharedComponent
import com.touchin.prosto.di.getSharedModule
import com.touchin.prosto.navigation.NavigatableDelegate
import com.touchin.prosto.navigation.NavigateData

abstract class BaseContentBottom<
        ViewState : Any,
        ViewModel : BaseContentViewModel<ViewState>,
        Controller : BaseController>(
    @LayoutRes layoutId: Int
) : CoreContentBottom<ViewState, NavigateData, ViewModel, Controller>(layoutId), Renderable {

    override val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    override val navigatableDelegate: Navigatable<NavigateData> get() = NavigatableDelegate(findNavController())
    override val stateMap: MutableMap<String, Int> = mutableMapOf()

    protected val viewModelFactory by lazy { getSharedComponent().viewModelsFactory() }

    protected open fun getSharedComponent(): SharedComponent = getSharedModule()

    override fun createViewModel(): ViewModel = createViewModelLazy().value

    protected abstract fun createViewModelLazy(): Lazy<ViewModel>

}
