package com.memo.deep.openmyeye.ui.view.textView

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.TextView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.cache.FontCache
import com.memo.deep.openmyeye.ui.activity.BaseActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


/**
 * 方正兰亭中粗黑简体的TextView
 */
open class FZTextView : TextView {
    // 不知道为什么不能延迟初始化，没意义，都会被覆盖掉
    var charIncrease: Int = 1
    var typerSpeed: Int = 100
    var isSameComplete: Boolean = false
    var isBold: Boolean = true

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init(context, attributeSet, defStyleAttr)
    }

    private fun init(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.FZTextView)
        isBold = attributes.getBoolean(R.styleable.FZTextView_bold, true)
        isSameComplete = attributes.getBoolean(R.styleable.FZTextView_sameComplete, false)
        charIncrease = attributes.getInt(R.styleable.FZTextView_charIncrease, 1)
        typerSpeed = attributes.getInt(R.styleable.FZTextView_typerSpeed, 50)
        attributes.recycle()
        applyCustomFont(isBold)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    public fun applyCustomFont(isBold: Boolean) {
        var fontName = if (isBold) "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF" else "FZLanTingHeiS-L-GB-Regular.TTF"
        val customFont = FontCache.getTypeface(fontName, context)
        typeface = customFont
    }

    public fun startTyper(activity: BaseActivity?, text: String) {
        if (activity == null || text.isEmpty()) {
            return
        }

        // 第一步监听获取高度，固定高度
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //避免重复监听
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                layoutParams.height = measuredHeight
            }
        })

        //第二步，设置文字，为下一步得到测量高度
        // 设置透明颜色，避免闪一下。
        val orginColor = this.currentTextColor
        this.text = text
        setTextColor(ContextCompat.getColor(context, R.color.transparent))

        // 第三步，判断是否是共同完成时间，设置速度
        val length = if (text.isEmpty()) 1 else text.length
        // 1.5秒内完成，文字多，每个文字间隔时间短一点，文字少，每个文字间隔时间多一点（因为有floable，可能不会同步）
        val speed = 1500 / length

        // 第四步，进行循环设置文字
        // 从一半开始打字
        var index = length / 2
        // 这里会存在一个问题，弄了好久才发现，如果上游发射数据过快，那么下游来不及处理，
        // 使用背压，或者放在同一线程
        Flowable.interval(0, speed.toLong(), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
//                .onBackpressureBuffer()
                .map {
                    return@map text.substring(0, index)
                }
                .take((length - index + 1).toLong())
                .observeOn(AndroidSchedulers.mainThread())
                // 为了避免此类问题，就直接在销毁页面才取消订阅
                .compose(activity.getProvider().bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe {
                    // 之前设置为了透明，回复成原来的颜色，可以优化调用一次就好了
                    setTextColor(orginColor)
                    this.text = it
                    index++
                }
    }

}