package com.memo.deep.openmyeye.ui.adapter.recycle

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.*
import com.memo.deep.openmyeye.bean.my.PlayDetail
import com.memo.deep.openmyeye.bean.my.SearchMuti
import com.memo.deep.openmyeye.bean.my.SearchTitleMuti
import com.memo.deep.openmyeye.cache.ConstantCache
import com.memo.deep.openmyeye.code.ListVideo
import com.memo.deep.openmyeye.ui.activity.BaseActivity
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.adapter.viewpager.NormalViewPagerAdapter
import com.memo.deep.openmyeye.ui.fragment.third.AuthorFragment
import com.memo.deep.openmyeye.ui.fragment.third.CardFragment
import com.memo.deep.openmyeye.ui.view.textView.FZExpandTextView
import com.memo.deep.openmyeye.ui.view.textView.FZTextView
import com.memo.deep.openmyeye.util.MyUtils
import com.oushangfeng.pinnedsectionitemdecoration.utils.FullSpanUtil
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import java.text.SimpleDateFormat
import java.util.*


/**
 * 发现页面的adapter
 */
class FindAdapter(private val fragment: Fragment? = null, list: List<BaseMuti>,
                  val isWhite: Boolean = false, var activity: BaseActivity? = null,
                  val onViewPagerClick: (item: Any) -> Unit = {})
    : BaseMultiItemQuickAdapter<BaseMuti, BaseViewHolder>(list) {

    companion object {
        val TAG = "FindAdapter"
        val TYPE_HEADER = 1
        val TYPE_DATA = 2
    }

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
        addItemType(BaseMuti.videoBeanForClient, R.layout.item_play_detail_info)
        addItemType(BaseMuti.viewPagerFollowCard, R.layout.item_viewpager_follow_card)
        addItemType(BaseMuti.autoPlayFollowCard, R.layout.item_find_auto_play_follow_card)
        addItemType(BaseMuti.pictureFollowCard1, R.layout.item_find_picture_follow_card1)
        addItemType(BaseMuti.pictureFollowCard4, R.layout.item_find_picture_follow_card4)
        addItemType(BaseMuti.searchString, R.layout.item_search_string)
        addItemType(BaseMuti.searchTitle, R.layout.item_search_title)

        // 自己加的，videoBeanForClient和上面一个使用的是同一个布局
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
            BaseMuti.videoBeanForClient -> initVideoBeanForClient()
            // 是从squareCardCollection数据结构分类出来的
            BaseMuti.viewPagerFollowCard -> initViewPagerFollowCard()
            BaseMuti.autoPlayFollowCard -> initAutoPlayFollowCard()
            BaseMuti.pictureFollowCard1 -> initPictureFollowCard1()
            BaseMuti.pictureFollowCard4 -> initPictureFollowCard4()
            // 自己添加的
            BaseMuti.playDetail -> initPlayDetail()
            BaseMuti.footerBean -> initFooter()
            BaseMuti.searchString -> initSearchString()
            BaseMuti.searchTitle -> initSearchTitle()
        }
    }


    /**
     * 初始化viewpager
     */
    @SuppressLint("ClickableViewAccessibility")
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
//        val detail = followCard.data.content.data.author.name +
//                " /  #" + followCard.data.content.data.category
        val detail = followCard.data.header.description
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
        // 隐藏bug，如果刷新数据，这里的数据改变了，那么一样不会进行初始化。
        if (initSquare) return
        val squareCardCollection = item as SquareCardCollection<Any>
        val viewPager = helper.getView<ViewPager>(R.id.vp_square)
        helper.setText(R.id.tv_header, squareCardCollection.data.header.title)
        val list = ArrayList<Fragment>()
        squareCardCollection.data.itemList.forEach {
            val bundle = Bundle()
            when (it) {
                // 原来的
                is Banner -> {
                    bundle.putString(Constant.INTENT_URL, it.data.image)
                    val element = CardFragment()
                    element.arguments = bundle
                    list.add(element)
                }
            }
        }
        initViewPager(viewPager, list, squareCardCollection.data.itemList)
        initSquare = true

//        viewPager.adapter = NormalViewPagerAdapter(mContext, squareCardCollection.data.itemList)
    }

    private var initVideo = false
    private fun initVideoCollectionWithBrief() {
        // 初始化了，就不在初始化了，解决滑动卡顿的问题
        if (initVideo) return
        // 上面标题
        val videoCollectionWithBrief = item as VideoCollectionWithBrief
        helper.setText(R.id.tv_title, videoCollectionWithBrief.data.header.title)
                .setText(R.id.tv_detail, videoCollectionWithBrief.data.header.description)
                // 必须添加点击事件，不然嵌套无法点击
                .addOnClickListener(R.id.vp_video_collection)
                .addOnClickListener(R.id.tv_watch)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(videoCollectionWithBrief.data.header.icon)
        // 下面ViewPager
        val viewPager = helper.getView<ViewPager>(R.id.vp_video_collection)
        val list = ArrayList<Fragment>()
        var index = 0
        ConstantCache.data = videoCollectionWithBrief.data.itemList
        videoCollectionWithBrief.data.itemList.forEach {
            val bundle = Bundle()
            bundle.putInt(Constant.INTENT_ID, index++)
            val element = AuthorFragment()
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
                .addOnClickListener(R.id.ll_video)
        val pic = helper.getView<SimpleDraweeView>(R.id.iv)
        val header = helper.getView<SimpleDraweeView>(R.id.iv_header)
        pic.setImageURI(dynamicInfoCard.data.simpleVideo.cover.detail)
        header.setImageURI(dynamicInfoCard.data.user.avatar)
    }

    private var isFirstPlay = true
    /**
     * 初始化播放详情页面的
     */
    private fun initPlayDetail() {
        val playDetail = item as PlayDetail
        helper.setText(R.id.tv_collection, playDetail.collectionCount.toString())
                .setText(R.id.tv_share, playDetail.shareCount.toString())
                .setText(R.id.tv_reply, playDetail.replyCount.toString())
                .setText(R.id.tv_author, playDetail.author)
                .setText(R.id.tv_detail, playDetail.authorType)

        // 因tag可能不是三个,动态显示
        val nameList = listOf(R.id.tv_name1, R.id.tv_name2, R.id.tv_name3)
        val picList = listOf(R.id.iv1, R.id.iv2, R.id.iv3)
        val llList = listOf(R.id.ll_fl1, R.id.ll_fl2, R.id.ll_fl3)
        var index = 0
        playDetail.nameList.forEach {
            if (index > 2) return@forEach
            helper.setText(nameList.get(index), it)
            helper.setVisible(llList.get(index), true)
            helper.getView<SimpleDraweeView>(picList.get(index)).setImageURI(playDetail.picList.get(index))
            index++
        }
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
     * 初始化播放详情页面的
     */
    private fun initVideoBeanForClient() {
        val videoBeanForClient = item as VideoBeanForClient
        helper.setText(R.id.tv_collection, videoBeanForClient.consumption.collectionCount.toString())
                .setText(R.id.tv_share, videoBeanForClient.consumption.shareCount.toString())
                .setText(R.id.tv_reply, videoBeanForClient.consumption.replyCount.toString())
                .setText(R.id.tv_author, videoBeanForClient.author.name)
                .setText(R.id.tv_detail, videoBeanForClient.author.description)

        // 因tag可能不是三个,动态显示
        val nameList = listOf(R.id.tv_name1, R.id.tv_name2, R.id.tv_name3)
        val picList = listOf(R.id.iv1, R.id.iv2, R.id.iv3)
        val llList = listOf(R.id.ll_fl1, R.id.ll_fl2, R.id.ll_fl3)
        var index = 0
        videoBeanForClient.tags.forEach {
            if (index > 2) return@forEach
            helper.setText(nameList.get(index), it.name)
            helper.setVisible(llList.get(index), true)
            helper.getView<SimpleDraweeView>(picList.get(index)).setImageURI(it.headerImage)
            index++
        }
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(videoBeanForClient.author.icon)
        // 第一次进入，开始打字动画
        if (isFirstPlay) {
            isFirstPlay = false
            helper.getView<FZTextView>(R.id.tv_title).startTyper(activity, videoBeanForClient.title)
            helper.getView<FZTextView>(R.id.tv_type).startTyper(activity, videoBeanForClient.category)
            helper.getView<FZTextView>(R.id.tv_description).startTyper(activity, videoBeanForClient.description)
        } else {
            helper.setText(R.id.tv_title, videoBeanForClient.title)
                    .setText(R.id.tv_type, videoBeanForClient.category)
                    .setText(R.id.tv_description, videoBeanForClient.description)
        }
    }

    private var initViewpagerFollowCard = false
    private fun initViewPagerFollowCard() {
        // 初始化了，就不在初始化了，解决滑动卡顿的问题
        // 隐藏bug，如果刷新数据，这里的数据改变了，那么一样不会进行初始化。
        if (initViewpagerFollowCard) return
        val squareCardCollection = item as SquareCardCollection<Any>
        helper.setText(R.id.tv_subtitle, squareCardCollection.data.header.subTitle)
                .setText(R.id.tv_title, squareCardCollection.data.header.title)
        val viewPager = helper.getView<ViewPager>(R.id.vp_follow_card)
        val list = ArrayList<Fragment>()
        var index = 0
        ConstantCache.data = squareCardCollection.data.itemList
        squareCardCollection.data.itemList.forEach {
            val bundle = Bundle()
            when (it) {
                // 其他bean里的，新的
                is FollowCard -> {
                    // 直接判断是第几个
                    bundle.putInt(Constant.INTENT_ID, index++)
                    val element = AuthorFragment()
                    element.arguments = bundle
                    list.add(element)
                }
            }
        }
        initViewPager(viewPager, list, squareCardCollection.data.itemList)
        initViewpagerFollowCard = true
    }


    private fun initAutoPlayFollowCard() {
        val autoPlayFollowCard = item as AutoPlayFollowCard
        val data = autoPlayFollowCard.data.content.data
        helper.setText(R.id.tv_author, data.owner.nickname)
                .setText(R.id.tv_collection, data.consumption.collectionCount.toString())
                .setText(R.id.tv_reply, data.consumption.replyCount.toString())
                .setText(R.id.tv_time, MyUtils.changeTime(data.createTime))

        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(autoPlayFollowCard.data.header.icon)
        helper.getView<FZExpandTextView>(R.id.tv_description).setContent(data.description)
        addTags(data.tags)
        initCoverVideo(autoPlayFollowCard)
    }

    private fun addTags(tags: List<*>) {
        val linearLayout = helper.getView<LinearLayout>(R.id.ll_tag)
        //bug 这里不清楚，tag会一直添加,大概因为复用的问题
        linearLayout.removeAllViews()
        var index = 0
        for (tag in tags) {
            val fzTextView = FZTextView(mContext)
            val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            // 后面添加的tag增加margin
            if (index != 0) {
                layoutParams.setMargins(5, 0, 0, 0)
            }
            fzTextView.layoutParams = layoutParams
            fzTextView.textSize = 12f
            fzTextView.setTextColor(ContextCompat.getColor(mContext, R.color.comment_blue))
            fzTextView.setPadding(10, 5, 10, 5)
            when (tag) {
                is AutoPlayFollowCard.Data.Content.Data.Tag -> fzTextView.text = tag.name
                is PictureFollowCard.Data.Content.Data.Tag -> fzTextView.text = tag.name
            }
            fzTextView.applyCustomFont(false)
            fzTextView.setBackgroundResource(R.drawable.shape_communit_grayy)
            linearLayout.addView(fzTextView)
            index++
        }
    }

    private fun initCoverVideo(autoPlayFollowCard: AutoPlayFollowCard) {
        val gsyVideoOption = GSYVideoOptionBuilder()
        val imageView = SimpleDraweeView(mContext)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageURI(autoPlayFollowCard.data.content.data.cover.detail)
        gsyVideoOption
                .setThumbImageView(imageView)
                .setRotateViewAuto(false)
                // 小屏不支持滑动
                .setIsTouchWiget(false)
                .setLockLand(false)
                // 根据尺寸全屏自动选择横屏还是竖屏
//                .setAutoFullWithSize(true)
                //音频焦点冲突时是否释放
                .setReleaseWhenLossAudio(false)
                .setUrl(autoPlayFollowCard.data.content.data.playUrl)
                .setEnlargeImageRes(R.drawable.full_screen)
                .setShrinkImageRes(com.shuyu.gsyvideoplayer.R.drawable.video_shrink)
        val listVideo = helper.getView<ListVideo>(R.id.detail_player)
        listVideo.fullscreenButton.setOnClickListener {
            listVideo.startWindowFullscreen(mContext, false, true)
        }
        listVideo.playPosition = helper.layoutPosition
        listVideo.tag = TAG
        gsyVideoOption.build(listVideo)
    }


    private fun initPictureFollowCard1() {
        val pictureFollowCard = item as PictureFollowCard
        val data = pictureFollowCard.data.content.data
        helper.setText(R.id.tv_author, data.owner.nickname)
                .setText(R.id.tv_collection, data.consumption.collectionCount.toString())
                .setText(R.id.tv_reply, data.consumption.replyCount.toString())
                .setText(R.id.tv_time, MyUtils.changeTime(data.createTime))
        helper.getView<SimpleDraweeView>(R.id.iv_pic).setImageURI(data.cover.detail)
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(pictureFollowCard.data.header.icon)
        val fzTextView = helper.getView<FZExpandTextView>(R.id.tv_description)
        if (data.description == "") {
            fzTextView.visibility = View.GONE
        } else {
            fzTextView.visibility = View.VISIBLE
            fzTextView.setContent(data.description)
        }
        addTags(data.tags)
    }

    private fun initPictureFollowCard4() {
        val pictureFollowCard = item as PictureFollowCard
        val data = pictureFollowCard.data.content.data
        helper.setText(R.id.tv_author, data.owner.nickname)
                .setText(R.id.tv_collection, data.consumption.collectionCount.toString())
                .setText(R.id.tv_reply, data.consumption.replyCount.toString())
                .setText(R.id.tv_time, MyUtils.changeTime(data.createTime))
        helper.getView<SimpleDraweeView>(R.id.iv).setImageURI(pictureFollowCard.data.header.icon)
        val fzTextView = helper.getView<FZExpandTextView>(R.id.tv_description)
        if (data.description == "") {
            fzTextView.visibility = View.GONE
        } else {
            fzTextView.setContent(data.description)
        }
        val list = listOf(
                helper.getView<SimpleDraweeView>(R.id.iv_pic1),
                helper.getView<SimpleDraweeView>(R.id.iv_pic2),
                helper.getView<SimpleDraweeView>(R.id.iv_pic3),
                helper.getView<SimpleDraweeView>(R.id.iv_pic4))
        var index = 0
        for (url in data.urls) {
            if (index < 4) {
                list.get(index++).setImageURI(url)
            }
        }
    }

    private fun initSearchString() {
        val searchMuti = item as SearchMuti
        var color = ContextCompat.getColor(mContext, R.color.comment_blue)
        if (searchMuti.isColorMore) {
            color = ContextCompat.getColor(mContext, R.color.word_black)
        }
        helper.setText(R.id.tv, Html.fromHtml(searchMuti.name))
                .setTextColor(R.id.tv, color)

    }

    private fun initSearchTitle() {
        val stringMuti = item as SearchTitleMuti
        helper.setText(R.id.tv, stringMuti.name)
                .setVisible(R.id.tv_del, stringMuti.name == mContext.getString(R.string.search_history))
                .addOnClickListener(R.id.tv)
                .addOnClickListener(R.id.tv_del)
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

    /**
     * 以下处理粘性头部
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, BaseMuti.searchTitle)
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        FullSpanUtil.onViewAttachedToWindow(holder, this, BaseMuti.searchTitle)

    }

}