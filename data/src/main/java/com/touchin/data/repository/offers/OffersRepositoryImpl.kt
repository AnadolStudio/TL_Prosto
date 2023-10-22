package com.touchin.data.repository.offers

import com.touchin.data.api.OffersApi
import com.touchin.domain.repository.offers.OfferDomain
import com.touchin.domain.repository.offers.OffersRepository

class OffersRepositoryImpl(private val api: OffersApi) : OffersRepository {

    override suspend fun getOfferList(): List<OfferDomain> = api.getOfferList().map { it.toDomain() }
}
