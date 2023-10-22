package com.touchin.data.api.utils

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class RetrofitFactory(
    protected val moshiFactory: MoshiFactory,
    protected val clientFactory: ClientFactory
) {

    open fun <T> createRetrofitApi(clazz: Class<T>, serverUrl: String): T {
        return Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(clientFactory.create(serverUrl))
                .addConverterFactory(NullableConverterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshiFactory.networkMoshi))
                .build()
                .create(clazz)
    }

}
