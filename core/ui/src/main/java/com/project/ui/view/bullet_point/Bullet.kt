package com.project.ui.view.bullet_point

import android.graphics.Canvas

interface Bullet {
    fun drawLineUp(canvas: Canvas)
    fun drawLineDown(canvas: Canvas)
    fun drawCircle(canvas: Canvas)
}
