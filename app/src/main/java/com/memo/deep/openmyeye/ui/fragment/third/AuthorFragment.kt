package com.memo.deep.openmyeye.ui.fragment.third

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.itemBean.FollowCard
import com.memo.deep.openmyeye.bean.itemBean.VideoCollectionWithBrief
import com.memo.deep.openmyeye.cache.ConstantCache
import kotlinx.android.synthetic.main.item_find_follow_card.view.*

/**
 * viewPager 卡片的具体布局
 */
class AuthorFragment : Fragment() {
    val list = ConstantCache.data as List<*>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_author, container, false)
        val index = arguments?.getInt(Constant.INTENT_ID) ?: 0
        val item = list.get(index)
        when (item) {
            is VideoCollectionWithBrief.Data.Item -> {
                inflate.tv_title.text = item.data.title
                inflate.tv_detail.text = item.data.description
                inflate.iv.setImageURI(item.data.cover.detail)
            }
            // followCard需要使用这个布局
            is FollowCard -> {
                inflate = LayoutInflater.from(activity).inflate(R.layout.item_find_follow_card, container, false)
                inflate.tv_title.text = item.data.header.title
                inflate.tv_detail.text = item.data.header.description
                inflate.iv.setImageURI(item.data.content.data.cover.detail)
                inflate.iv_header.setImageURI(item.data.content.data.author.icon)
            }
        }

        return inflate
    }
}