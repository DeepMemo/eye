package com.memo.deep.openmyeye.ui.fragment.second

import android.support.v7.util.DiffUtil
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import kotlinx.android.synthetic.main.fragment_second.view.*

class CreateFragment : FindFragment() {

    var start = 0
    val url = "http://baobab.kaiyanapp.com/api/v5/index/tab/category/2"
    override fun getContent() {
        start = 0
        presenter.getCommonContent(url, Constant.URL_MAP)
    }

    override fun getMoreContent() {
        start++
        val map = mutableMapOf("start" to start.toString(),
                "num" to "10")
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