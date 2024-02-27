package com.touchin.prosto.feature.support

import com.anadolstudio.core.viewmodel.BaseController

interface SupportController : BaseController {
    fun onEmailChanged(text: String)
    fun onSubjectChanged(text: String)
    fun onBodyChanged(text: String)
    fun onSendClicked()
}
