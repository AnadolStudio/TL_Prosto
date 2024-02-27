package com.touchin.prosto.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.touchin.prosto.R
import com.touchin.prosto.databinding.ViewBottomTopLineBinding

class BottomTopLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewBottomTopLineBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.BottomTopLine, defStyleAttr, 0) {
            binding.imageView.imageTintList = getColorStateList(R.styleable.BottomTopLine_android_tint)
        }
    }

}
