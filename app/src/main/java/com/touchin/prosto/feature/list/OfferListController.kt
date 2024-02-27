package com.touchin.prosto.feature.list

import com.anadolstudio.core.viewmodel.BaseController
import com.touchin.prosto.feature.model.OfferUi

interface OfferListController : BaseController {
    fun onOfferClicked(offerUi: OfferUi)
    fun onFavoriteChecked(offerUi: OfferUi)
    fun onFavoriteFilterClicked()
    fun onRetryClicked()
}
