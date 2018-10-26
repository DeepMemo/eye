package com.memo.deep.openmyeye.ui.view.text_view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.memo.deep.openmyeye.util.FontCache


/**
 * Futura-CondensedMedium的TextView
 */
class FuturaTextView : TextView {
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
        val customFont = FontCache.getTypeface("fonts/Futura-CondensedMedium.ttf", context)
        typeface = customFont
    }

}