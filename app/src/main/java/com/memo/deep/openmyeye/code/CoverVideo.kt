package com.memo.deep.openmyeye.code

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
import moe.codeest.enviews.ENDownloadView


open class CoverVideo : StandardGSYVideoPlayer {

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
     * 控制底部白球的显示,以及margin的控制
     */
    open fun setProgressBar(show: Boolean) {
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

    // 以下为测试

    override fun changeUiToNormal() {
        Log.i("=-=video", "changeUiToNormal")
        changeUiToClear()
        setViewShowState(mThumbImageViewLayout, View.VISIBLE)
    }

    /**
     * 修改准备播放时，UI显示
     */
    override fun changeUiToPreparingShow() {
        Log.i("=-=video", "changeUiToPreparingShow")
//        setViewShowState(mTopContainer, View.INVISIBLE)
//        setViewShowState(mBottomContainer, View.INVISIBLE)
//        setViewShowState(mStartButton, View.INVISIBLE)
//        setViewShowState(mThumbImageViewLayout, View.INVISIBLE)
//        setViewShowState(mBottomProgressBar, View.INVISIBLE)
//        setViewShowState(mLockScreen, View.GONE)
//
//        // 自己添加,加载loading动画
        setViewShowState(mLoadingProgressBar, View.VISIBLE)
        startRotation(mLoadingProgressBar)
    }

    /**
     * 准备播放以后就进入播放周期，第一次不显示UI
     */
    // 第一次进入,自动播放，不显示其他UI界面

    /**
     *  全屏又要调用
     */
    override fun changeUiToPlayingShow() {
        //  全屏的UI控制
        if (mIfCurrentIsFullscreen) {
            // 不能调用super方法，这三项为不显示
            setViewShowState(mTopContainer, View.INVISIBLE)
            setViewShowState(mBottomContainer, View.INVISIBLE)
            setViewShowState(mStartButton, View.INVISIBLE)

            setViewShowState(mLoadingProgressBar, View.INVISIBLE)
            setViewShowState(mThumbImageViewLayout, View.INVISIBLE)
            setViewShowState(mBottomProgressBar, View.INVISIBLE)
            setViewShowState(mLockScreen, if (mIfCurrentIsFullscreen && mNeedLockFull) View.VISIBLE else View.GONE)

            if (mLoadingProgressBar is ENDownloadView) {
                (mLoadingProgressBar as ENDownloadView).reset()
            }
            updateStartImage()
        } else {
            setViewShowState(mThumbImageViewLayout, View.INVISIBLE)
            setViewShowState(mLoadingProgressBar, View.INVISIBLE)
        }
//        setViewShowState(mFullscreenButton, View.VISIBLE)
    }

    override fun onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, View.VISIBLE)
            return
        }
        if (mCurrentState == GSYVideoView.CURRENT_STATE_PREPAREING) {
            if (mBottomContainer != null) {
                if (mBottomContainer.visibility == View.VISIBLE) {
                    changeUiToPrepareingClear()
                } else {
                    changeUiToPreparingShow()
                }
            }
        } else if (mCurrentState == GSYVideoView.CURRENT_STATE_PLAYING) {
            if (mBottomContainer != null) {
                if (mBottomContainer.visibility == View.VISIBLE) {
                    changeUiToPlayingClear()
                } else {
                    // 覆写方法只为了修改这onClickUiToggle
                    showOnClickUI()
                }
            }
        } else if (mCurrentState == GSYVideoView.CURRENT_STATE_PAUSE) {
            if (mBottomContainer != null) {
                if (mBottomContainer.visibility == View.VISIBLE) {
                    changeUiToPauseClear()
                } else {
                    changeUiToPauseShow()
                }
            }
        } else if (mCurrentState == GSYVideoView.CURRENT_STATE_AUTO_COMPLETE) {
            if (mBottomContainer != null) {
                if (mBottomContainer.visibility == View.VISIBLE) {
                    changeUiToCompleteClear()
                } else {
                    changeUiToCompleteShow()
                }
            }
        } else if (mCurrentState == GSYVideoView.CURRENT_STATE_PLAYING_BUFFERING_START) {
            if (mBottomContainer != null) {
                if (mBottomContainer.visibility == View.VISIBLE) {
                    changeUiToPlayingBufferingClear()
                } else {
                    changeUiToPlayingBufferingShow()
                }
            }
        }
    }

    private fun showOnClickUI() {
        setViewShowState(mTopContainer, View.VISIBLE)
        setViewShowState(mBottomContainer, View.VISIBLE)
        setViewShowState(mStartButton, View.VISIBLE)
        setViewShowState(mLoadingProgressBar, View.INVISIBLE)
        setViewShowState(mThumbImageViewLayout, View.INVISIBLE)
        setViewShowState(mBottomProgressBar, View.INVISIBLE)
        setViewShowState(mLockScreen, if (mIfCurrentIsFullscreen && mNeedLockFull) View.VISIBLE else View.GONE)
        updateStartImage()

        setViewShowState(mFullscreenButton, View.VISIBLE)
        setProgressBar(true)

    }


//    override fun changeUiToPreparingShow() {
//        super.changeUiToPreparingShow()
//    }

//    override fun changeUiToPlayingShow() {
//        super.changeUiToPlayingShow()
//    }

    override fun changeUiToPauseShow() {
        Log.i("=-=video", "changeUiToPauseShow")
        super.changeUiToPauseShow()
    }

    override fun changeUiToError() {
        Log.i("=-=video", "changeUiToError")
        super.changeUiToError()
    }

    override fun changeUiToCompleteShow() {
        Log.i("=-=video", "changeUiToCompleteShow")
        super.changeUiToCompleteShow()
    }

    override fun changeUiToPlayingBufferingShow() {
        Log.i("=-=video", "changeUiToPlayingBufferingShow")
//        super.changeUiToPlayingBufferingShow()
    }
}
