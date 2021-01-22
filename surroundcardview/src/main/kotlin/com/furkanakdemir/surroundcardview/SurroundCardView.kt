/*
 * Copyright 2020 Furkan Akdemir
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.furkanakdemir.surroundcardview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Path
import android.graphics.PathMeasure
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.furkanakdemir.surroundcardview.StartPoint.TOP_START
import com.furkanakdemir.surroundcardview.canvas.CanvasTransformer
import com.furkanakdemir.surroundcardview.canvas.CanvasTransformerFactory
import com.google.android.material.card.MaterialCardView

@Suppress("TooManyFunctions")
class SurroundCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    var surroundListener: SurroundListener? = null
    var releaseListener: ReleaseListener? = null

    var isCardSurrounded: Boolean = false
        private set

    private var releaseAnimator = ValueAnimator.ofFloat(VALUE_RELEASE_ANIMATOR_START)
    private var surroundAnimator = ValueAnimator.ofFloat(VALUE_SURROUND_ANIMATOR_END)

    private var pathPaint: Paint = Paint(ANTI_ALIAS_FLAG)

    private var firstPath = Path()
    private var secondPath = Path()

    private var radiusCorner: Float

    private var linePadding: Float = 0f
    private var surroundStrokeWidth: Int = 0
    private var duration: Long = DURATION_DEFAULT

    private var internalHeight: Float = 0f
    private var internalWidth: Float = 0f

    @ColorInt
    private var surroundColor: Int = ContextCompat.getColor(
        context,
        R.color.scv_surround_color_default
    )

    private var startPoint = TOP_START

    private var canvasTransformer: CanvasTransformer = CanvasTransformerFactory.default()

    init {
        clipToOutline = false
        pathPaint.style = Paint.Style.STROKE
        radiusCorner = radius

        getAttributes(context, attrs)

        setSurroundStrokeWidthInternal(surroundStrokeWidth)
        setSurroundStrokeColorInternal(surroundColor)
    }

    private fun getAttributes(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.SurroundCardView) {
            surroundColor = getColor(
                R.styleable.SurroundCardView_scv_color,
                context.getColor(R.color.scv_surround_color_default)
            )

            duration = getInteger(
                R.styleable.SurroundCardView_scv_duration,
                DURATION_DEFAULT.toInt()
            ).toLong()

            surroundStrokeWidth = getDimensionPixelSize(
                R.styleable.SurroundCardView_scv_width,
                resources.getDimensionPixelSize(R.dimen.scv_stroke_width_default)
            )

            isCardSurrounded = getBoolean(
                R.styleable.SurroundCardView_scv_surrounded,
                IS_SURROUNDED_DEFAULT
            )

            startPoint = StartPoint.values()[
                getInt(R.styleable.SurroundCardView_scv_startPoint, TOP_START.ordinal)
            ]
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        internalHeight = h.toFloat()
        internalWidth = w.toFloat()

        prepare()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        canvasTransformer.transform(canvas)

        canvas.drawPath(firstPath, pathPaint)
        canvas.drawPath(secondPath, pathPaint)
        canvas.restore()
    }

    fun surround() {
        if (!isCardSurrounded) {
            post { update(isSurrounded = true, forced = false) }
        }
    }

    fun release() {
        if (isCardSurrounded) {
            post { update(isSurrounded = false, forced = false) }
        }
    }

    fun switch() {
        if (isCardSurrounded) {
            release()
        } else {
            surround()
        }
    }

    fun setSurrounded(isSurrounded: Boolean) {
        isCardSurrounded = isSurrounded
        post { update(isSurrounded = isSurrounded, forced = true) }
    }

    private fun update(isSurrounded: Boolean, forced: Boolean) {
        if (forced) {
            if (!isSurrounded) {
                firstPath.reset()
                secondPath.reset()
            } else {
                prepare()
            }
            invalidate()
        } else {
            if (isSurrounded) {
                surroundInternal()
            } else {
                releaseInternal()
            }
        }
    }

    private fun releaseInternal() {
        releaseAnimator.start()
        isCardSurrounded = false
    }

    private fun surroundInternal() {
        prepare()
        surroundAnimator.start()
        isCardSurrounded = true
    }

    private fun prepare() {
        canvasTransformer =
            CanvasTransformerFactory.create(internalWidth, internalHeight, startPoint)

        pathPaint.pathEffect = null

        createFirstPath()
        createSecondPath()
        setupAnimators()
    }

    private fun setupAnimators() {
        val measure = PathMeasure(firstPath, false)
        val length = measure.length

        releaseAnimator = ValueAnimator.ofFloat(VALUE_RELEASE_ANIMATOR_START, length)

        releaseAnimator.duration = duration
        releaseAnimator.interpolator = AccelerateDecelerateInterpolator()

        releaseAnimator.addUpdateListener {
            val phase = it.animatedValue as Float
            pathPaint.pathEffect = DashPathEffect(floatArrayOf(length, length), phase)
            invalidate()
        }

        releaseAnimator.doOnEnd { releaseListener?.onRelease() }

        surroundAnimator = ValueAnimator.ofFloat(length, VALUE_SURROUND_ANIMATOR_END)
        surroundAnimator.interpolator = AccelerateDecelerateInterpolator()
        surroundAnimator.duration = duration

        surroundAnimator.doOnEnd { surroundListener?.onSurround() }

        surroundAnimator.addUpdateListener {
            val phase = it.animatedValue as Float
            pathPaint.pathEffect = DashPathEffect(floatArrayOf(length, length), phase)
            invalidate()
        }
    }

    fun setStartPoint(startPoint: StartPoint) {
        this.startPoint = startPoint
    }

    fun setDuration(duration: Long) {
        this.duration = duration
    }

    fun setSurroundStrokeColor(@ColorRes color: Int) {
        val colorId = context.getColor(color)
        setSurroundStrokeColorInternal(colorId)
    }

    private fun setSurroundStrokeColorInternal(colorId: Int) {
        pathPaint.color = colorId
    }

    fun setSurroundStrokeWidth(@DimenRes width: Int) {
        val widthInPx = context.resources.getDimensionPixelSize(width)
        setSurroundStrokeWidthInternal(widthInPx)
    }

    private fun setSurroundStrokeWidthInternal(widthInPx: Int) {
        pathPaint.strokeWidth = widthInPx.toFloat()
        linePadding = (widthInPx / 2).toFloat()
    }

    private fun createSecondPath() {
        secondPath.reset()
        secondPath.moveTo(linePadding, radiusCorner)
        secondPath.lineTo(linePadding, internalHeight - radiusCorner)
        secondPath.quadTo(
            linePadding,
            internalHeight - linePadding,
            radiusCorner,
            internalHeight - linePadding
        )
        secondPath.lineTo(internalWidth - radiusCorner, internalHeight - linePadding)
        secondPath.quadTo(
            internalWidth - linePadding,
            internalHeight - linePadding,
            internalWidth - linePadding,
            internalHeight - radiusCorner
        )
    }

    private fun createFirstPath() {
        firstPath.reset()
        firstPath.moveTo(linePadding, radiusCorner)
        firstPath.quadTo(linePadding, linePadding, radiusCorner, linePadding)
        firstPath.lineTo(internalWidth - radiusCorner, linePadding)
        firstPath.quadTo(
            internalWidth - linePadding,
            linePadding,
            internalWidth - linePadding,
            radiusCorner
        )
        firstPath.lineTo(
            internalWidth - linePadding,
            internalHeight - radiusCorner
        )
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        surroundAnimator.cancel()
        releaseAnimator.cancel()
    }

    public override fun onSaveInstanceState(): Parcelable {
        val superState: Parcelable? = super.onSaveInstanceState()
        return SurroundCardSavedState(superState).apply {
            savedIsSurrounded = isCardSurrounded
            savedStartPoint = startPoint
            savedDuration = duration
            savedSurroundColor = surroundColor
            savedSurroundStrokeWidth = surroundStrokeWidth
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SurroundCardSavedState
        super.onRestoreInstanceState(savedState.superState)

        isCardSurrounded = savedState.savedIsSurrounded

        setDuration(savedState.savedDuration)
        setStartPoint(savedState.savedStartPoint)
        setSurroundStrokeColorInternal(savedState.savedSurroundColor)
        setSurroundStrokeWidthInternal(savedState.savedSurroundStrokeWidth)
    }

    companion object {
        internal const val DURATION_DEFAULT = 600L
        private const val VALUE_SURROUND_ANIMATOR_END = 0f
        private const val VALUE_RELEASE_ANIMATOR_START = 0f

        private const val IS_SURROUNDED_DEFAULT = false
    }
}
