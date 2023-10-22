package com.touchin.prosto.feature.start

import com.touchin.domain.repository.common.NightModeRepository
import com.touchin.prosto.R
import com.touchin.prosto.base.viewmodel.BaseActionViewModel
import com.touchin.prosto.base.viewmodel.navigateTo
import com.touchin.prosto.base.viewmodel.navigateUp
import javax.inject.Inject

class StartViewModel @Inject constructor(
    private val nightModeRepository: NightModeRepository
) : BaseActionViewModel(), StartController {

    override fun onOffersClicked() = _navigationEvent.navigateTo(id = R.id.action_startFragment_to_offerListFragment)

    override fun onSupportClicked() = _navigationEvent.navigateTo(id = R.id.action_startFragment_to_supportFragment)

    override fun onNightModeClicked() = nightModeRepository.toggleNightMode()

    override fun onBackClicked() = _navigationEvent.navigateUp()

}
