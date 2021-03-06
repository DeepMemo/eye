package com.memo.deep.openmyeye.ui.fragment.second

import android.support.v7.util.DiffUtil
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import kotlinx.android.synthetic.main.fragment_second.view.*

class RecommendFragment : FindFragment() {

    var page = 0
    override fun getContent() {
        // 刷新重置
        page = 0
        val map = mutableMapOf("page" to page.toString(),
                "isOldUser" to "true")
        map.putAll(Constant.URL_MAP)
        presenter.getCommonContent("http://baobab.kaiyanapp.com/api/v5/index/tab/allRec", map)
    }

    override fun getMoreContent() {
        page++
        val map = mutableMapOf("page" to page.toString(),
                "isOldUser" to "true")
        map.putAll(Constant.URL_MAP)
        presenter.getCommonMoreContent("http://baobab.kaiyanapp.com/api/v5/index/tab/allRec", map)
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