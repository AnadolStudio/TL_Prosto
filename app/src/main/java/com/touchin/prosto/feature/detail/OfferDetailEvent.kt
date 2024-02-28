package com.touchin.prosto.feature.detail

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent
import com.touchin.prosto.feature.model.OfferUi

sealed class OfferDetailEvent : SingleCustomEvent() {

    class FavoriteResult(val offerUi: OfferUi) : OfferDetailEvent()
}
