package com.touchin.prosto.base.recycler

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.viewbinding.BindableItem

abstract class BaseViewHolder<T: ViewBinding>(
        @LayoutRes private val layoutRes: Int
): BindableItem<T>() {

    abstract override fun hashCode(): Int

    abstract override fun equals(other: Any?): Boolean

    final override fun getId(): Long = hashCode().toLong()

    override fun getLayout(): Int = layoutRes

}
