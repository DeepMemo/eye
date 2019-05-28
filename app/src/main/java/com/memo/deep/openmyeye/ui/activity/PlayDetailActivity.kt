package com.memo.deep.openmyeye.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.FooterBean
import com.memo.deep.openmyeye.bean.itemBean.VideoBeanForClient
import com.memo.deep.openmyeye.bean.my.PlayDetail
import com.memo.deep.openmyeye.cache.ConstantCache
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import com.memo.deep.openmyeye.ui.mvp.contract.IPlayDetailContract
import com.memo.deep.openmyeye.ui.mvp.presenter.PlayDetailPresenter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.activity_play_detail.*


/**
 * 视频播放页面
 */
class PlayDetailActivity : BaseActivity(), IPlayDetailContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_detail)
        initData()
        initView()
    }

    private var playDetail: PlayDetail? = null
    private lateinit var presenter: PlayDetailPresenter
    private fun initData() {
        presenter = PlayDetailPresenter(this, getProvider())
        val id = intent.getIntExtra(Constant.INTENT_ID, 0)
        // 如果id等于0那么使用的是playDetail
        if (id == 0) {
            playDetail = ConstantCache.data as PlayDetail
            presenter.getContent(playDetail?.id ?: return)
        } else {
            presenter.getPlayDetailVideo(id)
        }
    }

    lateinit var orientationUtils: OrientationUtils
    private var isPlay = false
    private var isPause = false
    private fun initView() {
        initPlayer()
        initSrl()
        initRecycle()
        setListener()
    }


    private fun initPlayer() {
        orientationUtils = OrientationUtils(this, detail_player)
        //初始化不打开外部的旋转
        orientationUtils.isEnable = false
        if (playDetail != null) {
            setOption(playDetail?.coverUrl, playDetail?.playUrl).build(detail_player)
            detail_player.startPlayLogic()
        }
    }

    private fun initSrl() {
        //设置背景
        iv_bg.setImageURI(playDetail?.bgUrl)
    }


    val list = mutableListOf<BaseMuti>()
    val adapter = FindAdapter(null, list, true, this)
    private fun initRecycle() {
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        val slideInDownAnimator = SlideInDownAnimator(LinearInterpolator())
        slideInDownAnimator.addDuration = 500
        rv.itemAnimator = slideInDownAnimator
    }


    private fun setListener() {
        srl.setOnRefreshListener {
            finish()
        }

        srl.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean,
                                        percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                val string = getString(R.string.pull_to_close)
                var end = (string.length * percent).toInt()
                if (end > string.length) {
                    end = string.length
                }
                val substring = string.substring(0, end)
                tv_pull_header.text = substring
            }
        })

        adapter.setOnItemClickListener { adapter, view, position ->
            list.get(position)
        }


        detail_player.backButton.setOnClickListener {
            finish()
        }
        detail_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()
        }
    }


    private fun setOption(coverUrl: String?, playUrl: String?): GSYVideoOptionBuilder {
//        val videoOptionModel =  VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
//        val videoOptionModel1 =  VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp");
//        val list =  ArrayList<VideoOptionModel>();
//        list.add(videoOptionModel);
//        list.add(videoOptionModel1);
//        GSYVideoManager.instance().setOptionModelList(list);
        val gsyVideoOption = GSYVideoOptionBuilder()
        val imageView = SimpleDraweeView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageURI(coverUrl)
        gsyVideoOption
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
//                .setUrl("rtsp://admin:admin88888@172.20.0.235:554/h264/ch33/main/av_stream")
                .setUrl(playUrl)
                .setCacheWithPlay(true)
                .setIsTouchWiget(false)
                .setEnlargeImageRes(R.drawable.full_screen)
                .setShrinkImageRes(com.shuyu.gsyvideoplayer.R.drawable.video_shrink)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any) {
                        super.onPrepared(url, *objects)
                        //开始播放了才能旋转和全屏
                        orientationUtils.isEnable = true
                        isPlay = true
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        orientationUtils.backToProtVideo()
                    }
                })
                .setLockClickListener { view, lock ->
                    //配合下方的onConfigurationChanged
                    orientationUtils.isEnable = !lock
                }

        return gsyVideoOption
    }


    override fun onNext(t: List<BaseMuti>) {
        // 走这个回调，肯定不为null
        list.add(playDetail!!)
        list.addAll(t)
        list.add(FooterBean(R.color.white))
        // 动画不能调用notifyDataSetChanged
        adapter.notifyItemRangeInserted(0, list.size)

    }

    /**
     * 视频详情的回调
     */
    override fun onNextMore(t: List<BaseMuti>) {
        list.addAll(t)
        adapter.notifyItemRangeInserted(0, t.size)
        val videoBeanForClient = t.get(0) as VideoBeanForClient
        setOption(videoBeanForClient.cover.detail, videoBeanForClient.playUrl).build(detail_player)
        detail_player.startPlayLogic()
        iv_bg.setImageURI(videoBeanForClient.cover.blurred)
    }

    override fun onError() {
    }

    override fun onMoreEnd() {
    }

    override fun onBackPressed() {
        orientationUtils.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detail_player.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detail_player.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detail_player.currentPlayer.release()
        }
        orientationUtils.releaseListener()
        // 释放内存
        ConstantCache.data = null
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detail_player.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

}
