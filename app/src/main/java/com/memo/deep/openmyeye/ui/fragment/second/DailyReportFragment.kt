package com.memo.deep.openmyeye.ui.fragment.second

import android.support.v7.util.DiffUtil
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import kotlinx.android.synthetic.main.fragment_second.view.*

/**
 * 日报
 */
class DailyReportFragment : FindFragment() {

    val url = "http://baobab.kaiyanapp.com/api/v5/index/tab/feed"
    override fun getContent() {
        index = 1
        presenter.getCommonContent(url, Constant.URL_MAP)
    }

    val oneDayMining = 24 * 60 * 60 * 1000
    var index = 1
    override fun getMoreContent() {
        val date = System.currentTimeMillis() - index++ * oneDayMining
        val map = mutableMapOf("date" to date.toString(),
                "num" to "2")
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
}