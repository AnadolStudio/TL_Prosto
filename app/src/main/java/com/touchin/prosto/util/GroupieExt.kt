package com.touchin.prosto.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import timber.log.Timber

fun <T : Group> Section.update(vararg t: T) {
    this.update(t.toList())
}

fun Section.safeClear(recyclerView: RecyclerView) {
    try {
        this.clear()
    } catch (ex: IllegalStateException) {
        Timber.d(ex)
        recyclerView.post { this.clear() }
    }
}

fun <T : Group> Section.safeUpdate(recyclerView: View, vararg t: T) {
    try {
        this.update(t.toList())
    } catch (ex: IllegalStateException) {
        Timber.d(ex)
        recyclerView.post { this.update(t.toList()) }
    }
}

fun Section.postUpdate(recyclerView: RecyclerView, item: Group) = postUpdate(recyclerView, listOf(item))

fun Section.postUpdate(recyclerView: RecyclerView, items: List<Group>) {
    recyclerView.post { this.update(items) }
}
