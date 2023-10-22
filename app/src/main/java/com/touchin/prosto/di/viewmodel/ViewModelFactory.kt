package com.touchin.prosto.di.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = viewModels[modelClass]?.get()
        return viewModel as T
    }
}

inline fun <reified VM : ViewModel> Fragment.assistedViewModel(
    crossinline creator: (SavedStateHandle) -> VM,
): Lazy<VM> = viewModels { createAbstractSavedStateViewModelFactory(arguments, creator) }

inline fun <reified T : ViewModel> SavedStateRegistryOwner.createAbstractSavedStateViewModelFactory(
    arguments: Bundle? = Bundle(),
    crossinline creator: (SavedStateHandle) -> T,
): ViewModelProvider.Factory {
    return object : AbstractSavedStateViewModelFactory(
        owner = this@createAbstractSavedStateViewModelFactory,
        defaultArgs = arguments,
    ) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle,
        ): T = creator(handle) as T
    }
}
