package com.example.todo_list.ui.view.bullet_point

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.todo_list.R

open class BulletPoint(context: Context, attrs: AttributeSet) : View(context, attrs), Bullet {
    private var position: Position? = null
    private val lineSize: Int
    private val lineColor: Int
    private var background = context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
    protected val circleColor: Int
    protected val circleSize: Int
    protected val paint = Paint()
    protected var color: Int? = null

    enum class Position {
        START, MID, END, ONE
    }

    init {
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.SideLine, 0, 0)
        color = attr.getColor(R.styleable.SideLine_color, Color.BLUE)
        circleSize = attr.getDimensionPixelSize(R.styleable.SideLine_circleSize, 15)
        circleColor = attr.getColor(R.styleable.SideLine_circleColor, Color.BLUE)
        lineSize = attr.getDimensionPixelSize(R.styleable.SideLine_lineSize, 5)
        lineColor = attr.getDimensionPixelSize(R.styleable.SideLine_lineColor, Color.BLUE)
    }

    fun setPosition(position: Position) {
        this.position = position
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (position ?: return) {
            Position.START -> {
                drawCircle(canvas)
                drawLineDown(canvas)
            }

            Position.MID -> {
                drawLineUp(canvas)
                drawCircle(canvas)
                drawLineDown(canvas)
            }

            Position.END -> {
                drawLineUp(canvas)
                drawCircle(canvas)
            }

            Position.ONE -> {
                drawCircle(canvas)
            }
        }
    }

    override fun drawLineUp(canvas: Canvas) {
        settingDrawLine()

        val x = width / 2F
        val y1 = height / 2F - circleSize
        val y2 = 0F

        canvas.drawLine(x, y1, x, y2, paint)
    }

    override fun drawLineDown(canvas: Canvas) {
        settingDrawLine()

        val x = width / 2F
        val y1 = height / 2F + circleSize
        val y2 = height.toFloat()

        canvas.drawLine(x, y1, x, y2, paint)
    }

    override fun drawCircle(canvas: Canvas) {
        paint.color = color ?: circleColor
        canvas.drawCircle(width / 2F, height / 2F, circleSize.toFloat(), paint)

        settingBackgroundCircle(canvas)
    }

    protected fun settingBackgroundCircle(canvas: Canvas){
        paint.color = when(background){
            Configuration.UI_MODE_NIGHT_YES -> {
                Color.BLACK
            }
            else -> {
                Color.WHITE
            }
        }

        canvas.drawCircle(width / 2F, height / 2F, circleSize.toFloat()-5, paint)
    }

    protected fun settingDrawLine(color: Int = this.color ?: lineColor){
        paint.color = color
        paint.strokeWidth = lineSize.toFloat()
    }
}

