package com.memo.deep.openmyeye.bean.itemBean

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti


data class AutoPlayFollowCard(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.autoPlayFollowCard
    }

    data class Data(
            var dataType: String = "",
            var header: Header = Header(),
            var content: Content = Content(),
            var adTrack: Any = Any()
    ) {

        data class Header(
                var id: Int = 0,
                var actionUrl: String = "",
                var labelList: Any = Any(),
                var icon: String = "",
                var iconType: String = "",
                var time: Long = 0,
                var showHateVideo: Boolean = false,
                var followType: String = "",
                var tagId: Int = 0,
                var tagName: Any = Any(),
                var issuerName: String = "",
                var topShow: Boolean = false
        )


        data class Content(
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
                    var library: String = "",
                    var tags: List<Tag> = listOf(),
                    var consumption: Consumption = Consumption(),
                    var resourceType: String = "",
                    var uid: Int = 0,
                    var createTime: Long = 0,
                    var updateTime: Long = 0,
                    var collected: Boolean = false,
                    var owner: Owner = Owner(),
                    var selectedTime: Any = Any(),
                    var checkStatus: String = "",
                    var area: String = "",
                    var city: String = "",
                    var longitude: Int = 0,
                    var latitude: Int = 0,
                    var ifMock: Boolean = false,
                    var validateStatus: String = "",
                    var validateResult: String = "",
                    var playUrl: String = "",
                    var cover: Cover = Cover(),
                    var status: String = "",
                    var releaseTime: Long = 0,
                    var duration: Int = 0,
                    var transId: Any = Any(),
                    var type: String = "",
                    var validateTaskId: String = ""
            ) {

                data class Cover(
                        var feed: String = "",
                        var detail: String = "",
                        var blurred: Any = Any(),
                        var sharing: Any = Any(),
                        var homepage: Any = Any()
                )


                data class Tag(
                        var id: Int = 0,
                        var name: String = "",
                        var actionUrl: String = "",
                        var adTrack: Any = Any(),
                        var desc: Any = Any(),
                        var bgPicture: String = "",
                        var headerImage: String = "",
                        var tagRecType: String = "",
                        var childTagList: Any = Any(),
                        var childTagIdList: Any = Any(),
                        var communityIndex: Int = 0
                )


                data class Consumption(
                        var collectionCount: Int = 0,
                        var shareCount: Int = 0,
                        var replyCount: Int = 0
                )


                data class Owner(
                        var uid: Int = 0,
                        var nickname: String = "",
                        var avatar: String = "",
                        var userType: String = "",
                        var ifPgc: Boolean = false,
                        var description: Any = Any(),
                        var area: Any = Any(),
                        var gender: Any = Any(),
                        var registDate: Long = 0,
                        var releaseDate: Long = 0,
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
        }
    }
}