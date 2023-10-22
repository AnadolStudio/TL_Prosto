package com.touchin.prosto.base.viewmodel

import android.os.Bundle
import androidx.core.os.bundleOf
import com.anadolstudio.core.navigation.Add
import com.anadolstudio.core.navigation.Back
import com.anadolstudio.core.navigation.NavigationEvent
import com.anadolstudio.core.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.core.viewmodel.livedata.onNext
import com.touchin.prosto.navigation.NavigateData

fun SingleLiveEvent<NavigationEvent<NavigateData>>.navigateUp() = onNext(Back())

fun SingleLiveEvent<NavigationEvent<NavigateData>>.navigateTo(
    id: Int,
    args: Bundle = bundleOf()
) = onNext(Add(NavigateData(id, args)))
