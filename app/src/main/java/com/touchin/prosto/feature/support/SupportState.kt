package com.touchin.prosto.feature.support

data class SupportState(
    val emailText: String = "",
    val subjectText: String = "",
    val bodyText: String = "",
    val hasEmailError: Boolean = false,
    val hasSubjectError: Boolean = false,
    val hasBodyError: Boolean = false
) {
    val sendButtonEnable: Boolean
        get() = listOf(
            emailText.isBlank(),
            hasEmailError,
            subjectText.isBlank(),
            hasSubjectError,
            bodyText.isBlank(),
            hasBodyError,
        ).all { hasError -> !hasError }
}

