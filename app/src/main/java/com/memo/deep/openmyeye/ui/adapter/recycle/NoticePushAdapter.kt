package com.memo.deep.openmyeye.ui.adapter.recycle

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.ItemListBean.NoticePushBean
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

class NoticePushAdapter(list: MutableList<NoticePushBean.Message>)
    : BaseMultiItemQuickAdapter<NoticePushBean.Message, BaseViewHolder>(list) {

    init {
        addItemType(BaseMuti.defalt, R.layout.item_notice_push)
    }

    override fun convert(helper: BaseViewHolder, item: NoticePushBean.Message) {
        helper.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_time, (helper.layoutPosition + 1).toString() + "天前")
                .setText(R.id.tv_content, item.content)
    }
}