package com.touchin.prosto.feature.list

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.viewModels
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.lce.LceState
import com.touchin.prosto.R
import com.touchin.prosto.base.fragment.BaseContentFragment
import com.touchin.prosto.databinding.FragmentOfferListBinding
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
            loadingView = binding.loading.root,
            stubView = null,
            errorView = binding.error.root
        )
    }

    override fun createViewModelLazy() = viewModels<OfferListViewModel> { viewModelFactory }

    override fun initView(controller: OfferListController) = with(binding) {
        toolbar.setBackClickListener { controller.onBackClicked() }
        favoriteButton.setOnClickListener { controller.onFavoriteFilterClicked() }
        binding.error.retry.setOnClickListener { controller.retryLoadOffers() }
        initRecycler()
    }

    private fun initRecycler() = with(binding.recycler) {
        setHasFixedSize(true)

        adapter = GroupieAdapter().apply {
            add(offersSection)
        }
    }

    override fun render(state: OfferListState, controller: OfferListController) {
        when (state.loadingState) {
            is LceState.Loading -> {
                viewDelegate.showLoading()
                val sceletoneAnimation = AlphaAnimation(1f, 0f).apply {
                    duration = 1000
                    interpolator = AccelerateDecelerateInterpolator()
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                }
                binding.loading.root.startAnimation(sceletoneAnimation)
            }

            is LceState.Error -> {
                viewDelegate.showError()
            }

            is LceState.Content -> {
                viewDelegate.showContent()
                offersSection.postUpdate(binding.recycler, state.offersList.map { createOfferHolder(it) })
            }
        }

    }

    protected fun createOfferHolder(offer: OfferUi): BigOfferCardHolder = BigOfferCardHolder(
        offer = offer,
        onOfferClick = controller::onOfferClicked,
        onFavoriteChecked = controller::onFavoriteChecked
    )

}
