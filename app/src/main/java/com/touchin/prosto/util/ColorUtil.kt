package com.touchin.prosto.util

import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun isColorsLight(vararg colors: Int): Boolean {
    val average = colors.map {
        0.2126 * it.red + 0.7152 * it.green + 0.0722 * it.blue
    }.average()

    return average > 255 * 0.5
}
