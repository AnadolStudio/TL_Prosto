package com.touchin.prosto.util

class GradientBackgroundFactory(
    firstColor: Int,
    secondColor: Int
) : LinearGradientShaderFactory(
    firstColor = firstColor,
    secondColor = secondColor,
    x0 = LINEAR_GRADIENT_X0,
    y0 = LINEAR_GRADIENT_Y0,
    x1 = LINEAR_GRADIENT_X1,
    y1 = LINEAR_GRADIENT_Y1
) {

    private companion object {

        // Empirical matched magic numbers
        const val LINEAR_GRADIENT_X0 = -0.1F
        const val LINEAR_GRADIENT_Y0 = 0.45F
        const val LINEAR_GRADIENT_X1 = 1.1F
        const val LINEAR_GRADIENT_Y1 = 0.85F
    }

}
