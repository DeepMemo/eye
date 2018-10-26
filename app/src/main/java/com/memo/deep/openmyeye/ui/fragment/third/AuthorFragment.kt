package com.memo.deep.openmyeye.ui.fragment.third

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.beanItem.VideoCollectionWithBrief
import kotlinx.android.synthetic.main.item_find_follow_card.view.*

/**
 * viewPager 卡片的具体布局
 */
class AuthorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val item = arguments?.getSerializable("item") as VideoCollectionWithBrief.Data.Item
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_author, container, false)
        inflate.tv_title.text = item.data.title
        inflate.tv_detail.text = item.data.description
        inflate.iv.setImageURI(item.data.cover.detail)
        return inflate
    }
}