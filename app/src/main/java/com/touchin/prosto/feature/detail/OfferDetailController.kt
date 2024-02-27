package com.touchin.prosto.feature.detail

import com.anadolstudio.core.viewmodel.BaseController
import com.touchin.prosto.feature.model.OfferUi

interface OfferDetailController : BaseController {
    fun onRetryClicked()
    fun onFavoriteChecked(offerUi: OfferUi)

}
