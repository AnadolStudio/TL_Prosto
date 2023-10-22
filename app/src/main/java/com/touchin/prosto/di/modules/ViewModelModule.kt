package com.touchin.prosto.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.touchin.prosto.di.viewmodel.ViewModelFactory
import com.touchin.prosto.di.viewmodel.ViewModelKey
import com.touchin.prosto.feature.SingleViewModel
import com.touchin.prosto.feature.list.OfferListViewModel
import com.touchin.prosto.feature.start.StartViewModel
import com.touchin.prosto.feature.support.SupportViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SingleViewModel::class)
    fun bindSingleViewModel(viewModel: SingleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferListViewModel::class)
    fun bindOfferListViewModel(viewModel: OfferListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    fun bindStartViewModel(viewModel: StartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SupportViewModel::class)
    fun bindSupportViewModel(viewModel: SupportViewModel): ViewModel
}
