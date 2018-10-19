package com.memo.deep.openmyeye.bean.beanItem

import com.memo.deep.openmyeye.bean.beanBase.BaseMuti

/**
 * 顶部滚动页面
 */
data class HorizontalScrollCard(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.horizontalScrollCard
    }

    data class Data(
            var dataType: String = "",
            var itemList: List<Item> = listOf(),
            var count: Int = 0
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
                    var title: String = "",
                    var description: String = "",
                    var image: String = "",
                    var actionUrl: String = "",
                    var adTrack: Any = Any(),
                    var shade: Boolean = false,
                    var label: Label = Label(),
                    var labelList: List<Any> = listOf(),
                    var header: Header = Header()
            ) {

                data class Header(
                        var id: Int = 0,
                        var title: Any = Any(),
                        var font: Any = Any(),
                        var subTitle: Any = Any(),
                        var subTitleFont: Any = Any(),
                        var textAlign: String = "",
                        var cover: Any = Any(),
                        var label: Any = Any(),
                        var actionUrl: Any = Any(),
                        var labelList: Any = Any(),
                        var rightText: Any = Any(),
                        var icon: Any = Any(),
                        var description: Any = Any()
                )


                data class Label(
                        var text: String = "",
                        var card: String = "",
                        var detail: Any = Any()
                )
            }
        }
    }
}