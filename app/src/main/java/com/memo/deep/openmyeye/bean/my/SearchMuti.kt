package com.memo.deep.openmyeye.bean.my

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

/**
 * 搜索页面的数据显示
 */
class SearchMuti(var name: String = "", var isColorMore: Boolean = false) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.searchString
    }
}