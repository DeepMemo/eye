package com.memo.deep.openmyeye.bean.ItemListBean

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

/**
 *     author : deep
 *     time   : 2019/07/11
 *     desc   : 通知-推送
 *     version: 1.0
 */

data class NoticePushBean(
        var messageList: List<Message> = listOf(),
        var updateTime: Long = 0,
        var nextPageUrl: String = ""
) {

    data class Message(
            var id: Int = 0,
            var title: String = "",
            var content: String = "",
            var date: Long = 0,
            var actionUrl: String = "",
            var icon: String = "",
            var viewed: Boolean = false,
            var ifPush: Boolean = false,
            var pushStatus: Int = 0,
            var uid: Any = Any()
    ) : BaseMuti()
}