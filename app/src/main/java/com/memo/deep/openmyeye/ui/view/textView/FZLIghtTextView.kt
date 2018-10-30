package com.memo.deep.openmyeye.ui.view.textView

import android.content.Context
import android.util.AttributeSet
import com.hanks.htextview.typer.TyperTextView
import com.memo.deep.openmyeye.util.FontCache


/**
 * 方正兰亭中粗黑简体的TextView
 */
class FZLIghtTextView : TyperTextView {
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
        val customFont = FontCache.getTypeface("fonts/FZLanTingHeiS-L-GB-Regular.TTF", context)
        typeface = customFont
    }

}