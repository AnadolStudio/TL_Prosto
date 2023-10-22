package com.touchin.data.api

import com.touchin.data.repository.offers.OfferResponse
import retrofit2.http.GET

interface OffersApi {

    @GET("offers")
    suspend fun getOfferList(): List<OfferResponse>

}
