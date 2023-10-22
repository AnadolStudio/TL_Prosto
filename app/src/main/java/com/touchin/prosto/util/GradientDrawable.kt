package com.touchin.prosto.util

import android.graphics.drawable.shapes.RectShape

class GradientDrawable(
    firstColor: Int,
    secondColor: Int,
) : ShaderDrawable(
    shape = RectShape(),
    factory = GradientBackgroundFactory(
        firstColor = firstColor,
        secondColor = secondColor
    )
)
