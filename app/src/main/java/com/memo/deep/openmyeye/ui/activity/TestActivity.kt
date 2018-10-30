package com.memo.deep.openmyeye.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.view.View
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.adapter.recycle.TestAdapter
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
        progress.thumb = getDrawable(R.drawable.empty)
        tv_typer.animateText(getString(R.string.pull_to_close))
    }

    private lateinit var testAdapter: TestAdapter
    //    private lateinit var testAdapter: OriginAdapter
    val list = arrayListOf("1", "2", "3")

    lateinit var detailPlayer: StandardGSYVideoPlayer
    lateinit var orientationUtils: OrientationUtils
    var isPlay = false
    var isPause = false
    private fun initView() {
//        PlayerFactory.setPlayManager(IjkPlayerManager::class.java)
        detailPlayer = detail_player
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
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
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

    override fun onRestart() {
        super.onRestart()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    fun onNext(oldList: List<String>, newList: ArrayList<String>) {
        val diffResult = DiffUtil.calculateDiff(NewDiffCallback(oldList, newList))
        testAdapter.list.clear()
        testAdapter.list.addAll(newList)
        diffResult.dispatchUpdatesTo(testAdapter)
    }

    /**
     * 数据差异比较
     */
    class NewDiffCallback(private val oldList: List<String>,
                          private val newList: List<String>) : DiffUtil.Callback() {
        override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
            return true
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
            return oldList.get(p0) == newList.get(p1)
        }

    }
}
