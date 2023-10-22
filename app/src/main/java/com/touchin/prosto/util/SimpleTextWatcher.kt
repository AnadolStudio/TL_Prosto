package com.touchin.prosto.util

import android.text.Editable
import android.text.TextWatcher

open class SimpleTextWatcher(
    private val beforeTextChanged: ((String) -> Unit)? = null,
    private val onTextChanged: ((String) -> Unit)? = null,
    private val afterTextChanged: ((String) -> Unit)? = null
) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        beforeTextChanged?.invoke(s.toString())
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged?.invoke(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged?.invoke(s.toString())
    }

}
