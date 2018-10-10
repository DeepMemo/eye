package com.memo.deep.openmyeye.bean

//http://baobab.kaiyanapp.com/api/v5/index/tab/allRec?page=0&isOldUser=true&udid=8d64d38fe5b34d85932be0c9580294ec5e4696c9&vc=403&vn=4.5.1&deviceModel=LLD-AL00&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=26
/**
 * 大概是具体内容，结构有点复杂
 */
data class Bean3(
    var itemList: List<Item> = listOf(),
    var count: Int = 0,
    var total: Int = 0,
    var nextPageUrl: String = "",
    var adExist: Boolean = false
) {

    data class Item(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
    ) {

        data class Data(
            var dataType: String = "",
            var id: Int = 0,
            var type: String = "",
            var text: String = "",
            var subTitle: Any = Any(),
            var actionUrl: String = "",
            var adTrack: Any = Any(),
            var follow: Any = Any()
        )
    }
}