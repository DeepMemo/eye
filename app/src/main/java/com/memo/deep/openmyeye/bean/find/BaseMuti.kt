package com.memo.deep.openmyeye.bean.find

import com.chad.library.adapter.base.entity.MultiItemEntity

abstract class BaseMuti: MultiItemEntity {
    companion object {
        val horizontalScrollCard = 0
        val textCard = 1
        // textCard有两种格式
        val textCardFooter = -1
        val briefCard = 2
        val followCard = 3
        val videoSmallCard = 4
        val squareCardCollection = 5
        val videoCollectionWithBrief = 6
        val banner = 7
    }
}