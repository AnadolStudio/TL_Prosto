package com.touchin.prosto.feature.list

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.util.common_extention.getCompatDrawable
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.touchin.prosto.R
import com.touchin.prosto.base.fragment.BaseContentFragment
import com.touchin.prosto.databinding.FragmentOfferListBinding
import com.touchin.prosto.feature.detail.OfferDetailFragment
import com.touchin.prosto.feature.list.recycler.BigOfferCardHolder
import com.touchin.prosto.feature.model.OfferUi
import com.touchin.prosto.util.postUpdate
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section

class OfferListFragment : BaseContentFragment<OfferListState, OfferListViewModel, OfferListController>(
    R.layout.fragment_offer_list
) {

    private val binding by viewBinding { FragmentOfferListBinding.bind(it) }
    private val offersSection = Section()
    private val viewDelegate by lazy {
        ViewStateDelegate(
            contentView = binding.recycler,
            loadingView = binding.skeletons.root,
            errorView = binding.errorView,
        )
    }

    override fun createViewModelLazy() = viewModels<OfferListViewModel> { viewModelFactory }

    override fun initView(controller: OfferListController) = with(binding) {
        parentFragmentManager.setFragmentResultListener(
            OfferDetailFragment.TAG,
            this@OfferListFragment
        ) { requestKey: String, data ->
            val offer = data.getParcelable(getString(R.string.navigation_offer)) as? OfferUi

            when (requestKey) {
                OfferDetailFragment.TAG -> offer?.let(controller::onFavoriteChecked)
                else -> Unit
            }
        }

        toolbar.setBackClickListener { controller.onBackClicked() }
        favoriteButton.setOnClickListener { controller.onFavoriteFilterClicked() }
        errorButton.scaleAnimationOnClick { controller.onRetryClicked() }
        initRecycler()
    }

    private fun initRecycler() = with(binding.recycler) {
        setHasFixedSize(true)

        adapter = GroupieAdapter().apply {
            add(offersSection)
        }
    }

    override fun render(state: OfferListState, controller: OfferListController) = with(state) {
        when {
            loadingState.isError -> viewDelegate.showError()
            loadingState.isLoading -> showLoading()
            else -> viewDelegate.showContent()
        }

        val favouriteIcon = if (isFilterByFavorite) R.drawable.ic_favorite_checked else R.drawable.ic_favorite_unchecked
        binding.favoriteButton.setDrawable(requireContext().getCompatDrawable(favouriteIcon))
        binding.favoriteButton.isVisible = isFilterButtonVisible
        offersSection.postUpdate(binding.recycler, filteredOffersList.map { createOfferHolder(it) })
    }

    private fun showLoading() {
        viewDelegate.showLoading()
        val animation = AlphaAnimation(1f, 0f).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        binding.skeletons.root.startAnimation(animation)
    }

    protected fun createOfferHolder(offer: OfferUi): BigOfferCardHolder = BigOfferCardHolder(
        offer = offer,
        onOfferClick = controller::onOfferClicked,
        onFavoriteChecked = controller::onFavoriteChecked
    )

}
