package com.touchin.prosto.feature.list

import android.view.View
import androidx.core.view.isVisible
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
import kotlinx.coroutines.flow.onEach

class OfferListFragment : BaseContentFragment<OfferListState, OfferListViewModel, OfferListController>(
    R.layout.fragment_offer_list
) {

    private val binding by viewBinding { FragmentOfferListBinding.bind(it) }
    private val offersSection = Section()
    private val viewStateDelegate by lazy {
        ViewStateDelegate(
            contentView = binding.recycler,
            loadingView = null,//todo
            errorView = binding.errorLoadedLayout
        )
    }

    override fun createViewModelLazy() = viewModels<OfferListViewModel> { viewModelFactory }

    override fun initView(controller: OfferListController) = with(binding) {
        toolbar.setBackClickListener { controller.onBackClicked() }
        favoriteButton.setOnClickListener { controller.onFavoriteFilterClicked() }
        reloadedButton.setOnClickListener { controller.onReloadedClicked() }
        initRecycler()
    }

    private fun initRecycler() = with(binding.recycler) {
        setHasFixedSize(true)

        adapter = GroupieAdapter().apply {
            add(offersSection)
        }
    }

    override fun render(state: OfferListState, controller: OfferListController) {
        when(state.loadingState) {
            is LceState.Content -> {
                offersSection.postUpdate(binding.recycler, state.offersFilteredList.map { createOfferHolder(it) })
                binding.favoriteButton.isVisible = state.isFavoriteFilterVisibility
                viewStateDelegate.showContent()
            }
            LceState.Loading -> {
                //
                viewStateDelegate.showLoading()

            }
            is LceState.Error -> {
                viewStateDelegate.showError()
            }
            else -> {}
        }
    }

    protected fun createOfferHolder(offer: OfferUi): BigOfferCardHolder = BigOfferCardHolder(
        offer = offer,
        onOfferClick = controller::onOfferClicked,
        onFavoriteChecked = controller::onFavoriteChecked
    )

}
