package com.memo.deep.openmyeye.ui.adapter.recycle

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.bean.beanItem.*
import com.memo.deep.openmyeye.bean.my.PlayDetail
import com.memo.deep.openmyeye.ui.activity.BaseActivity
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.adapter.viewpager.NormalViewPagerAdapter
import com.memo.deep.openmyeye.ui.fragment.third.AuthorFragment
import com.memo.deep.openmyeye.ui.fragment.third.CardFragment
import com.memo.deep.openmyeye.ui.view.textView.FZTextView
import com.memo.deep.openmyeye.util.MyUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * 发现页面的adapter
 */
class FindAdapter(private val fragment: Fragment?, list: List<BaseMuti>,
                  val isWhite: Boolean = false, var activity: BaseActivity? = null,
                  val onViewPagerClick: (item: Any) -> Unit = {})
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
        addItemType(BaseMuti.dynamicInfoCard, R.layout.item_find_dynamic_info_card)
        // 自己加的
        addItemType(BaseMuti.playDetail, R.layout.item_play_detail_info)
        addItemType(BaseMuti.footerBean, R.layout.custom_footer)


    }

    private lateinit var helper: BaseViewHolder
    private lateinit var item: BaseMuti
    override fun convert(helper: BaseViewHolder, item: BaseMuti) {
        this.helper = helper
        this.item = item
        when (item.itemType) {
            BaseMuti.horizontalScrollCard -> initHorizontalScrollCard()
            BaseMuti.textCard -> initTextCard()
            BaseMuti.textCardFooter -> initTextCardFooter()
            BaseMuti.briefCard -> initBriefCard()
            BaseMuti.followCard -> initFollowCard()
            BaseMuti.videoSmallCard -> initVideoSmall()
            BaseMuti.squareCardCollection -> initSquareCardCollection()
            BaseMuti.videoCollectionWithBrief -> initVideoCollectionWithBrief()
            BaseMuti.dynamicInfoCard -> initDynamicInfoCard()
            BaseMuti.playDetail -> initPlayDetail()
            BaseMuti.footerBean -> initFooter()
        }
    }

    /**
     * 初始化viewpager
     */
    private fun initViewPager(viewPager: ViewPager, list: ArrayList<Fragment>, itemList: List<Any>) {
        viewPager.adapter = CardAdapter(list, fragment?.childFragmentManager)
        // 两个页面的空白间隙
        viewPager.pageMargin = ConvertUtils.dp2px(5f)
        val gestureDetector = GestureDetector(mContext, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                onViewPagerClick(itemList.get(viewPager.currentItem))
                return false
            }
        })

        viewPager.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
        }
    }

    /**
     * 初始化化第一部分
     */
    private fun initHorizontalScrollCard() {
        val viewPager = helper.getView<ViewPager>(R.id.vp_horizontal)
        val list = ArrayList<Fragment>()
        (item as HorizontalScrollCard).data.itemList.forEach {
            val element = CardFragment()
            val bundle = Bundle()
            bundle.putString("url", it.data.image)
            element.arguments = bundle
            list.add(element)
        }
        initViewPager(viewPager, list, (item as HorizontalScrollCard).data.itemList)
    }

    /**
     * 使用不是fragment进行适配，暂保留
     */
    private fun initHorizontal2() {
        val viewPager = helper.getView<ViewPager>(R.id.vp_horizontal)
        val list = (item as HorizontalScrollCard).data.itemList
        viewPager.adapter = NormalViewPagerAdapter(mContext, list)
        viewPager.offscreenPageLimit = 3
    }

    private fun initTextCard() {
        helper.setText(R.id.tv_header, (item as TextCard).data.text)
        setTextColorWhite(R.id.tv_header)
    }

    private fun initTextCardFooter() {
        helper.setText(R.id.tv_footer, (item as TextCard).data.text)
        setTextColorWhite(R.id.tv_footer)
    }

    private fun initBriefCard() {
        val briefCard = item as BriefCard
        helper.setText(R.id.tv_title, briefCard.data.title)
                .setText(R.id.tv_detail, briefCard.data.description)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(briefCard.data.icon)
    }

    private fun initFollowCard() {
        val followCard = item as FollowCard
        val detail = followCard.data.content.data.author.name +
                " /  #" + followCard.data.content.data.category
        helper.setText(R.id.tv_title, followCard.data.content.data.title)
                .setText(R.id.tv_detail, detail)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(followCard.data.content.data.cover.detail)
        helper.getView<SimpleDraweeView>(R.id.iv_header).setImageURI(followCard.data.header.icon)
        setTextColorWhite(R.id.tv_title, R.id.tv_detail)
    }

    private fun initVideoSmall() {
        val videoSmallCard = item as VideoSmallCard
        val detail = videoSmallCard.data.category + " /  #开眼精选"
        val minute = MyUtils.getMinute(videoSmallCard.data.duration)
        helper.setText(R.id.tv_title, videoSmallCard.data.title)
                .setText(R.id.tv_detail, detail)
                .setText(R.id.tv_time, minute)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(videoSmallCard.data.cover.detail)
        setTextColorWhite(R.id.tv_title, R.id.tv_detail, R.id.tv_time)
    }

    private var initSquare = false
    private fun initSquareCardCollection() {
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
        initViewPager(viewPager, list, squareCardCollection.data.itemList)
        initSquare = true

    }

    private var initVideo = false
    private fun initVideoCollectionWithBrief() {
        // 初始化了，就不在初始化了，解决滑动卡顿的问题
        if (initVideo) return
        // 必须添加点击事件，不然嵌套无法点击
        helper.addOnClickListener(R.id.vp_video_collection)
        helper.addOnClickListener(R.id.tv_watch)
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
        initViewPager(viewPager, list, videoCollectionWithBrief.data.itemList)
        initVideo = true
    }

    /**
     * 初始化评论
     */
    private fun initDynamicInfoCard() {
        val dynamicInfoCard = item as DynamicInfoCard
        val time = TimeUtils.millis2String(dynamicInfoCard.data.createDate
                , SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()))
        val duration = MyUtils.getMinute(dynamicInfoCard.data.simpleVideo.duration)
        helper.setText(R.id.tv_nickname, dynamicInfoCard.data.user.nickname)
                .setText(R.id.tv_comment, dynamicInfoCard.data.reply.message)
                .setText(R.id.tv_praise, dynamicInfoCard.data.reply.likeCount.toString())
                .setText(R.id.tv_title, dynamicInfoCard.data.simpleVideo.title)
                .setText(R.id.tv_type, "#" + dynamicInfoCard.data.simpleVideo.category)
                .setText(R.id.tv_time, time)
                .setText(R.id.tv_duration, duration)
        val pic = helper.getView<SimpleDraweeView>(R.id.iv)
        val header = helper.getView<SimpleDraweeView>(R.id.iv_header)
        pic.setImageURI(dynamicInfoCard.data.simpleVideo.cover.detail)
        header.setImageURI(dynamicInfoCard.data.user.avatar)
    }


    /**
     * 初始化播放详情页面的
     */
    private var isFirstPlay = true

    private fun initPlayDetail() {
        val playDetail = item as PlayDetail
        helper.setText(R.id.tv_collection, playDetail.collectionCount.toString())
                .setText(R.id.tv_share, playDetail.shareCount.toString())
                .setText(R.id.tv_reply, playDetail.replyCount.toString())
                .setText(R.id.tv_name1, playDetail.name1)
                .setText(R.id.tv_name2, playDetail.name2)
                .setText(R.id.tv_name3, playDetail.name3)
                .setText(R.id.tv_author, playDetail.author)
                .setText(R.id.tv_detail, playDetail.authorType)

        helper.getView<SimpleDraweeView>(R.id.iv1).setImageURI(playDetail.pic1)
        helper.getView<SimpleDraweeView>(R.id.iv2).setImageURI(playDetail.pic2)
        helper.getView<SimpleDraweeView>(R.id.iv3).setImageURI(playDetail.pic3)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(playDetail.authorPicUrl)

        if (isFirstPlay) {
            isFirstPlay = false
            helper.getView<FZTextView>(R.id.tv_title).startTyper(activity, playDetail.title)
            helper.getView<FZTextView>(R.id.tv_type).startTyper(activity, playDetail.type)
            helper.getView<FZTextView>(R.id.tv_description).startTyper(activity, playDetail.description)
        } else {
            helper.setText(R.id.tv_title, playDetail.title)
                    .setText(R.id.tv_type, playDetail.type)
                    .setText(R.id.tv_description, playDetail.description)
        }
    }

    /**
     * 添加footer
     */
    private fun initFooter() {
        val footerBean = item as FooterBean
        helper.setVisible(R.id.load_more_load_end_view, true)
        helper.setVisible(R.id.load_more_loading_view, false)
        helper.setTextColor(R.id.tv_footer, ContextCompat.getColor(mContext, footerBean.color))
    }

    /**
     * 有些界面需要白色的字体
     */
    private fun setTextColorWhite(vararg ids: Int) {
        if (!isWhite) {
            return
        }

        for (id in ids) {
            helper.getView<TextView>(id).setTextColor(ContextCompat.getColor(mContext, R.color.white))
        }
    }

}