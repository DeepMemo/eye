package com.memo.deep.openmyeye.bean.my

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

/**
 * 搜索页面的title数据显示
 */
class SearchTitleMuti(var name: String = "") : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.searchTitle
    }
}