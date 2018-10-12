package com.memo.deep.openmyeye.ui.adapter.recycle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.find.*
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.adapter.viewpager.NormalViewPagerAdapter
import com.memo.deep.openmyeye.ui.fragment.viewPagerFragment.AuthorFragment
import com.memo.deep.openmyeye.ui.fragment.viewPagerFragment.CardFragment
import com.memo.deep.openmyeye.util.MyUtils
import java.util.*

/**
 * 发现页面的adapter
 */
class FindAdapter(val context: Fragment, list: List<BaseMuti>)
    : BaseMultiItemQuickAdapter<BaseMuti, BaseViewHolder>(list) {

    init {
        addItemType(BaseMuti.horizontalScrollCard, R.layout.item_find_horizontal_scroll_card)
        addItemType(BaseMuti.textCard, R.layout.item_find_text_card)
        addItemType(BaseMuti.textCardFooter, R.layout.item_find_text_card_footer2)
        addItemType(BaseMuti.briefCard, R.layout.item_find_brief_card)
        addItemType(BaseMuti.followCard, R.layout.item_find_follow_card)
        addItemType(BaseMuti.videoSmallCard, R.layout.item_find_video_small_card)
        addItemType(BaseMuti.squareCardCollection, R.layout.item_find_square_card_collection)
        addItemType(BaseMuti.videoCollectionWithBrief, R.layout.item_find_video_collection_with_brief)
    }

    private lateinit var helper: BaseViewHolder
    private lateinit var item: BaseMuti
    override fun convert(helper: BaseViewHolder, item: BaseMuti) {
        this.helper = helper
        this.item = item
        when (item.itemType) {
            BaseMuti.horizontalScrollCard -> initHorizontal()
            BaseMuti.textCard -> initTextCard()
            BaseMuti.textCardFooter -> initTextCardFooter()
            BaseMuti.briefCard -> initBriefCard()
            BaseMuti.followCard -> initFollowCard()
            BaseMuti.videoSmallCard -> initVideoSmall()
            BaseMuti.squareCardCollection -> initSquareCard()
            BaseMuti.videoCollectionWithBrief -> initVideoCollection()
        }
    }

    /**
     * 初始化化第一部分
     */
    private fun initHorizontal() {
        val viewPager = helper.getView<ViewPager>(R.id.vp_horizontal)
        val list = ArrayList<Fragment>()
        (item as HorizontalScrollCard).data.itemList.forEach {
            val element = CardFragment()
            val bundle = Bundle()
            bundle.putString("url", it.data.image)
            element.arguments = bundle
            list.add(element)
        }
        viewPager.adapter = CardAdapter(list, context.childFragmentManager)
        viewPager.offscreenPageLimit = 3
    }

    /**
     * 初始化化第一部分
     */
    private fun initHorizontal2() {
        val viewPager = helper.getView<ViewPager>(R.id.vp_horizontal)
        val list = (item as HorizontalScrollCard).data.itemList
        viewPager.adapter = NormalViewPagerAdapter(context.activity, list)
        viewPager.offscreenPageLimit = 3
    }

    private fun initTextCard() {
        helper.setText(R.id.tv_header, (item as TextCard).data.text)
    }

    private fun initTextCardFooter() {
        helper.setText(R.id.tv_footer, (item as TextCard).data.text)
    }

    private fun initBriefCard() {
        val briefCard = item as BriefCard
        helper.setText(R.id.tv_title, briefCard.data.title)
                .setText(R.id.tv_detail, briefCard.data.description)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(briefCard.data.icon)
    }

    private fun initFollowCard() {
        val followCard = item as FollowCard
        val detail = followCard.data.content.data.author.name + " /  #" + followCard.data.content.data.category
        helper.setText(R.id.tv_title, followCard.data.content.data.title)
                .setText(R.id.tv_detail, detail)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(followCard.data.content.data.cover.detail)
        helper.getView<SimpleDraweeView>(R.id.iv_header).setImageURI(followCard.data.header.icon)
    }

    private fun initVideoSmall() {
        val videoSmallCard = item as VideoSmallCard
        val detail = videoSmallCard.data.category + " /  #开眼精选"
        val minute = MyUtils.getMinute(videoSmallCard.data.duration)
        helper.setText(R.id.tv_title, videoSmallCard.data.title)
                .setText(R.id.tv_detail, detail)
                .setText(R.id.tv_time, minute)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(videoSmallCard.data.cover.detail)
    }

    private var initSquare = false
    private fun initSquareCard() {
        // 初始化了，就不在初始化了，解决滑动卡顿的问题
        if (initSquare) return

        val squareCardCollection = item as SquareCardCollection
        val viewPager = helper.getView<ViewPager>(R.id.vp_square)
        val list = ArrayList<Fragment>()
        squareCardCollection.data.itemList.forEach {
            val element = CardFragment()
            val bundle = Bundle()
            bundle.putString("url", it.data.image)
            element.arguments = bundle
            list.add(element)
        }
        viewPager.adapter = CardAdapter(list, context.childFragmentManager)
        viewPager.offscreenPageLimit = 3
        initSquare = true

    }

    private var initVideo = false
    private fun initVideoCollection() {
        // 初始化了，就不在初始化了，解决滑动卡顿的问题
        if (initVideo) return
        // 上面标题
        val videoCollectionWithBrief = item as VideoCollectionWithBrief
        helper.setText(R.id.tv_title, videoCollectionWithBrief.data.header.title)
                .setText(R.id.tv_detail, videoCollectionWithBrief.data.header.description)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(videoCollectionWithBrief.data.header.icon)
        // 下面ViewPager
        val viewPager = helper.getView<ViewPager>(R.id.vp_video_collection)
        val list = ArrayList<Fragment>()
        videoCollectionWithBrief.data.itemList.forEach {
            val element = AuthorFragment()
            val bundle = Bundle()
            bundle.putSerializable("item", it)
            element.arguments = bundle
            list.add(element)
        }
        viewPager.adapter = CardAdapter(list, context.childFragmentManager)
        viewPager.offscreenPageLimit = 3
        initVideo = true
    }

}