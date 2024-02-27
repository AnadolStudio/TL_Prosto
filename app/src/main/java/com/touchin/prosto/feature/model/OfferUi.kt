package com.touchin.prosto.feature.model

import android.os.Parcelable
import com.touchin.domain.repository.offers.OfferDomain
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfferUi(
    val id: String,
    val name: String,
    val companyName: String,
    val companyShortDescription: String?,
    val companyImageUrl: String?,
    val reward: String?,
    val longDescription: String,
    val description: String?,
    val isActive: Boolean,
    val backgroundFirstColor: Int,
    val backgroundSecondColor: Int,
    var isFavorite: Boolean = false,
) : Parcelable

fun OfferDomain.toUi(isFavorite: Boolean = false): OfferUi = OfferUi(
    id = id,
    name = name,
    companyName = companyName,
    companyShortDescription = companyShortDescription,
    companyImageUrl = companyImageUrl,
    reward = reward,
    description = description,
    isActive = isActive,
    longDescription = longDescription,
    backgroundFirstColor = backgroundFirstColor,
    backgroundSecondColor = backgroundSecondColor,
    isFavorite = isFavorite,
)
