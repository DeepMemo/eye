package com.memo.deep.openmyeye.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.bean.beanItem.FooterBean
import com.memo.deep.openmyeye.bean.my.PlayDetail
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

    private lateinit var playDetail: PlayDetail
    private lateinit var presenter: PlayDetailPresenter
    private fun initData() {
        playDetail = intent.getSerializableExtra(Constant.INTENT_DATA) as PlayDetail
        presenter = PlayDetailPresenter(this, getProvider())
        presenter.getContent(playDetail.id)
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
        setOption().build(detail_player)
    }

    private fun initSrl() {
        //设置背景
        iv_bg.setImageURI(playDetail.bgUrl)
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
//            srl.finishRefresh()
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


    private fun setOption(): GSYVideoOptionBuilder {
        val gsyVideoOption = GSYVideoOptionBuilder()
        val imageView = SimpleDraweeView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageURI(playDetail.coverUrl)
        gsyVideoOption
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(playDetail.playUrl)
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
        list.add(playDetail)
        list.addAll(t)
        list.add(FooterBean(R.color.white))
        adapter.notifyItemInserted(0)
        detail_player.startPlayLogic()

    }

    override fun onNextMore(t: List<BaseMuti>) {
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
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detail_player.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

}
