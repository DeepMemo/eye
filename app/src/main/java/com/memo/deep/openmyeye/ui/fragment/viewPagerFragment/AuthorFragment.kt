package com.memo.deep.openmyeye.ui.fragment.viewPagerFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.FindBean
import kotlinx.android.synthetic.main.item_find_follow_card.view.*

/**
 * viewPager 卡片的具体布局
 */
class AuthorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val item = arguments?.getSerializable("item") as FindBean.Item.Data.Item
        val url = arguments?.get("url") ?: ""
        val inflate = LayoutInflater.from(activity).inflate(R.layout.item_find_follow_card, container, false)
        inflate.iv_select.visibility = View.GONE
        inflate.tv_title.text = item.data.header.title
        inflate.tv_detail.text = item.data.header.description
        inflate.iv.setImageURI(item.data.header.icon)
        return inflate
    }
}