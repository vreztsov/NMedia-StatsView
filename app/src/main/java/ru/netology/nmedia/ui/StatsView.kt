package ru.netology.nmedia.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import ru.netology.nmedia.R
import ru.netology.nmedia.util.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {
    private var radius = 0F
    private var center = PointF(0F, 0F)
    private var oval = RectF(0F, 0F, 0F, 0F)
    private val startAngle = -90F
    private val fullAngle = 360F

    private var lineWidth = AndroidUtils.dp(context, 5F).toFloat()
    private var fontSize = AndroidUtils.dp(context, 40F).toFloat()
    private var colors = emptyList<Int>()

    private var progress = 0F
    private val precision = 0.1F
    private val minimalArc = 0.0001F
    private var valueAnimator: ValueAnimator? = null
    private var fillingType: Int = 0

    init {
        context.withStyledAttributes(attrs, R.styleable.StatsView) {
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth)
            fontSize = getDimension(R.styleable.StatsView_fontSize, fontSize)
            val resId = getResourceId(R.styleable.StatsView_colors, 0)
            colors = resources.getIntArray(resId).toList()
        }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatsView,
            0, 0
        ).apply {
            try {
                fillingType = getInteger(R.styleable.StatsView_fillingType, fillingType)
            } finally {
                recycle()
            }
        }

    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    var data: List<Float> = emptyList()
        set(value) {
            field = calcPartsOf(value)
            update()
        }

    private var unFilled: Float = 0F
    private var containsUnfilled = false
    private var dataSum = 0F

    fun setDataWithUnfilled(list: List<Float>, unFilled: Float) {
        this.unFilled = unFilled
        this.containsUnfilled = true
        this.data = list
    }


    private fun calcPartsOf(list: List<Float>): List<Float> {
        val listSum = list.sum()
        val sum = listSum + unFilled
        val result = list.toMutableList().map {
            it.div(sum)
        }
        dataSum = listSum.div(sum)
        return if (containsUnfilled) {
            result + unFilled.div(sum)
        } else {
            result
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 2F - lineWidth / 2
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius,
        )
    }

    override fun onDraw(canvas: Canvas) {
        if (data.isEmpty()) {
            return
        }
        canvas.drawText(
            "%.2f%%".format(dataSum * 100),
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint,
        )
        var startFrom = startAngle
        data.forEachIndexed { index, datum ->
            val angle = fullAngle * datum
            if (index == data.size - 1 && containsUnfilled) {
                paint.color = ContextCompat.getColor(context, R.color.divider_color)
            } else {
                paint.color = colors.getOrElse(index) { randomColor() }
            }
            when (fillingType) {
                1 -> {
                    canvas.drawArc(
                        oval,
                        startFrom + 360F * progress,
                        angle * progress,
                        false,
                        paint
                    )
                }
                2 -> {
                    if (fullAngle * progress < startFrom + angle - startAngle) {
                        canvas.drawArc(
                            oval,
                            startFrom,
                            fullAngle * progress - startFrom + startAngle,
                            false,
                            paint
                        )
                        return
                    } else {
                        canvas.drawArc(oval, startFrom, angle, false, paint)
                    }
                }

                else -> {
                    canvas.drawArc(oval, startFrom, angle, false, paint)
                }
            }
            startFrom += angle
        }
        when (fillingType) {
            1 -> {
                if (progress > (1F - precision)) {
                    canvas.drawArc(
                        oval,
                        startFrom,
                        minimalArc,
                        false,
                        paint.apply { color = colors[0] })
                }
            }
            else -> canvas.drawPoint(center.x, center.y - radius, paint.apply { color = colors[0] })
        }

    }

    private fun update() {
        valueAnimator?.let {
            it.removeAllListeners()
            it.cancel()
        }
        progress = 0F

        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {
            addUpdateListener { anim ->
                progress = anim.animatedValue as Float
                invalidate()
            }
            duration = 5000
            interpolator = LinearInterpolator()

        }.also {
            it.start()

        }
    }

    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}