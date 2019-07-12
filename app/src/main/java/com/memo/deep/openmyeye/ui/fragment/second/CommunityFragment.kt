package com.memo.deep.openmyeye.ui.fragment.second

import android.graphics.Rect
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.code.ListVideo
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment_second.view.*

/**
 * 社区有新的选项，是不同的item
 */
class CommunityFragment : FindFragment() {

    val url = "http://baobab.kaiyanapp.com/api/v5/index/tab/ugcSelected"
    var start = 0
    override fun getContent() {
        presenter.getCommonContent(url, Constant.URL_MAP)
        start = 0
    }

    override fun getMoreContent() {
        start += 10
        val map = mutableMapOf("start" to start.toString())
        map.putAll(Constant.URL_MAP)
        presenter.getCommonMoreContent(url, map)
    }

    override fun onNext(t: List<BaseMuti>) {
        val diffResult = DiffUtil.calculateDiff(NewDiffCallback(list, t), true)
        list.clear()
        list.addAll(t)
        diffResult.dispatchUpdatesTo(adapter)
        inflate.srl.finishRefresh()
        objectAnimator.cancel()
        // 数据居然动态变化，可能内容获取就添加了评论
        initLoadMore()
    }


    var firstVisibleItem = 0
    var lastVisibleItem = 0
    var visibleCount = 0
    override fun initView() {
        super.initView()
        addListener()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser){
            GSYVideoManager.releaseAllVideos()
        }
    }

    private fun addListener() {
        inflate.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> autoPlayVideo()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = inflate.rv.layoutManager as LinearLayoutManager
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                visibleCount = lastVisibleItem - firstVisibleItem
                //大于0说明有播放
                if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                    //当前播放的位置
                    val position = GSYVideoManager.instance().getPlayPosition()
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().getPlayTag().equals(FindAdapter.TAG)
                            && (position < firstVisibleItem || position > lastVisibleItem)) {
                        GSYVideoManager.releaseAllVideos()
                    }
                }
            }
        })
    }

    private fun autoPlayVideo() {
        val linearLayoutManager = inflate.rv.layoutManager as LinearLayoutManager
        for (i in 0..visibleCount) {
            val listVideo = linearLayoutManager.getChildAt(i).findViewById<ListVideo>(R.id.detail_player)
            if (linearLayoutManager.getChildAt(i) != null && listVideo != null) {
                val rect = Rect()
                listVideo.getLocalVisibleRect(rect)
                val videoHeight = listVideo.getHeight()
                if (rect.top == 0 && rect.bottom == videoHeight) {
                    listVideo.getStartButton().performClick()
                    return
                }
            }
        }
        GSYVideoManager.releaseAllVideos()
    }

}