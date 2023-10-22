package com.touchin.prosto.util

import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.shapes.Shape

open class ShaderDrawable(
    shape: Shape,
    factory: ShaderFactory
) : PaintDrawable() {

    init {
        this.shape = shape
        this.shaderFactory = factory
    }

}
