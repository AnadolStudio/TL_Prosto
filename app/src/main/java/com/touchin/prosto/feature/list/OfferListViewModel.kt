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
    private val preferencesStorage: PreferencesStorage
) : BaseContentViewModel<OfferListState>(
    OfferListState()
), OfferListController {

    init {
        loadOffers()
    }

    private var favoriteDiscounts: Set<String>
        get() = preferencesStorage.favoriteDiscounts
        set(value) {
            preferencesStorage.favoriteDiscounts = value
        }

    private fun loadOffers() {
        lceFlow { emit(offersRepository.getOfferList()) }
            .mapLceContent { offers ->
                offers.map {
                    it.toUi(isFavorite = favoriteDiscounts.contains(it.id))
                }
            }
            .onEach { updateState { copy(loadingState = it) } }
            .onEachContent { offers -> updateState { copy(offersList = offers) } }
            .onEachError { showError(it) }
            .launchIn(viewModelScope)
    }

    override fun onBackClicked() = _navigationEvent.navigateUp()

    override fun onOfferClicked(offerUi: OfferUi) = _navigationEvent.navigateTo(
        id = R.id.action_offerListFragment_to_offerBottom,
        args = bundleOf(context.getString(R.string.navigation_offer) to offerUi)
    )

    override fun onFavoriteChecked(offerUi: OfferUi) {
        val mutableFavoriteDiscounts = favoriteDiscounts.toMutableList()
        val offers = state.offersList.map {
            if (it.id == offerUi.id) {
                with(mutableFavoriteDiscounts) {
                    if (!contains(offerUi.id))
                        add(offerUi.id)
                    else
                        remove(offerUi.id)
                }
                it.copy(isFavorite = !it.isFavorite)
            } else {
                it
            }
        }
        favoriteDiscounts = mutableFavoriteDiscounts.toSet()
        updateState { copy(offersList = offers) }
    }

    override fun onFavoriteFilterClicked() = showTodo()
}
