package com.memo.deep.openmyeye.ui.adapter.recycle

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.ItemListBean.AllTypeBean

class AllTypeAdapter(list: MutableList<AllTypeBean.Item>, val method: (index: Int) -> Unit)
    : BaseMultiItemQuickAdapter<AllTypeBean.Item, BaseViewHolder>(list) {

    init {
        addItemType(AllTypeBean.squareCard, R.layout.item_type)
        addItemType(AllTypeBean.rectangleCard, R.layout.item_type1)
    }

    override fun convert(helper: BaseViewHolder, item: AllTypeBean.Item) {
        helper.setText(R.id.tv_type, item.data.title)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(item.data.image)
    }
}
