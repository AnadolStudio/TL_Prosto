package com.touchin.prosto.util

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.graphics.drawable.ShapeDrawable.ShaderFactory

open class LinearGradientShaderFactory(
        private val firstColor: Int,
        private val secondColor: Int,
        private val x0: Float,
        private val y0: Float,
        private val x1: Float,
        private val y1: Float,
        private val tileMode: TileMode = TileMode.CLAMP
) : ShaderFactory() {

    override fun resize(width: Int, height: Int): Shader =
            LinearGradient(
                    x0 * width,
                    y0 * height,
                    x1 * width,
                    y1 * height,
                    firstColor,
                    secondColor,
                    tileMode
            )

}
