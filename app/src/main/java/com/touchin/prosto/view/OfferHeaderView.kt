package com.touchin.prosto.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.core.util.common.throttleClick
import com.anadolstudio.core.util.common_extention.getCompatDrawable
import com.anadolstudio.core.util.common_extention.setImageFromUrl
import com.anadolstudio.core.util.common_extention.setTextOrMakeGoneIfBlank
import com.touchin.prosto.R
import com.touchin.prosto.databinding.ViewOfferHeaderBinding
import com.touchin.prosto.feature.model.OfferUi

class OfferHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewOfferHeaderBinding

    init {
        binding = ViewOfferHeaderBinding.inflate(LayoutInflater.from(context), this)
    }

    fun initView(offerUi: OfferUi, isColorsLight: Boolean, onFavoriteChecked: (OfferUi) -> Unit) = with(binding) {
        companyName.text = offerUi.companyName
        companyShortDescription.setTextOrMakeGoneIfBlank(offerUi.companyShortDescription)

        val favouriteIcon = if (offerUi.isFavorite) R.drawable.ic_favorite_checked else R.drawable.ic_favorite_unchecked
        favoriteButton.setImageDrawable(root.context.getCompatDrawable(favouriteIcon))
        favoriteButton.throttleClick { onFavoriteChecked.invoke(offerUi) }

        companyLogo.setImageFromUrl(
            url = offerUi.companyImageUrl,
            errorId = R.drawable.ic_offer_default_company_logo,
            placeholderId = R.drawable.ic_offer_default_company_logo,
        )

        val color = if (isColorsLight) Color.BLACK else Color.WHITE
        companyName.setTextColor(color)
        companyShortDescription.setTextColor(color)
        favoriteButton.imageTintList = ColorStateList.valueOf(color)
    }
}
