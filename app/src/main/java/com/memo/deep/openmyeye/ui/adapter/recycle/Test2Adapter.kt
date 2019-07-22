package com.memo.deep.openmyeye.ui.adapter.recycle

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.my.TestBean

class Test2Adapter(list: MutableList<TestBean>)
    : BaseMultiItemQuickAdapter<TestBean, BaseViewHolder>(list) {

    init {
        addItemType(BaseMuti.defalt, R.layout.item_test)
    }

    override fun convert(helper: BaseViewHolder, item: TestBean) {
        helper.setText(R.id.tv, item.name)
    }
}