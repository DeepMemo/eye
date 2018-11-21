package com.memo.deep.openmyeye.bean.baseBean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * find数据bean的父类
 */
abstract class BaseMuti : MultiItemEntity {
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
        val dynamicInfoCard = 8
        val videoBeanForClient = 11
        val viewPagerFollowCard = 12
        val autoPlayFollowCard = 13
        val pictureFollowCard1 = 14
        val pictureFollowCard4 = 15

        // 自己加的
        val playDetail = 9
        val footerBean = 10
        val searchString = 16
        val searchTitle = 17
    }
}