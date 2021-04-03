package com.hfhuaizhi.scale_module

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.roundToInt

/**
 * @author hfhuaizhi
 * @date 2021/1/23 22:26
 */
class CommonScaleContainer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var scale = 0.98f
    private var duration = 120L
    private var mColor = 0
    private var colorAnim: ValueAnimator = ValueAnimator.ofInt()

    init {
        if (attrs != null) {
            val ta: TypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonScaleContainer)
            val color = ta.getColor(R.styleable.CommonScaleContainer_container_bg, Color.WHITE)
            val radius = ta.getDimension(R.styleable.CommonScaleContainer_container_radius, 0f)
            val background = RoundRectDrawable(ColorStateList.valueOf(color), radius)
            setBackground(background)
            background.radius = radius
            setContainerColor(color)
            ta.recycle()
        }
        clipToOutline = true
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                this.animate().scaleX(scale).scaleY(scale).setDuration(duration).start()
                startColorAnim()
            }
            MotionEvent.ACTION_UP -> {
                if (clickInView(event.x, event.y)) {
                    onClick()
                } else {
                    onCancel()
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                onCancel()
                return false
            }
        }
        return true
    }

    private fun clickInView(x: Float, y: Float) = x <= width && x >= 0 && y >= 0 && y <= height

    private fun onClick() {
        this.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
        reverseColorAnim()
        performClick()
    }

    private fun onCancel() {
        this.animate().scaleX(1f).scaleY(1f).setDuration(duration).start()
        reverseColorAnim()
    }

    private fun startColorAnim() {
        val originColor = mColor
        val targetColor = Color.argb(getTargetColor(Color.alpha(originColor)), getTargetColor(Color.red(originColor)),
                getTargetColor(Color.green(originColor)), getTargetColor(Color.blue(originColor)))
        colorAnim.setIntValues(originColor, targetColor)
        colorAnim.duration = duration
        colorAnim.setEvaluator(ArgbEvaluator())
        colorAnim.addUpdateListener {
            val color = it.animatedValue as Int
            ((background as RoundRectDrawable)).color = ColorStateList.valueOf(color)
        }
        colorAnim.start()
    }

    private fun getTargetColor(color: Int): Int {
        return (color * 24f / 25).roundToInt()
    }

    private fun reverseColorAnim() {
        colorAnim.reverse()
    }

    fun setContainerColor(color: Int) {
        mColor = color
        post {
            ((background as RoundRectDrawable)).color = ColorStateList.valueOf(color)
        }
    }
}