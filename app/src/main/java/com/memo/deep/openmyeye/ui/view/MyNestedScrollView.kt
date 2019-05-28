package com.memo.deep.openmyeye.ui.view

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.memo.deep.openmyeye.ui.activity.CategoryDetailActivity

class MyNestedScrollView : NestedScrollView {
    private var intercept = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var mDownsPosX = 0f
    var mDownsPosY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.getX()
        val y = ev.getY()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownsPosX = x
                mDownsPosY = y
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = Math.abs(x - mDownsPosX)
                val deltaY = Math.abs(y - mDownsPosY)
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX >= deltaY) {
                    return false

                } else if (y - mDownsPosY < 0) {
                    // 判断并是上滑,且移动的距离不是照片的距离，进行打断
                    if (scrollY < CategoryDetailActivity.moveLength)
                        return true
                } else if (y - mDownsPosY < 0) {
                    // 判断是上滑,并且移动的距离不是照片的距离，进行打断
                    if (scrollY < CategoryDetailActivity.moveLength)
                        return true
                } else {
                    return intercept
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    /*
    改方法用来处理NestedScrollView是否拦截滑动事件
     */
    fun setIntercept(intercept: Boolean) {
        this.intercept = intercept
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.i("===>scrollView", "=====-=>dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }
}
