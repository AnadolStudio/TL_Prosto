package com.touchin.domain.repository.offers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfferDomain(
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
) : Parcelable
