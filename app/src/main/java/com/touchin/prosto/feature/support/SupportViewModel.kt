package com.touchin.prosto.feature.support

import android.content.Context
import com.touchin.prosto.base.viewmodel.BaseContentViewModel
import com.touchin.prosto.base.viewmodel.navigateUp
import com.touchin.prosto.util.EmailUtil
import com.touchin.prosto.util.sendEmail
import javax.inject.Inject

class SupportViewModel @Inject constructor(
    private val context: Context,
) : BaseContentViewModel<SupportState>(
    initState = SupportState()
), SupportController {

    override fun onSendClicked() {
        if (hasErrors()) return

        sendEmail(
            context = context,
            subject = "subject", // TODO необходимо изменить в рамках задачи
            email = state.emailText,
            body = "body" // TODO необходимо изменить в рамках задачи
        )
    }

    override fun onEmailChanged(text: String) = updateState { copy(emailText = text, hasEmailError = false) }

    private fun hasErrors(): Boolean {
        val hasEmailError = !EmailUtil.EMAIL_PATTERN_REGEX.matches(state.emailText)
        updateState { copy(hasEmailError = hasEmailError) }

        return listOf(hasEmailError).any { hasError -> hasError }
    }

    override fun onBackClicked() = _navigationEvent.navigateUp()

}
