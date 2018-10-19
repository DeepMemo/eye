package com.memo.deep.openmyeye.bean.beanItem

import com.memo.deep.openmyeye.bean.beanBase.BaseMuti

/**
 * 类别#音乐
 */
data class BriefCard(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.briefCard
    }

    data class Data(
            var dataType: String = "",
            var id: Int = 0,
            var icon: String = "",
            var iconType: String = "",
            var title: String = "",
            var subTitle: Any = Any(),
            var description: String = "",
            var actionUrl: String = "",
            var adTrack: Any = Any(),
            var follow: Follow = Follow(),
            var ifPgc: Boolean = false
    ) {

        data class Follow(
            var itemType: String = "",
            var itemId: Int = 0,
            var followed: Boolean = false
        )
    }
}