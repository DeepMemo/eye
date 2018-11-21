package com.memo.deep.openmyeye.ui.adapter.recycle

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.BriefCard

class CategoryAdapter(resId: Int, list: List<BaseMuti>) : BaseItemDraggableAdapter<BaseMuti, BaseViewHolder>(resId, list) {
    override fun convert(helper: BaseViewHolder, item: BaseMuti) {
        val briefCard = item as BriefCard
        helper.setText(R.id.tv_title, briefCard.data.title)
                .setText(R.id.tv_detail, briefCard.data.description)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(briefCard.data.icon)
        helper.setVisible(R.id.iv_category, true)
    }
}