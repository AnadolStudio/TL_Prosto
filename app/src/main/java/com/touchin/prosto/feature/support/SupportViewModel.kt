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
            subject = state.subjectText, // TODO необходимо изменить в рамках задачи
            email = state.emailText,
            body = state.textPism // TODO необходимо изменить в рамках задачи
        )
    }

    override fun onEmailChanged(text: String) = updateState { copy(emailText = text, hasEmailError = false) }
    override fun onSubject(text: String) = updateState { copy(subjectText = text, hasSubjectError = false) }
    override fun onSubject1(text: String) = updateState { copy(textPism = text, hasBodyError = false) }


    private fun hasErrors(): Boolean {
        val russianOnlyRegex = Regex("[а-яА-Я]+")
        val hasSubjectError = !russianOnlyRegex.matches(state.subjectText)
        updateState { copy(hasSubjectError = hasSubjectError) }

        val hasBodyError = !russianOnlyRegex.matches(state.textPism)
        updateState { copy(hasBodyError = hasBodyError) }

        val hasEmailError = !EmailUtil.EMAIL_PATTERN_REGEX.matches(state.emailText)
        updateState { copy(hasEmailError = hasEmailError) }

        return listOf(hasEmailError, hasSubjectError, hasBodyError).any { hasError -> hasError }

    }

    override fun onBackClicked() = _navigationEvent.navigateUp()

}
