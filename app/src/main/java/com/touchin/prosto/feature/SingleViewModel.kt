package com.touchin.prosto.feature

import com.anadolstudio.core.util.rx.smartSubscribe
import com.touchin.domain.repository.common.NightModeRepository
import com.touchin.prosto.base.viewmodel.BaseActionViewModel
import javax.inject.Inject

class SingleViewModel @Inject constructor(
    nightModeRepository: NightModeRepository,
) : BaseActionViewModel() {

    init {
        nightModeRepository.observeNightModeChanges()
            .smartSubscribe(
                onSuccess = {showEvent(SingleEvents.ChangeNightModeEvent(it)) },
                onError = this::showError
            )
            .disposeOnCleared()
    }

}
