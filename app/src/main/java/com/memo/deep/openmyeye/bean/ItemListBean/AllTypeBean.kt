package com.memo.deep.openmyeye.bean.ItemListBean

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

/**
 *     author : deep
 *     time   : 2019/07/18
 *     desc   :
 *     version: 1.0
 */

data class AllTypeBean(
        var itemList: List<Item> = listOf(),
        var count: Int = 0,
        var total: Int = 0,
        var nextPageUrl: Any = Any(),
        var adExist: Boolean = false
) {

    companion object {
        val squareCard = 0
        val rectangleCard = 1
    }

    data class Item(
            var type: String = "",
            var data: Data = Data(),
            var tag: Any = Any(),
            var id: Int = 0,
            var adIndex: Int = 0
    ) : BaseMuti() {

        data class Data(
                var dataType: String = "",
                var id: Int = 0,
                var title: String = "",
                var image: String = "",
                var actionUrl: String = "",
                var shade: Boolean = false,
                var adTrack: Any? = Any(),
                var description: Any = Any()
        )

        override fun getItemType(): Int {
            if (type == "rectangleCard") {
                return rectangleCard
            }

            return squareCard
        }
    }
}