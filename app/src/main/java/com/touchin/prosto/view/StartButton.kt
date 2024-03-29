package com.touchin.prosto.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.touchin.prosto.R
import com.touchin.prosto.databinding.ViewStartButtonBinding

class StartButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewStartButtonBinding = ViewStartButtonBinding.bind(
        LayoutInflater.from(context).inflate(R.layout.view_start_button, this, true)
    )

    init {
        initializeAttributes(attrs)
    }

    private fun initializeAttributes(attrs: AttributeSet?) {
        if (attrs == null) return
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.StartButton)

        binding.apply {
            typeArray.getDrawable(R.styleable.StartButton_src).let(::setDrawable)
            typeArray.getText(R.styleable.StartButton_text).toString().let(::setText)
        }

        typeArray.recycle()
    }

    fun setDrawable(drawable: Drawable?) = binding.imageView.setImageDrawable(drawable)

    fun setDrawable(drawableRes: Int) =
        setDrawable(ContextCompat.getDrawable(context, drawableRes))

    fun setText(text: String?) = binding.textView.setText(text)

    fun setText(textRes: Int) = binding.textView.setText(textRes)
}
