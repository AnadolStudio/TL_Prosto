package com.touchin.prosto.base.viewmodel

import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.anadolstudio.core.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.core.viewmodel.livedata.onNext
import com.anadolstudio.core.presentation.event.SingleMessageSnack

interface BaseViewModelDelegate {
    fun showTodo()

    class Delegate(private val singleLiveEvent: SingleLiveEvent<SingleEvent>) : BaseViewModelDelegate {

        private val todoMessages = listOf(
                "Извините, этот функционал пока не реализован \uD83D\uDE43",
                "Эта функция будет доступна в ближайшее время \uD83C\uDFC3",
        )

        override fun showTodo() {
            singleLiveEvent.onNext(SingleMessageSnack.Short(todoMessages.random()))
        }
    }

}
