package com.memo.deep.openmyeye.bean.beanItem

import com.memo.deep.openmyeye.bean.beanBase.BaseMuti

/**
 * 加在数据集合末尾的bean，以The end 展示
 */
class FooterBean(var color: Int) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.footerBean
    }
}