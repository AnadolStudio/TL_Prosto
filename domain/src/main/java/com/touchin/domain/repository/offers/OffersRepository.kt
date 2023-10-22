package com.touchin.domain.repository.offers

interface OffersRepository {
    suspend fun getOfferList(): List<OfferDomain>
}
