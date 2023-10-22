package com.touchin.data.api.utils

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

interface ClientFactory {

    fun create(serverUrl: String): OkHttpClient

    fun onSetupTimeOut(builder: OkHttpClient.Builder) = builder.setupTimeOut()

    fun OkHttpClient.Builder.setupTimeOut(
        connectTimeout: Long = NetworkConstants.CONNECT_TIMEOUT_SECONDS,
        readTimeout: Long = NetworkConstants.READ_TIMEOUT_SECONDS,
        writeTimeout: Long = NetworkConstants.WRITE_TIMEOUT_SECONDS,
    ) {
        connectTimeout(connectTimeout, TimeUnit.SECONDS)
        readTimeout(readTimeout, TimeUnit.SECONDS)
        writeTimeout(writeTimeout, TimeUnit.SECONDS)
    }

}
