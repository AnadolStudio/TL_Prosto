package com.touchin.prosto.view

import android.content.Context
import android.graphics.Color
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
        const val ACTIVE_ALPHA = 1F
        const val INACTIVE_ALPHA = 0.5F
    }

    private val binding: ViewOfferMainInfoBinding

    init {
        binding = ViewOfferMainInfoBinding.inflate(LayoutInflater.from(context), this)
        minHeight = MIN_HEIGHT
    }

    fun initView(offerUi: OfferUi, isColorsLight: Boolean) = with(binding) {
        title.setTextOrMakeGoneIfBlank(offerUi.reward)
        description.setTextOrMakeGoneIfBlank(offerUi.description)
        root.alpha = if (offerUi.isActive) ACTIVE_ALPHA else INACTIVE_ALPHA

        val color = if (isColorsLight) Color.BLACK else Color.WHITE
        title.setTextColor(color)
        description.setTextColor(color)
    }
}
