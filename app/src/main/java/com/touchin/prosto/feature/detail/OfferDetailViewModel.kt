package com.touchin.prosto.feature.detail

import com.touchin.prosto.base.viewmodel.BaseContentViewModel
import com.touchin.prosto.base.viewmodel.navigateUp
import com.touchin.prosto.feature.model.OfferUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("TooManyFunctions", "LongParameterList")
class OfferDetailViewModel @AssistedInject constructor(
    @Assisted offerUi: OfferUi
) : BaseContentViewModel<OfferDetailState>(
    initState = OfferDetailState(
        offer = offerUi
    )
), OfferDetailController {

    override fun onRetryClicked() = showTodo()

    override fun onBackClicked() = _navigationEvent.navigateUp()

    override fun onFavoriteChecked(offerUi: OfferUi) {

    }

    @AssistedFactory
    interface Factory {
        fun create(offerUi: OfferUi): OfferDetailViewModel
    }
}
