package com.touchin.data.repository.common

import android.content.Context
import com.touchin.domain.repository.common.ResourceRepository

class ResourceRepositoryImpl(private val context: Context) : ResourceRepository {
    override fun getString(id: Int): String = context.getString(id)
    override fun getColor(id: Int): Int = context.getColor(id)
}
