package com.memo.deep.openmyeye.bean.beanItem

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti

/**
 * 评论的item
 */
data class DynamicInfoCard(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti(), MultiItemEntity {
    override fun getItemType(): Int {
        return BaseMuti.dynamicInfoCard
    }

    data class Data(
            var dataType: String = "",
            var dynamicType: String = "",
            var text: String = "",
            var actionUrl: String = "",
            var user: User = User(),
            var mergeNickName: Any = Any(),
            var mergeSubTitle: Any = Any(),
            var merge: Boolean = false,
            var createDate: Long = 0,
            var simpleVideo: SimpleVideo = SimpleVideo(),
            var reply: Reply = Reply()
    ) {

        data class User(
                var uid: Int = 0,
                var nickname: String = "",
                var avatar: String = "",
                var userType: String = "",
                var ifPgc: Boolean = false,
                var description: Any = Any(),
                var area: Any = Any(),
                var gender: Any = Any(),
                var registDate: Long = 0,
                var releaseDate: Any = Any(),
                var cover: Any = Any(),
                var actionUrl: String = "",
                var followed: Boolean = false,
                var limitVideoOpen: Boolean = false,
                var library: String = "",
                var uploadStatus: String = "",
                var bannedDate: Any = Any(),
                var bannedDays: Any = Any()
        )


        data class Reply(
                var id: Long = 0,
                var videoId: Int = 0,
                var videoTitle: String = "",
                var message: String = "",
                var likeCount: Int = 0,
                var showConversationButton: Boolean = false,
                var parentReplyId: Int = 0,
                var rootReplyId: Long = 0,
                var ifHotReply: Boolean = false,
                var liked: Boolean = false,
                var parentReply: Any = Any(),
                var user: User = User()
        ) {

            data class User(
                    var uid: Int = 0,
                    var nickname: String = "",
                    var avatar: String = "",
                    var userType: String = "",
                    var ifPgc: Boolean = false,
                    var description: Any = Any(),
                    var area: Any = Any(),
                    var gender: Any = Any(),
                    var registDate: Long = 0,
                    var releaseDate: Any = Any(),
                    var cover: Any = Any(),
                    var actionUrl: String = "",
                    var followed: Boolean = false,
                    var limitVideoOpen: Boolean = false,
                    var library: String = "",
                    var uploadStatus: String = "",
                    var bannedDate: Any = Any(),
                    var bannedDays: Any = Any()
            )
        }


        data class SimpleVideo(
                var id: Int = 0,
                var resourceType: String = "",
                var uid: Int = 0,
                var title: String = "",
                var description: String = "",
                var cover: Cover = Cover(),
                var category: String = "",
                var playUrl: String = "",
                var duration: Int = 0,
                var releaseTime: Long = 0,
                var consumption: Any = Any(),
                var collected: Boolean = false,
                var actionUrl: String = "",
                var onlineStatus: String = "",
                var count: Int = 0
        ) {

            data class Cover(
                    var feed: String = "",
                    var detail: String = "",
                    var blurred: String = "",
                    var sharing: Any = Any(),
                    var homepage: String = ""
            )
        }
    }
}