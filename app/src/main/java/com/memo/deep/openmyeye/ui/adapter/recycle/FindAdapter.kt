package com.memo.deep.openmyeye.ui.adapter.recycle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.FindBean
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.fragment.viewPagerFragment.AuthorFragment
import com.memo.deep.openmyeye.ui.fragment.viewPagerFragment.CardFragment
import com.memo.deep.openmyeye.util.MyUtils
import java.util.*

/**
 * 发现页面的adapter
 */
class FindAdapter(val context: Fragment, list: List<FindBean.Item>)
    : BaseMultiItemQuickAdapter<FindBean.Item, BaseViewHolder>(list) {

    init {
        addItemType(FindBean.Item.horizontalScrollCard, R.layout.item_find_horizontal_scroll_card)
        addItemType(FindBean.Item.textCard, R.layout.item_find_text_card)
        addItemType(FindBean.Item.textCardFooter, R.layout.item_find_text_card_footer2)
        addItemType(FindBean.Item.briefCard, R.layout.item_find_brief_card)
        addItemType(FindBean.Item.followCard, R.layout.item_find_follow_card)
        addItemType(FindBean.Item.videoSmallCard, R.layout.item_find_video_small_card)
        addItemType(FindBean.Item.squareCardCollection, R.layout.item_find_square_card_collection)
        addItemType(FindBean.Item.videoCollectionWithBrief, R.layout.item_find_video_collection_with_brief)
    }

    private lateinit var item: FindBean.Item
    private lateinit var helper: BaseViewHolder
    override fun convert(helper: BaseViewHolder, item: FindBean.Item) {
        this.item = item
        this.helper = helper
        when (item.type) {
            "horizontalScrollCard" -> initHorizontal()
            "textCard" -> {
                if ("footer2" == item.data.type) {
                    initTextCardFooter()
                } else {
                    initTextCard()
                }
            }
            "briefCard" -> initBriefCard()
            "followCard" -> initFollowCard()
            "videoSmallCard" -> initVideoSmall()
            "squareCardCollection" -> initSquareCard()
            "videoCollectionWithBrief" -> initVideoCollection()
        }
    }

    /**
     * 初始化化第一部分
     */
    private fun initHorizontal() {
        val viewPager = helper.getView<ViewPager>(R.id.vp)
        val list = ArrayList<Fragment>()
        item.data.itemList.forEach {
            val element = CardFragment()
            val bundle = Bundle()
            bundle.putString("url",it.data.image)
            element.arguments = bundle
            list.add(element)
        }
        viewPager.adapter = CardAdapter(list, context.childFragmentManager)
    }

    private fun initTextCard() {
        helper.setText(R.id.tv_header, item.data.text)
    }

    private fun initTextCardFooter() {
        helper.setText(R.id.tv_footer, item.data.text)
    }

    private fun initBriefCard() {
        helper.setText(R.id.tv_title, item.data.title)
                .setText(R.id.tv_detail, item.data.description)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(item.data.icon)

    }

    private fun initFollowCard() {
        val detail = item.data.content.data.author.name + " /  #" + item.data.content.data.category
        val minute = MyUtils.getMinute(item.data.content.data.duration)
        helper.setText(R.id.tv_title, item.data.content.data.title)
                .setText(R.id.tv_detail, detail)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(item.data.icon)
    }

    private fun initVideoSmall() {
        val detail = item.data.content.data.category + " /  #开眼精选"
        val minute = MyUtils.getMinute(item.data.content.data.duration)
        helper.setText(R.id.tv_title, item.data.content.data.title)
                .setText(R.id.tv_detail, detail)
                .setText(R.id.tv_time, minute)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(item.data.content.data.cover.detail)
    }

    private fun initSquareCard() {
        val viewPager = helper.getView<ViewPager>(R.id.vp)
        val list = ArrayList<Fragment>()
        item.data.itemList.forEach {
            val element = CardFragment()
            val bundle = Bundle()
            bundle.putString("url",it.data.image)
            element.arguments = bundle
            list.add(element)
        }
        viewPager.adapter = CardAdapter(list, context.childFragmentManager)
    }

    private fun initVideoCollection() {
        // 上面标题
        helper.setText(R.id.tv_title, item.data.content.data.title)
                .setText(R.id.tv_detail, item.data.header.description)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(item.data.header.icon)
        // 下面ViewPager
        val viewPager = helper.getView<ViewPager>(R.id.vp)
        val list = ArrayList<Fragment>()
        item.data.itemList.forEach {
            val element = AuthorFragment()
            val bundle = Bundle()
            bundle.putSerializable("item", it)
            element.arguments = bundle
            list.add(element)
        }
        viewPager.adapter = CardAdapter(list, context.childFragmentManager)
    }
}