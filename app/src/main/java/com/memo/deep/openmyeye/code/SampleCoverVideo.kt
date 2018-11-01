package com.memo.deep.openmyeye.code

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.SeekBar
import com.blankj.utilcode.util.ConvertUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.util.MyUtils
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView


class SampleCoverVideo : StandardGSYVideoPlayer {

    constructor(context: Context) : super(context)
    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    fun setBottomProgressBar(progressBar: SeekBar) {
        mProgressBar = progressBar
        mProgressBar.setOnSeekBarChangeListener(this)
    }

    override fun getLayoutId(): Int {
        if (mIfCurrentIsFullscreen) {
            return R.layout.video_layout_standard
        }
        return R.layout.video_layout_standard_look
    }

    /**
     * 定义开始按键显示
     */
    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            if (mCurrentState == GSYVideoView.CURRENT_STATE_PLAYING) {
                imageView.setImageResource(R.drawable.ic_player_pause)
            } else if (mCurrentState == GSYVideoView.CURRENT_STATE_ERROR) {
                imageView.setImageResource(com.shuyu.gsyvideoplayer.R.drawable.video_click_error_selector)
            } else {
                imageView.setImageResource(R.drawable.ic_player_play)
            }
        }
    }

    /**
     * 修改时间
     */
    override fun setProgressAndTime(progress: Int, secProgress: Int, currentTime: Int, totalTime: Int) {
        var secProgress = secProgress

        if (mGSYVideoProgressListener != null && mCurrentState == GSYVideoView.CURRENT_STATE_PLAYING) {
            mGSYVideoProgressListener.onProgress(progress, secProgress, currentTime, totalTime)
        }

        if (mProgressBar == null || mTotalTimeTextView == null || mCurrentTimeTextView == null) {
            return
        }

        if (!mTouchingProgressBar) {
            if (progress != 0) mProgressBar.progress = progress
        }
        if (gsyVideoManager.bufferedPercentage > 0) {
            secProgress = gsyVideoManager.bufferedPercentage
        }
        if (secProgress > 94) secProgress = 100
        setSecondaryProgress(secProgress)
        // 只修改了这里
        mTotalTimeTextView.text = "/" + CommonUtil.stringForTime(totalTime)
        if (currentTime > 0)
            mCurrentTimeTextView.text = CommonUtil.stringForTime(currentTime)

        if (mBottomProgressBar != null) {
            if (progress != 0) mBottomProgressBar.progress = progress
            setSecondaryProgress(secProgress)
        }
    }

    /**
     * 隐藏所有的UI界面，timerTask所用，其实可以用changeUiToClear
     */
    override fun hideAllWidget() {
        super.hideAllWidget()
        //添加
        setViewShowState(mFullscreenButton, View.INVISIBLE)
        setProgressBar(false)
    }

    /**
     * 控制底部白球的显示,已经margin的控制
     */
    private fun setProgressBar(show: Boolean) {
        if (mProgressBar != null) {
            val id: Int
            val marginBottom: Int
            if (show) {
                id = R.drawable.video_seek_thumb
                marginBottom = 0
            } else {
                id = R.drawable.empty
                marginBottom = ConvertUtils.dp2px(6f)
            }
            // 动态控制底部白球的显示
            val drawable = mProgressBar.context.getDrawable(id)
            mProgressBar.thumb = drawable
            mProgressBar.thumbOffset = 0
            MyUtils.setMargins(mProgressBar, 0, 0, 0, marginBottom)
        }
    }

    /**
     * 传入外部的seekbar，暂时不用
     */
    fun setProgressbar(seekBar: SeekBar) {
        mProgressBar = seekBar
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        setViewShowState(mLoadingProgressBar, View.VISIBLE)
        startRotation(mLoadingProgressBar)
    }

    /**
     * 控制点击的时候，UI的显示
     */
    // 第一次进入,自动播放，不显示其他UI界面
    var isFirst = true

    override fun changeUiToPlayingShow() {
        if (isFirst) {
            changeUiToClear()
            isFirst = false
            return
        }
        super.changeUiToPlayingShow()
        // 添加
        setProgressBar(true)
        setViewShowState(mFullscreenButton, View.VISIBLE)
    }

    /**
     * 控制点击的时候，UI的显示
     */
    override fun changeUiToClear() {
        super.changeUiToClear()
        // 添加
        setViewShowState(mFullscreenButton, View.INVISIBLE)
        setProgressBar(false)

    }

    /**
     * 加载动画
     */
    private fun startRotation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        animator.duration = 400
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.repeatCount = ObjectAnimator.RESTART
        animator.interpolator = LinearInterpolator()
        animator.start()
    }
}
