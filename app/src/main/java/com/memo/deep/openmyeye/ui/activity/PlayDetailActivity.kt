package com.memo.deep.openmyeye.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.code.SampleCoverVideo
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_play_detail.*


/**
 * 视频播放页面
 */
class PlayDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_detail)
        initView()
    }

    //    lateinit var detailPlayer: StandardGSYVideoPlayer
    lateinit var detailPlayer: SampleCoverVideo
    lateinit var orientationUtils: OrientationUtils
    var isPlay = false
    var isPause = false
    private fun initView() {
//        PlayerFactory.setPlayManager(IjkPlayerManager::class.java)
        detailPlayer = detail_player
        // 设置新的seekbar
//        detailPlayer.setBottomProgressBar(sb_progress)
        orientationUtils = OrientationUtils(this, detailPlayer)
        //外部辅助的旋转，帮助全屏
        val orientationUtils = OrientationUtils(this, detail_player)
//初始化不打开外部的旋转
        orientationUtils.setEnable(false)
        val gsyVideoOption = GSYVideoOptionBuilder()
        val imageView = SimpleDraweeView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setImageURI("http://img.kaiyanapp.com/4015b66f385160e59d8e95051b574bdc.png?imageMogr2/quality/60/format/jpg")
        gsyVideoOption
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=132919&resourceType=video&editionType=high&source=aliyun")
                .setCacheWithPlay(true)
                .setIsTouchWiget(false)
                .setEnlargeImageRes(R.drawable.full_screen)
                .setShrinkImageRes(com.shuyu.gsyvideoplayer.R.drawable.video_shrink)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any) {
                        super.onPrepared(url, *objects)
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true)
                        isPlay = true
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0])//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1])//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo()
                        }
                    }
                }).setLockClickListener(object : LockClickListener {
                    override fun onClick(view: View, lock: Boolean) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock)
                        }
                    }
                }).build(detailPlayer)

        detailPlayer.backButton.setOnClickListener {
            finish()
        }
        detailPlayer.getFullscreenButton().setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                //直接横屏
                orientationUtils.resolveByClick()

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
//                detailPlayer.startWindowFullscreen(this@PlayDetailActivity, true, true)
            }
        })
    }


    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer.currentPlayer.release()
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

}
