package com.touchin.prosto.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.core.util.common.dpToPx
import com.anadolstudio.core.util.common_extention.setTextOrMakeGoneIfBlank
import com.touchin.prosto.databinding.ViewOfferMainInfoBinding
import com.touchin.prosto.feature.model.OfferUi

class OfferMainInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
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
        if (offerUi.isActive) {
            title.alpha = 1f
            description.alpha = 1f
        } else {
            title.alpha = 0.5f
            description.alpha = 0.5f
        }
    }
}
