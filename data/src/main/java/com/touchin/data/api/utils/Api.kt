package com.touchin.data.api.utils

class Api<Api>(
    serverUrl: String,
    protected val retrofitFactory: RetrofitFactory,
    protected val clazz: Class<Api>,
) {

    @Volatile
    private var api = createApi(serverUrl)

    fun provide() = api

    private fun createApi(serverUrl: String): Api = retrofitFactory.createRetrofitApi(clazz, serverUrl)
}
