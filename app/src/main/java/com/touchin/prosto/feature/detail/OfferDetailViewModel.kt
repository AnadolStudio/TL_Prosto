package com.touchin.prosto.feature.detail

import com.anadolstudio.core.presentation.event.SingleMessageSnack
import com.touchin.prosto.R
import com.touchin.prosto.base.viewmodel.BaseContentViewModel
import com.touchin.prosto.base.viewmodel.navigateUp
import com.touchin.prosto.feature.model.OfferUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("TooManyFunctions", "LongParameterList")
class OfferDetailViewModel @AssistedInject constructor(
    context: android.content.Context,
    @Assisted offerUi: OfferUi
) : BaseContentViewModel<OfferDetailState>(
    initState = OfferDetailState(
        offer = offerUi
    )
), OfferDetailController {

    init {
        if (!offerUi.isActive) {
            showEvent(SingleMessageSnack.Long(context.getString(R.string.inactive_offer)))
        }
    }

    override fun onRetryClicked() = showTodo()

    override fun onBackClicked() = _navigationEvent.navigateUp()

    @AssistedFactory
    interface Factory {
        fun create(offerUi: OfferUi): OfferDetailViewModel
    }
}
