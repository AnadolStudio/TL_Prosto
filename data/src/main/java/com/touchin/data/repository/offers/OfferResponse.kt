package com.touchin.data.repository.offers

import android.graphics.Color
import com.touchin.domain.repository.offers.OfferDomain
import com.touchin.prosto.util.parseColorOrDefault

data class OfferResponse(
    val id: String,
    val name: String,
    val companyName: String,
    val companyShortDescription: String?,
    val companyImageUrl: String?,
    val reward: String?,
    val longDescription: String,
    val description: String?,
    val isActive: Boolean?,
    val backgroundFirstColor: String?,
    val backgroundSecondColor: String?,
)

fun OfferResponse.toDomain(): OfferDomain = OfferDomain(
    id = id,
    name = name,
    companyName = companyName,
    companyShortDescription = companyShortDescription,
    companyImageUrl = companyImageUrl,
    reward = reward,
    description = description,
    longDescription = longDescription,
    isActive = isActive != false,
    backgroundFirstColor = backgroundFirstColor.parseColorOrDefault(Color.WHITE),
    backgroundSecondColor = backgroundSecondColor.parseColorOrDefault(Color.WHITE),
)
