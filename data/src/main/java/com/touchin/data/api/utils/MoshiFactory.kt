package com.touchin.data.api.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MoshiFactory {

    val networkMoshi: Moshi by lazy {
        Moshi.Builder()
                .add(DateTimeAdapter())
                .add(SkipBadElementsListAdapter.Factory)
                .addLast(KotlinJsonAdapterFactory())
                .build()
    }

}
