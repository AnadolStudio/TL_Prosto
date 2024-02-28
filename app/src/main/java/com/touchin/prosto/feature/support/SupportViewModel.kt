package com.touchin.prosto.feature.support

import android.content.Context
import com.touchin.data.repository.common.PreferencesStorage
import com.touchin.prosto.base.viewmodel.BaseContentViewModel
import com.touchin.prosto.base.viewmodel.navigateUp
import com.touchin.prosto.util.EmailUtil
import com.touchin.prosto.util.sendEmail
import javax.inject.Inject

class SupportViewModel @Inject constructor(
    private val context: Context,
    private val storage: PreferencesStorage,
) : BaseContentViewModel<SupportState>(
    initState = SupportState()
), SupportController {

    private companion object {
        val LATIN_REGEX = "[a-zA-z]".toRegex()
    }

    init {
        showEvent(
            SupportEvents.DraftEvent(
                emailText = storage.email,
                subjectText = storage.subject,
                bodyText = storage.body,
            )
        )
    }

    override fun onSendClicked() {
        if (hasErrors()) return

        updateState { copy(isEmailSend = true) }
        storage.email = ""
        storage.subject = ""
        storage.body = ""

        sendEmail(
            context = context,
            subject = state.subjectText,
            email = state.emailText,
            body = state.bodyText
        )
    }

    override fun onEmailChanged(text: String) =
        updateState { copy(emailText = text, hasEmailError = false, isEmailSend = false) }

    override fun onSubjectChanged(text: String) =
        updateState { copy(subjectText = text, hasSubjectError = false, isEmailSend = false) }

    override fun onBodyChanged(text: String) =
        updateState { copy(bodyText = text, hasBodyError = false, isEmailSend = false) }

    private fun hasErrors(): Boolean {
        val hasEmailError = !EmailUtil.EMAIL_PATTERN_REGEX.matches(state.emailText)
        val hasSubjectError = LATIN_REGEX.find(state.subjectText) != null
        val hasBodyError = LATIN_REGEX.find(state.bodyText) != null

        updateState {
            copy(hasEmailError = hasEmailError, hasSubjectError = hasSubjectError, hasBodyError = hasBodyError)
        }

        return listOf(hasEmailError, hasSubjectError, hasBodyError).any { hasError -> hasError }
    }

    override fun onBackClicked() = _navigationEvent.navigateUp()

    override fun onCleared() {
        if (!state.isEmailSend) {
            storage.email = state.emailText
            storage.subject = state.subjectText
            storage.body = state.bodyText
        }

        super.onCleared()
    }

}
