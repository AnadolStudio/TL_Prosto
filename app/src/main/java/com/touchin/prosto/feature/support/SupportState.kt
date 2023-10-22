package com.touchin.prosto.feature.support

data class SupportState(
    val emailText: String = "",
    val hasEmailError: Boolean = false
) {
    val sendButtonEnable: Boolean
        get() = listOf(
            emailText.isBlank(),
            hasEmailError,
        ).all { hasError -> !hasError }
}

