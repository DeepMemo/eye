package com.memo.deep.openmyeye.ui.view.textView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.EditText
import com.blankj.utilcode.util.ConvertUtils
import com.memo.deep.openmyeye.R


class PicHintEditTextView : EditText {
    var searchSize = 14f
    lateinit var paint: Paint

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        InitPaint(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    private fun InitPaint(attributeSet: AttributeSet) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = ContextCompat.getColor(context, R.color.word_gray)
        paint.textSize = ConvertUtils.sp2px(12f).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        DrawSearchIcon(canvas)
    }


    private fun DrawSearchIcon(canvas: Canvas) {
        if (this.text.toString().isEmpty()) {
            val text = context.getString(R.string.search_hint)
            val textWidth = paint.measureText(text)
            val textHeight = getFontLeading(paint)

            val dx = (width - searchSize - textWidth - 8) / 2
            val dy = (height - searchSize) / 2

            canvas.save()
            var mDrawable: Drawable = context.getDrawable(R.drawable.search_72px)
            // 完全不知道这些变量的实际意义，瞎掰的，searchSize其实指的图片14dp
            mDrawable.setBounds(0, (height - ConvertUtils.sp2px(searchSize)) / 2, ConvertUtils.sp2px(searchSize), ConvertUtils.sp2px(searchSize))
            mDrawable.draw(canvas)
            canvas.drawText(text, ConvertUtils.sp2px(searchSize) + 8.toFloat(), scrollY + (height - (height - textHeight) / 2) - paint.fontMetrics.bottom, paint)
            canvas.restore()
        }
    }

    fun getFontLeading(paint: Paint): Float {
        val fm = paint.getFontMetrics()
        return fm.bottom - fm.top
    }
}