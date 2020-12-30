package com.murat.veripark.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager


class NonSwipeableViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    override fun onInterceptTouchEvent(ev: MotionEvent?) = false

    override fun onTouchEvent(ev: MotionEvent?) = false

    private fun setScroller() {
        try {
            val viewpager = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, MyScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class MyScroller(context: Context) : Scroller(context, DecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, 350)
        }
    }
}
