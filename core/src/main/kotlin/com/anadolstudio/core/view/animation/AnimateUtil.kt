package com.anadolstudio.core.view.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.anadolstudio.core.util.common_extention.makeGone
import com.anadolstudio.core.util.common_extention.makeVisible

object AnimateUtil {
    const val DURATION_EXTRA_SHORT: Long = 100
    const val DURATION_SHORT: Long = 200
    const val DURATION_NORMAL: Long = 300
    const val DURATION_LONG: Long = 400
    const val DURATION_EXTRA_LONG: Long = 800
    const val START = -1
    const val END = 1

    const val REDUCE_SCALE_CLICK = 0.9F
    const val INCREASE_SCALE_CLICK = 1.1F

    private const val SCALE_DEFAULT = 1.0F

    private enum class Vector { HORIZONTAL, VERTICAL }

    fun showAnimX(view: View, start: Float, end: Float) {
        view.visibility = INVISIBLE
        view.translationX = start
        view.animate()
            .translationX(end)
            .setDuration(DURATION_NORMAL)
            .setListener(getSimpleStartListener(view))
    }

    fun showAnimX(view: View, start: Float, end: Float, visible: Int) {
        view.translationX = start
        view.animate()
                .translationX(end)
                .setDuration(DURATION_NORMAL)
                .setListener(getSimpleListener(view, visible))
    }

    fun showAnimX(view: View, start: Float, end: Float, listener: AnimatorListenerAdapter) {
        view.visibility = INVISIBLE
        view.translationX = start
        view.animate()
                .translationX(end)
                .setDuration(DURATION_NORMAL)
                .setListener(listener)
    }

    fun <T : View> fadOutAnimation(
        view: T,
        duration: Long = DURATION_SHORT,
        visibility: Int = INVISIBLE,
        completion: ((T) -> Unit)? = null
    ) {
        with(view) {
            animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .withEndAction {
                        this.visibility = visibility
                        completion?.let { it(this) }
                    }
        }
    }

    fun <T : View> fadInAnimation(
        view: T,
        duration: Long = DURATION_SHORT, completion: ((T) -> Unit)? = null
    ) {
        with(view) {
            alpha = 0f
            visibility = VISIBLE
            animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .withEndAction { completion?.let { it(this) } }
        }
    }

    fun View.showTranslationTopOut(duration: Long = DURATION_NORMAL, safeVisible: Boolean = true) {
        if (safeVisible && !isVisible) return

        visibility = GONE
        translationY = 0F
        animate()
                .translationY(-height.toFloat())
                .setDuration(duration)
                .setInterpolator(DecelerateInterpolator())
                .setListener(getSimpleEndListener(this))
    }

    fun View.showTranslationTopIn(duration: Long = DURATION_NORMAL, safeVisible: Boolean = true) {
        if (safeVisible && isVisible) return

        translationY = -height.toFloat()
        animate()
            .translationY(0f)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .setListener(getSimpleStartListener(this))
    }

    fun View.showTranslationTopOutBottomIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInAnotherEge(START, Vector.VERTICAL, duration, onEndAction)

    fun View.showTranslationBottomOutTopIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInAnotherEge(END, Vector.VERTICAL, duration, onEndAction)

    fun View.showTranslationStartOutEndIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInAnotherEge(END, Vector.HORIZONTAL, duration, onEndAction)

    fun View.showTranslationEndOutStartIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInAnotherEge(START, Vector.HORIZONTAL, duration, onEndAction)

    private fun View.outEdgeInAnotherEge(
        direction: Int,
        vector: Vector,
        duration: Long,
        onEndAction: (() -> Unit)?
    ) {
        animate().apply {
            when (vector) {
                Vector.HORIZONTAL -> this.translationX(direction * width.toFloat())
                Vector.VERTICAL -> this.translationY(direction * height.toFloat())
            }
        }
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                onEndAction?.invoke()
                when (vector) {
                    Vector.HORIZONTAL -> translationX = -1 * direction * width.toFloat()
                    Vector.VERTICAL -> translationY = -1 * direction * height.toFloat()
                }
                animate()
                    .apply {
                        when (vector) {
                            Vector.HORIZONTAL -> this.translationX(0F)
                            Vector.VERTICAL -> this.translationY(0F)
                        }
                    }
                    .setDuration(duration)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
            }
            .start()
    }

    fun View.showTranslationTopOutTopIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInThisEge(START, Vector.VERTICAL, duration, onEndAction)

    fun View.showTranslationBottomOutBottomIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInThisEge(END, Vector.VERTICAL, duration, onEndAction)

    fun View.showTranslationStartOutStartIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInThisEge(END, Vector.HORIZONTAL, duration, onEndAction)

    fun View.showTranslationEndOutEndIn(duration: Long = DURATION_NORMAL, onEndAction: (() -> Unit)? = null) =
        outEdgeInThisEge(START, Vector.HORIZONTAL, duration, onEndAction)

    private fun View.outEdgeInThisEge(
        direction: Int,
        vector: Vector,
        duration: Long,
        onEndAction: (() -> Unit)?
    ) {
        animate().apply {
            when (vector) {
                Vector.HORIZONTAL -> this.translationX(direction * width.toFloat())
                Vector.VERTICAL -> this.translationY(direction * height.toFloat())
            }
        }
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                onEndAction?.invoke()
                animate()
                    .apply {
                        when (vector) {
                            Vector.HORIZONTAL -> this.translationX(0F)
                            Vector.VERTICAL -> this.translationY(0F)
                        }
                    }
                    .setDuration(duration)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
            }
            .start()
    }


    fun ViewGroup.animSlideOut(@Slide.GravityFlag slideEdge: Int, duration: Long = DURATION_NORMAL) {
        if (!isVisible) return

        val slide = Slide(slideEdge)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
        TransitionManager.beginDelayedTransition(this, slide)
        makeGone()
    }

    fun ViewGroup.animSlideIn(@Slide.GravityFlag slideEdge: Int, duration: Long = DURATION_NORMAL) {
        if (isVisible) return

        val slide = Slide(slideEdge)
                .setDuration(duration)
                .setInterpolator(DecelerateInterpolator())
        TransitionManager.beginDelayedTransition(this, slide)
        makeVisible()
    }

    fun ViewGroup.animSlideTopOut(duration: Long = DURATION_NORMAL) =
            animSlideOut(Gravity.TOP, duration)

    fun ViewGroup.animSlideTopIn(duration: Long = DURATION_NORMAL) =
            animSlideIn(Gravity.TOP, duration)

    fun ViewGroup.animSlideBottomOut(duration: Long = DURATION_NORMAL) =
            animSlideOut(Gravity.BOTTOM, duration)

    fun ViewGroup.animSlideBottomIn(duration: Long = DURATION_NORMAL) =
            animSlideIn(Gravity.BOTTOM, duration)

    fun ViewGroup.animSlideStartOut(duration: Long = DURATION_NORMAL) =
            animSlideOut(Gravity.START, duration)

    fun ViewGroup.animSlideStartIn(duration: Long = DURATION_NORMAL) =
            animSlideIn(Gravity.START, duration)

    fun ViewGroup.animSlideEndIn(duration: Long = DURATION_NORMAL) =
            animSlideIn(Gravity.END, duration)

    fun ViewGroup.animSlideEndOut(duration: Long = DURATION_NORMAL) =
            animSlideOut(Gravity.END, duration)

    fun showAnimY(view: View, start: Float, end: Float, listener: AnimatorListenerAdapter) {
        view.visibility = INVISIBLE
        view.translationY = start
        view.animate()
                .translationY(end)
                .setDuration(DURATION_NORMAL)
                .setListener(listener)
    }

    fun showAnimY(view: View, start: Float, end: Float, visible: Int) {
        view.translationY = start
        view.animate()
                .translationY(end)
                .setDuration(DURATION_NORMAL)
                .setListener(getSimpleListener(view, visible))
    }

    fun getSimpleStartListener(view: View) = getSimpleListener(view, VISIBLE)

    fun getSimpleEndListener(view: View) = getSimpleListener(view, GONE)

    fun getSimpleListener(view: View, visible: Int) = object : AnimatorListenerAdapter() {

        override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            if (visible == VISIBLE) {
                view.clearAnimation()
                view.visibility = visible
            }
        }

        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            if (visible != VISIBLE) {
                view.clearAnimation()
                view.visibility = visible
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun View.scaleAnimationOnClick(
            scale: Float = REDUCE_SCALE_CLICK,
            scaleDefault: Float = SCALE_DEFAULT,
            action: () -> Unit
    ) = setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> scaleAnimation(scale)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> scaleAnimation(scaleDefault, getActionOrNull(event, action))
        }

        return@setOnTouchListener true
    }

    private fun View.getActionOrNull(event: MotionEvent, action: (() -> Unit)): (() -> Unit)? {
        if (measuredWidth == 0 || measuredHeight == 0) return action

        val inBounds = event.x.toInt() in 0..measuredWidth
                && event.y.toInt() in 0..measuredHeight

        return if (inBounds) action else null
    }

    private fun View.scaleAnimation(scale: Float, action: (() -> Unit)? = null) = animate()
            .scaleX(scale)
            .scaleY(scale)
            .setDuration(DURATION_SHORT)
            .withStartAction { action?.invoke() }
            .setInterpolator(DecelerateInterpolator())
            .start()

    fun blinkAnimation(): Animation = AlphaAnimation(1f, 0f).apply {
        duration = 1000
        interpolator = AccelerateDecelerateInterpolator()
        repeatCount = Animation.INFINITE
        repeatMode = Animation.REVERSE
    }

}
