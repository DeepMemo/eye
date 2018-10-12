package com.memo.deep.openmyeye.ui.adapter.viewpager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.find.HorizontalScrollCard
import com.memo.deep.openmyeye.bean.find.SquareCardCollection
import com.memo.deep.openmyeye.bean.find.VideoCollectionWithBrief
import kotlinx.android.synthetic.main.item_find_follow_card.view.*

/**
 * 测试页面滑动卡顿是否是因为Fragment，结果不是，舍弃使用viewpager，使用RecycleView代替
 * 发现页面中viewpager的适配器，使用轻量级，不适用FragmentPagerAdapter
 */
class NormalViewPagerAdapter(val context: Context?, val list: List<Any>) : PagerAdapter() {

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === p1
    }

    override fun getCount(): Int {

        return list.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = list.get(0)
        var view = View(context)
        when (item) {
            is HorizontalScrollCard.Data.Item -> {
                view = LayoutInflater.from(context).inflate(R.layout.fragment_card, null)
                view.findViewById<SimpleDraweeView>(R.id.iv).setImageURI(item.data.image)
            }
            is SquareCardCollection.Data.Item -> {
                view = LayoutInflater.from(context).inflate(R.layout.fragment_card, null)
                view.findViewById<SimpleDraweeView>(R.id.iv).setImageURI(item.data.image)
            }
            is VideoCollectionWithBrief.Data.Item -> {
                view = LayoutInflater.from(context).inflate(R.layout.item_find_follow_card, null)
                view.tv_title.text = item.data.title
                view.tv_detail.text = item.data.description
                view.iv.setImageURI(item.data.cover.detail)
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }
}