package com.touchin.prosto.feature.detail

import android.graphics.Color
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.touchin.prosto.R
import com.touchin.prosto.base.bottom.BaseContentBottom
import com.touchin.prosto.databinding.FragmentOfferDetailBinding
import com.touchin.prosto.di.viewmodel.assistedViewModel
import com.touchin.prosto.util.GradientDrawable
import com.touchin.prosto.util.isColorsLight

@Suppress("TooManyFunctions")
class OfferDetailFragment : BaseContentBottom<OfferDetailState, OfferDetailViewModel, OfferDetailController>(
    R.layout.fragment_offer_detail
) {

    companion object {
        const val TAG = "OfferDetailFragment"
    }

    private val binding by viewBinding { FragmentOfferDetailBinding.bind(it) }
    protected val args: OfferDetailFragmentArgs by navArgs()

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
            .offerDetailViewModelFactory()
            .create(args.offer)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is OfferDetailEvent.FavoriteResult -> setFragmentResult(
            TAG,
            bundleOf(getString(R.string.navigation_offer) to event.offerUi)
        )

        else -> super.handleEvent(event)
    }

    override fun render(state: OfferDetailState, controller: OfferDetailController) {
        val offerItem = state.offer
        val isColorsLight = isColorsLight(offerItem.backgroundFirstColor, offerItem.backgroundSecondColor)
        binding.mainInfo.initView(offerItem, isColorsLight)
        binding.offerName.text = offerItem.name
        binding.headerView.initView(offerItem, isColorsLight) { controller.onFavoriteChecked() }
        binding.longDescription.text = offerItem.longDescription

        binding.offerName.setTextColor(if (isColorsLight) Color.BLACK else Color.WHITE)

        binding.gradientBackground.background = GradientDrawable(
            firstColor = offerItem.backgroundFirstColor,
            secondColor = offerItem.backgroundSecondColor,
        )
    }
}
