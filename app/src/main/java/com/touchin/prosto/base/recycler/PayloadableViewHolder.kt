package com.touchin.prosto.base.recycler

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.Item

abstract class PayloadableViewHolder<VB : ViewBinding>(@LayoutRes layoutRes: Int) : BaseViewHolder<VB>(layoutRes) {

    override fun bind(binding: VB, position: Int, payloads: MutableList<Any>) {
        val newItem = payloads.filterIsInstance(this::class.java).lastOrNull()

        if (newItem != null) {
            bind(binding, newItem)
            onPayloadBinding(binding, newItem)
        } else {
            super.bind(binding, position, payloads)
            onFullBinding(binding, this)
        }
    }

    override fun bind(binding: VB, position: Int) = bind(binding, this)

    protected abstract fun bind(binding: VB, item: PayloadableViewHolder<VB>)

    override fun getChangePayload(newItem: Item<*>): Any = newItem

    /**
     * Метод вызывается во время частичного связывания данных с ViewHolder
     * Частичное связывание происходит, если id у viewHolder одинаковые,
     * метод equals возвращает false и getChangePayload не равен null
     *
     * Как это работает:
     * @see com.xwray.groupie.DiffCallback
     * DiffCallback сперва сравнивает id старого и нового item в areItemsTheSame
     * @see com.xwray.groupie.DiffCallback.areItemsTheSame
     * Если возвращается false, то происходит полный bind
     * Если вернулось true, то идет сравнение в areContentsTheSame, где сравниваются items через equals
     * @see com.xwray.groupie.DiffCallback.areContentsTheSame
     *
     * Если вернулось true, то значит элемент не изменился, перерисовки не будет
     * Если вернулось false, то вызовится метод getChangePayload
     * @see com.xwray.groupie.DiffCallback.getChangePayload
     *
     * Если этот метод вернул данные, то вызовится частичная перерисовка, иначе полная
     */
    protected open fun onPayloadBinding(binding: VB, item: PayloadableViewHolder<VB>) = Unit

    /**
     * Метод вызывается во время полного связывания данных с ViewHolder
     * Полное связывание происходит при первом инициализации или, если id у viewHolder различаются
     */
    protected open fun onFullBinding(binding: VB, item: PayloadableViewHolder<VB>) = Unit
}
