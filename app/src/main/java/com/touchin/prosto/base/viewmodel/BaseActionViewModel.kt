package com.touchin.prosto.base.viewmodel

import com.touchin.prosto.navigation.NavigateData
import com.anadolstudio.core.viewmodel.CoreActionViewModel

abstract class BaseActionViewModel : CoreActionViewModel<NavigateData>(), BaseViewModelDelegate {

    protected val baseViewModelDelegate: BaseViewModelDelegate = BaseViewModelDelegate.Delegate(_singleEvent)

    override fun showTodo() = baseViewModelDelegate.showTodo()

}
