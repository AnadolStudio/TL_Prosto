package com.touchin.prosto.feature.support

import com.anadolstudio.core.viewmodel.BaseController

interface SupportController : BaseController {
    fun onEmailChanged(text: String)
    fun onSendClicked()
}
