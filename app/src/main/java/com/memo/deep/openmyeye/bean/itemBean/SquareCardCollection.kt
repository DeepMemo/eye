package com.memo.deep.openmyeye.bean.itemBean

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

/**
 * 海报
 */
data class SquareCardCollection<T>(
        var type: String = "",
        var data: Data<T> = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        var type = -1
        val item = data.itemList.get(0)
        when (item) {
            is Banner -> type = BaseMuti.squareCardCollection
            is FollowCard -> type = BaseMuti.viewPagerFollowCard
        }
        return type
    }

    data class Data<T>(
            var dataType: String = "",
            var header: Header = Header(),
            var itemList: List<T> = listOf(),
            var count: Int = 0,
            var adTrack: Any = Any()
    ) {

        data class Header(
                var id: Int = 0,
                var title: String = "",
                var font: String = "",
                var subTitle: String = "",
                var subTitleFont: Any = Any(),
                var textAlign: String = "",
                var cover: Any = Any(),
                var label: Any = Any(),
                var actionUrl: String = "",
                var labelList: Any = Any(),
                var rightText: Any = Any()
        )
    }
}