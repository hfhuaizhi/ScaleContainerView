package com.hfhuaizhi.scale_module

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.RequiresApi
import kotlin.math.ceil
import kotlin.math.cos

/**
 * copy from card view
 * Very simple drawable that draws a rounded rectangle background with arbitrary corners and also
 * reports proper outline for Lollipop.
 *
 *
 * Simpler and uses less resources compared to GradientDrawable or ShapeDrawable.
 */
@RequiresApi(21)
internal class RoundRectDrawable(backgroundColor: ColorStateList?, private var mRadius: Float) : Drawable() {
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val mBoundsF: RectF
    private val mBoundsI: Rect
    private var mPadding = 0f
    private var mInsetForPadding = false
    private var mInsetForRadius = true
    private var mBackground: ColorStateList? = null
    private var mTintFilter: PorterDuffColorFilter? = null
    private var mTint: ColorStateList? = null
    private var mTintMode: PorterDuff.Mode? = PorterDuff.Mode.SRC_IN
    private fun setBackground(color: ColorStateList?) {
        mBackground = color ?: ColorStateList.valueOf(Color.TRANSPARENT)
        mPaint.color = mBackground!!.getColorForState(state, mBackground!!.defaultColor)
    }

    fun setPadding(padding: Float, insetForPadding: Boolean, insetForRadius: Boolean) {
        if (padding == mPadding && mInsetForPadding == insetForPadding && mInsetForRadius == insetForRadius) {
            return
        }
        mPadding = padding
        mInsetForPadding = insetForPadding
        mInsetForRadius = insetForRadius
        updateBounds(null)
        invalidateSelf()
    }

    override fun draw(canvas: Canvas) {
        val paint = mPaint
        val clearColorFilter: Boolean
        if (mTintFilter != null && paint.colorFilter == null) {
            paint.colorFilter = mTintFilter
            clearColorFilter = true
        } else {
            clearColorFilter = false
        }
        canvas.drawRoundRect(mBoundsF, mRadius, mRadius, paint)
        if (clearColorFilter) {
            paint.colorFilter = null
        }
    }

    private fun updateBounds(bounds: Rect?) {
        var bounds = bounds
        if (bounds == null) {
            bounds = getBounds()
        }
        mBoundsF[bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat()] = bounds.bottom.toFloat()
        mBoundsI.set(bounds)
        if (mInsetForPadding) {
            val vInset = calculateVerticalPadding(mPadding, mRadius, mInsetForRadius)
            val hInset = calculateHorizontalPadding(mPadding, mRadius, mInsetForRadius)
            mBoundsI.inset(ceil(hInset.toDouble()).toInt(), ceil(vInset.toDouble()).toInt())
            // to make sure they have same bounds.
            mBoundsF.set(mBoundsI)
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        updateBounds(bounds)
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(mBoundsI, mRadius)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    var radius: Float
        get() = mRadius
        set(radius) {
            if (radius == mRadius) {
                return
            }
            mRadius = radius
            updateBounds(null)
            invalidateSelf()
        }
    var color: ColorStateList?
        get() = mBackground
        set(color) {
            setBackground(color)
            invalidateSelf()
        }

    override fun setTintList(tint: ColorStateList?) {
        mTint = tint
        mTintFilter = createTintFilter(mTint, mTintMode)
        invalidateSelf()
    }

    override fun setTintMode(tintMode: PorterDuff.Mode?) {
        mTintMode = tintMode
        mTintFilter = createTintFilter(mTint, mTintMode)
        invalidateSelf()
    }

    override fun onStateChange(stateSet: IntArray): Boolean {
        val newColor = mBackground!!.getColorForState(stateSet, mBackground!!.defaultColor)
        val colorChanged = newColor != mPaint.color
        if (colorChanged) {
            mPaint.color = newColor
        }
        if (mTint != null && mTintMode != null) {
            mTintFilter = createTintFilter(mTint, mTintMode)
            return true
        }
        return colorChanged
    }

    override fun isStateful(): Boolean {
        return (mTint != null && mTint!!.isStateful
                || mBackground != null && mBackground!!.isStateful || super.isStateful())
    }

    /**
     * Ensures the tint filter is consistent with the current tint color and
     * mode.
     */
    private fun createTintFilter(tint: ColorStateList?, tintMode: PorterDuff.Mode?): PorterDuffColorFilter? {
        if (tint == null || tintMode == null) {
            return null
        }
        val color = tint.getColorForState(state, Color.TRANSPARENT)
        return PorterDuffColorFilter(color, tintMode)
    }

    companion object {
        private val COS_45 = cos(Math.toRadians(45.0))
        private const val SHADOW_MULTIPLIER = 1.5f
        fun calculateVerticalPadding(maxShadowSize: Float, cornerRadius: Float,
                                     addPaddingForCorners: Boolean): Float {
            return if (addPaddingForCorners) {
                (maxShadowSize * SHADOW_MULTIPLIER + (1 - COS_45) * cornerRadius).toFloat()
            } else {
                maxShadowSize * SHADOW_MULTIPLIER
            }
        }

        fun calculateHorizontalPadding(maxShadowSize: Float, cornerRadius: Float,
                                       addPaddingForCorners: Boolean): Float {
            return if (addPaddingForCorners) {
                (maxShadowSize + (1 - COS_45) * cornerRadius).toFloat()
            } else {
                maxShadowSize
            }
        }
    }

    init {
        setBackground(backgroundColor)
        mBoundsF = RectF()
        mBoundsI = Rect()
    }
}