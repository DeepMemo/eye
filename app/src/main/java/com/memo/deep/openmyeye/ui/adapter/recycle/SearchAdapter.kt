package com.memo.deep.openmyeye.ui.adapter.recycle

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

class SearchAdapter(val list: List<BaseMuti>) : BaseMultiItemQuickAdapter<BaseMuti, BaseViewHolder>(list) {

    override fun convert(helper: BaseViewHolder?, item: BaseMuti?) {
    }
}