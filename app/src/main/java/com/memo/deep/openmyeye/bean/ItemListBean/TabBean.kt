package com.memo.deep.openmyeye.bean.ItemListBean

/**
 *     author : deep
 *     time   : 2019/07/08
 *     desc   :
 *     version: 1.0
 */

data class TabBean(
    var tabInfo: TabInfo = TabInfo()
) {

    data class TabInfo(
        var tabList: List<Tab> = listOf(),
        var defaultIdx: Int = 0
    ) {

        data class Tab(
            var id: Int = 0,
            var name: String = "",
            var apiUrl: String = "",
            var tabType: Int = 0,
            var nameType: Int = 0,
            var adTrack: Any = Any()
        )
    }
}