package com.memo.deep.openmyeye.ui.view.textView

import android.content.Context
import android.util.AttributeSet
import com.ctetin.expandabletextviewlibrary.ExpandableTextView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.cache.FontCache


/**
 * 方正兰亭中粗黑简体的TextView
 */
open class FZExpandTextView : ExpandableTextView {
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


}