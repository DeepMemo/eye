package com.memo.deep.openmyeye.ui.fragment.second

import com.memo.deep.openmyeye.`interface`.Constant

class RecommendFragment : FindFragment() {

    var page = 0
    override fun getContent() {
        val map = mutableMapOf("page" to page.toString(),
                "isOldUser" to "true")
        map.putAll(Constant.URL_MAP)
        presenter.getCommonContent("http://baobab.kaiyanapp.com/api/v5/index/tab/allRec", map)
    }

    override fun getMoreContent() {
        val map = mutableMapOf("page" to page++.toString(),
                "isOldUser" to "true")
        map.putAll(Constant.URL_MAP)
        presenter.getCommonContent("api\\/v5\\/index\\/tab\\/allRec", map)
    }

}