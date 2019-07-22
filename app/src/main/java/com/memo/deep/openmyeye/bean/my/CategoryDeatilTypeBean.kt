package com.memo.deep.openmyeye.bean.my

/**
 *     author : deep
 *     time   : 2019/07/18
 *     desc   :
 *     version: 1.0
 */

data class CategoryDeatilTypeBean(
    var categoryInfo: CategoryInfo = CategoryInfo(),
    var tabInfo: TabInfo = TabInfo(),
    var tagInfo: TagInfo = TagInfo()
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


    data class TagInfo(
        var dataType: String = "",
        var id: Int = 0,
        var name: String = "",
        var description: Any = Any(),
        var headerImage: Any = Any(),
        var bgPicture: Any = Any(),
        var actionUrl: Any = Any(),
        var recType: Int = 0,
        var follow: Any = Any(),
        var tagFollowCount: Int = 0,
        var tagVideoCount: Int = 0,
        var tagDynamicCount: Int = 0,
        var childTabList: Any = Any(),
        var lookCount: Int = 0,
        var shareLinkUrl: Any = Any()
    )


    data class CategoryInfo(
        var dataType: String = "",
        var id: Int = 0,
        var name: String = "",
        var description: String = "",
        var headerImage: String = "",
        var actionUrl: String = "",
        var follow: Follow = Follow()
    ) {

        data class Follow(
            var itemType: String = "",
            var itemId: Int = 0,
            var followed: Boolean = false
        )
    }
}