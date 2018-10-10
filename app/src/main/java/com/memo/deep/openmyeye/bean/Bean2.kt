package com.memo.deep.openmyeye.bean

//http://baobab.kaiyanapp.com/api/v5/index/tab/list?udid=8d64d38fe5b34d85932be0c9580294ec5e4696c9&vc=403&vn=4.5.1&deviceModel=LLD-AL00&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=26
/**
 * 滚动条
 */
data class Bean2(
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