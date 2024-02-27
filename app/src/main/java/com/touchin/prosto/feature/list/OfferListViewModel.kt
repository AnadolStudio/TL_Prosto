package com.touchin.prosto.feature.list

import android.content.Context
import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.anadolstudio.core.viewmodel.lce.lceFlow
import com.anadolstudio.core.viewmodel.lce.mapLceContent
import com.anadolstudio.core.viewmodel.lce.onEachContent
import com.anadolstudio.core.viewmodel.lce.onEachError
import com.touchin.data.repository.common.PreferencesStorage
import com.touchin.domain.repository.offers.OffersRepository
import com.touchin.prosto.R
import com.touchin.prosto.base.viewmodel.BaseContentViewModel
import com.touchin.prosto.base.viewmodel.navigateTo
import com.touchin.prosto.base.viewmodel.navigateUp
import com.touchin.prosto.feature.model.OfferUi
import com.touchin.prosto.feature.model.toUi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class OfferListViewModel @Inject constructor(
    private val context: Context,
    private val offersRepository: OffersRepository,
    private val storage: PreferencesStorage,
) : BaseContentViewModel<OfferListState>(
    OfferListState()
), OfferListController {

    init {
        loadOffers()
    }

    private fun loadOffers() {
        lceFlow { emit(offersRepository.getOfferList()) }
            .mapLceContent { offers ->
                val favoriteSet = storage.favoriteSet
                offers.map { it.toUi(isFavorite = favoriteSet.contains(it.id)) }
            }
            .onEach { updateState { copy(loadingState = it) } }
            .onEachContent { offers ->
                updateState { copy(offersList = offers, isFilterButtonVisible = storage.favoriteSet.isNotEmpty()) }
            }
            .onEachError { showError(it) }
            .launchIn(viewModelScope)
    }

    override fun onBackClicked() = _navigationEvent.navigateUp()

    override fun onOfferClicked(offerUi: OfferUi) = _navigationEvent.navigateTo(
        id = R.id.action_offerListFragment_to_offerBottom,
        args = bundleOf(context.getString(R.string.navigation_offer) to offerUi)
    )

    override fun onFavoriteChecked(offerUi: OfferUi) {
        val isFavorite = !offerUi.isFavorite

        if (isFavorite) {
            storage.favoriteSet += offerUi.id
        } else {
            storage.favoriteSet -= offerUi.id
        }

        val newOfferList = state.offersList.map {
            if (it.id == offerUi.id) it.copy(isFavorite = isFavorite) else it
        }
        updateState {
            copy(
                offersList = newOfferList,
                isFilterButtonVisible = storage.favoriteSet.isNotEmpty(),
                isFilterByFavorite = isFilterByFavorite && storage.favoriteSet.isNotEmpty()
            )
        }
    }

    override fun onFavoriteFilterClicked() = updateState { copy(isFilterByFavorite = !isFilterByFavorite) }
}
