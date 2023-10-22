package com.touchin.prosto.di

import com.touchin.prosto.di.viewmodel.ViewModelFactory
import com.touchin.prosto.feature.detail.OfferDetailViewModel
import dagger.Module

interface SharedComponent {
    fun viewModelsFactory(): ViewModelFactory
    fun offerDetailViewModelFactory(): OfferDetailViewModel.Factory
}

@Module
class SharedModule {}

interface SharedComponentProvider {
    fun getModule(): SharedComponent
}
