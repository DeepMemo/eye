package com.memo.deep.openmyeye.bean.beanItem

import com.memo.deep.openmyeye.bean.beanBase.BaseMuti

/**
 * 文字
 */
data class TextCard(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        if (data.type == "footer2") {
            return BaseMuti.textCardFooter
        }
        return BaseMuti.textCard
    }

    data class Data(
            var dataType: String = "",
            var id: Int = 0,
            var type: String = "",
            var text: String = "",
            var subTitle: Any = Any(),
            var actionUrl: Any = Any(),
            var adTrack: Any = Any(),
            var follow: Any = Any()
    )
}