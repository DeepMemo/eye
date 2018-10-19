package com.memo.deep.openmyeye.ui.adapter.recycle

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.memo.deep.openmyeye.R

class TestAdapter(resId: Int, var list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(resId,list) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_header, item)
    }
}