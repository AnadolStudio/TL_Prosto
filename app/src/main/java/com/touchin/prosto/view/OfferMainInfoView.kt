package com.touchin.prosto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.core.util.common.dpToPx
import com.anadolstudio.core.util.common_extention.setTextOrMakeGoneIfBlank
import com.google.android.material.snackbar.Snackbar
import com.touchin.prosto.R
import com.touchin.prosto.databinding.ViewOfferMainInfoBinding
import com.touchin.prosto.feature.model.OfferUi

class OfferMainInfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private companion object {
        val MIN_HEIGHT = 100.dpToPx()
    }

    private val binding: ViewOfferMainInfoBinding

    init {
        binding = ViewOfferMainInfoBinding.inflate(LayoutInflater.from(context), this)
        minHeight = MIN_HEIGHT
    }

    fun initView(offerUi: OfferUi) = with(binding) {
        title.setTextOrMakeGoneIfBlank(offerUi.reward)
        description.setTextOrMakeGoneIfBlank(offerUi.description)
        val alpha = if (offerUi.isActive) 1.0f else 0.5f
        title.alpha = alpha
        description.alpha = alpha
        if (!offerUi.isActive) {
            showSnackbar(context.getString(R.string.inactive_action_message))
        }
    }
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
