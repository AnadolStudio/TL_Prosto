package com.touchin.data.api.utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient

class BaseClientFactory(
    private val interceptors: List<Interceptor>,
) : ClientFactory {

    override fun create(serverUrl: String): OkHttpClient = with(OkHttpClient.Builder()) {
        followRedirects(true)
        onSetupTimeOut(this)

        interceptors.forEach { addInterceptor(it) }

        build()
    }

}
