package com.memo.deep.openmyeye.code

import android.content.Context
import android.util.AttributeSet
import com.memo.deep.openmyeye.R

class ListVideo : CoverVideo {

    constructor(context: Context) : super(context)
    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun getLayoutId(): Int {
        return R.layout.item_video_list
    }

    /**
     * 故意覆写，不需要去设置margin
     */
    override fun setProgressBar(show: Boolean) {
    }

}