package com.memo.deep.openmyeye.ui.view.textView

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.memo.deep.openmyeye.util.FontCache


/**
 * Lobster的TextView
 */
class LoTextView : TextView {
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
        val customFont = FontCache.getTypeface("fonts/Lobster-1.4.otf", context)
        typeface = customFont
    }

}