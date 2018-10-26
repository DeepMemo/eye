package com.memo.deep.openmyeye.ui.view.text_view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.memo.deep.openmyeye.util.FontCache


/**
 * 方正兰亭中粗黑简体的TextView
 */
class FZTextView : TextView {
    constructor(context: Context) : super(context) {
        applyCustomFont(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        applyCustomFont(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        applyCustomFont(context)
    }


    private fun applyCustomFont(context: Context) {
        val customFont = FontCache.getTypeface("fonts/FZLanTingHeiS-DB1-GB-Regular.TTF", context)
        typeface = customFont
    }

}