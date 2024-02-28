package com.touchin.prosto.feature.support

import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent

sealed class SupportEvents : SingleCustomEvent() {

    class DraftEvent(
        val emailText: String = "",
        val subjectText: String = "",
        val bodyText: String = "",
    ) : SupportEvents()

}
