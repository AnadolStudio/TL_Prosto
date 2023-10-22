package com.touchin.prosto.feature.list.recycler

import android.view.View
import com.anadolstudio.core.util.common.throttleClick
import com.touchin.prosto.R
import com.touchin.prosto.base.recycler.PayloadableViewHolder
import com.touchin.prosto.databinding.ItemBigOfferCardBinding
import com.touchin.prosto.feature.model.OfferUi
import com.touchin.prosto.util.GradientDrawable

class BigOfferCardHolder(
    private val offer: OfferUi,
    private val onOfferClick: (OfferUi) -> Unit,
    private val onFavoriteChecked: (OfferUi) -> Unit,
) : PayloadableViewHolder<ItemBigOfferCardBinding>(R.layout.item_big_offer_card) {

    override fun bind(binding: ItemBigOfferCardBinding, item: PayloadableViewHolder<ItemBigOfferCardBinding>) {
        if (item !is BigOfferCardHolder) return

        val offerItem = item.offer
        with(binding) {
            root.throttleClick { onOfferClick(offerItem) }

            headerView.initView(offerItem, onFavoriteChecked)
            mainInfoView.initView(offerItem)

            container.background = GradientDrawable(
                firstColor = offerItem.backgroundFirstColor,
                secondColor = offerItem.backgroundSecondColor,
            )
        }
    }

    override fun initializeViewBinding(view: View): ItemBigOfferCardBinding = ItemBigOfferCardBinding.bind(view)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BigOfferCardHolder

        if (offer != other.offer) return false

        return true
    }

    override fun hashCode(): Int = offer.id.hashCode()

    override fun getChangePayload(newItem: com.xwray.groupie.Item<*>): Any = newItem

}
