package com.memo.deep.openmyeye.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

//http://baobab.kaiyanapp.com/api/v5/index/tab/discovery?udid=a754f397b32441feabdafd94c5833118cbd76441&vc=403&vn=4.5.1&deviceModel=LLD-AL00&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=26
/**
 * 首页发现fragment的数据
 *
 */

data class FindBean(
        var itemList: List<Item> = listOf(),
        var count: Int = 0,
        var total: Int = 0,
        var nextPageUrl: String = "",
        var adExist: Boolean = false
) {

    data class Item(
            var type: String = "",
            var data: Data = Data(),
            var tag: Any = Any(),
            var id: Int = 0,
            var adIndex: Int = 0
    ) : MultiItemEntity {
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
        }

        override fun getItemType(): Int {
            var result = 0
            when (type) {
                "horizontalScrollCard" -> result = horizontalScrollCard
                "textCard" -> {
                    if ("footer2" == data.type) {
                        result = textCardFooter
                    } else {
                        result = textCard
                    }
                }
                "briefCard" -> result = briefCard
                "followCard" -> result = followCard
                "videoSmallCard" -> result = videoSmallCard
                "squareCardCollection" -> result = squareCardCollection
                "videoCollectionWithBrief" -> result = videoCollectionWithBrief
            }

            return result
        }

        data class Data(
                var dataType: String = "",
                var header: Header = Header(),
                var content: Content = Content(),
                var adTrack: Any = Any(),
                var itemList: List<Data.Item> = listOf(),
                var count: Int = 0,
                var id: Int = 0,
                var type: String = "",
                var text: String = "",
                var title: String = "",
                // 为null，不知道是什么数据
                var subTitle: Any = Any(),
                var actionUrl: Any = Any(),
                var description: String = "",
                var icon: String = "",
                var follow: Follow = Follow(),
                var ifPgc: Boolean = false
        ) {

            data class Follow(
                    var itemType: String = "",
                    var itemId: Int = 0,
                    var followed: Boolean = false
            )

            data class Item(
                    var type: String = "",
                    var data: Data = Data(),
                    var tag: Any = Any(),
                    var id: Int = 0,
                    var adIndex: Int = 0
            ) : Serializable {

                data class Data(
                        var dataType: String = "",
                        var header: Header = Header(),
                        var itemList: List<Item> = listOf(),
                        var count: Int = 0,
                        var adTrack: Any = Any(),
                        var image: String = ""
                ) {

                    data class Header(
                            var id: Int = 0,
                            var icon: String = "",
                            var iconType: String = "",
                            var title: String = "",
                            var subTitle: Any = Any(),
                            var description: String = "",
                            var actionUrl: String = "",
                            var adTrack: Any = Any(),
                            var follow: Follow = Follow(),
                            var ifPgc: Boolean = false
                    ) {

                        data class Follow(
                                var itemType: String = "",
                                var itemId: Int = 0,
                                var followed: Boolean = false
                        )
                    }


                    data class Item(
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
                                var slogan: Any = Any(),
                                var provider: Provider = Provider(),
                                var category: String = "",
                                var author: Author = Author(),
                                var cover: Cover = Cover(),
                                var playUrl: String = "",
                                var thumbPlayUrl: Any = Any(),
                                var duration: Int = 0,
                                var webUrl: WebUrl = WebUrl(),
                                var releaseTime: Long = 0,
                                var playInfo: List<PlayInfo> = listOf(),
                                var campaign: Any = Any(),
                                var waterMarks: Any = Any(),
                                var adTrack: Any = Any(),
                                var type: String = "",
                                var titlePgc: String = "",
                                var descriptionPgc: String = "",
                                var remark: String = "",
                                var ifLimitVideo: Boolean = false,
                                var searchWeight: Int = 0,
                                var idx: Int = 0,
                                var shareAdTrack: Any = Any(),
                                var favoriteAdTrack: Any = Any(),
                                var webAdTrack: Any = Any(),
                                var date: Long = 0,
                                var promotion: Any = Any(),
                                var label: Any = Any(),
                                var labelList: List<Any> = listOf(),
                                var descriptionEditor: String = "",
                                var collected: Boolean = false,
                                var played: Boolean = false,
                                var subtitles: List<Any> = listOf(),
                                var lastViewTime: Any = Any(),
                                var playlists: Any = Any(),
                                var src: Any = Any()
                        ) {

                            data class Cover(
                                    var feed: String = "",
                                    var detail: String = "",
                                    var blurred: String = "",
                                    var sharing: Any = Any(),
                                    var homepage: Any = Any()
                            )


                            data class PlayInfo(
                                    var height: Int = 0,
                                    var width: Int = 0,
                                    var urlList: List<Url> = listOf(),
                                    var name: String = "",
                                    var type: String = "",
                                    var url: String = ""
                            ) {

                                data class Url(
                                        var name: String = "",
                                        var url: String = "",
                                        var size: Int = 0
                                )
                            }


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


                            data class Provider(
                                    var name: String = "",
                                    var alias: String = "",
                                    var icon: String = ""
                            )


                            data class Author(
                                    var id: Int = 0,
                                    var icon: String = "",
                                    var name: String = "",
                                    var description: String = "",
                                    var link: String = "",
                                    var latestReleaseTime: Long = 0,
                                    var videoNum: Int = 0,
                                    var adTrack: Any = Any(),
                                    var follow: Follow = Follow(),
                                    var shield: Shield = Shield(),
                                    var approvedNotReadyVideoCount: Int = 0,
                                    var ifPgc: Boolean = false
                            ) {

                                data class Follow(
                                        var itemType: String = "",
                                        var itemId: Int = 0,
                                        var followed: Boolean = false
                                )


                                data class Shield(
                                        var itemType: String = "",
                                        var itemId: Int = 0,
                                        var shielded: Boolean = false
                                )
                            }


                            data class WebUrl(
                                    var raw: String = "",
                                    var forWeibo: String = ""
                            )
                        }
                    }
                }
            }

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
                        var slogan: String = "",
                        var provider: Provider = Provider(),
                        var category: String = "",
                        var author: Author = Author(),
                        var cover: Cover = Cover(),
                        var playUrl: String = "",
                        var thumbPlayUrl: Any = Any(),
                        var duration: Int = 0,
                        var webUrl: WebUrl = WebUrl(),
                        var releaseTime: Long = 0,
                        var playInfo: List<PlayInfo> = listOf(),
                        var campaign: Any = Any(),
                        var waterMarks: Any = Any(),
                        var adTrack: Any = Any(),
                        var type: String = "",
                        var titlePgc: Any = Any(),
                        var descriptionPgc: Any = Any(),
                        var remark: Any = Any(),
                        var ifLimitVideo: Boolean = false,
                        var searchWeight: Int = 0,
                        var idx: Int = 0,
                        var shareAdTrack: Any = Any(),
                        var favoriteAdTrack: Any = Any(),
                        var webAdTrack: Any = Any(),
                        var date: Long = 0,
                        var promotion: Any = Any(),
                        var label: Any = Any(),
                        var labelList: List<Any> = listOf(),
                        var descriptionEditor: String = "",
                        var collected: Boolean = false,
                        var played: Boolean = false,
                        var subtitles: List<Any> = listOf(),
                        var lastViewTime: Any = Any(),
                        var playlists: Any = Any(),
                        var src: Any = Any()
                ) {

                    data class Cover(
                            var feed: String = "",
                            var detail: String = "",
                            var blurred: String = "",
                            var sharing: Any = Any(),
                            var homepage: String = ""
                    )


                    data class PlayInfo(
                            var height: Int = 0,
                            var width: Int = 0,
                            var urlList: List<Url> = listOf(),
                            var name: String = "",
                            var type: String = "",
                            var url: String = ""
                    ) {

                        data class Url(
                                var name: String = "",
                                var url: String = "",
                                var size: Int = 0
                        )
                    }


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


                    data class Provider(
                            var name: String = "",
                            var alias: String = "",
                            var icon: String = ""
                    )


                    data class Author(
                            var id: Int = 0,
                            var icon: String = "",
                            var name: String = "",
                            var description: String = "",
                            var link: String = "",
                            var latestReleaseTime: Long = 0,
                            var videoNum: Int = 0,
                            var adTrack: Any = Any(),
                            var follow: Follow = Follow(),
                            var shield: Shield = Shield(),
                            var approvedNotReadyVideoCount: Int = 0,
                            var ifPgc: Boolean = false
                    ) {

                        data class Follow(
                                var itemType: String = "",
                                var itemId: Int = 0,
                                var followed: Boolean = false
                        )


                        data class Shield(
                                var itemType: String = "",
                                var itemId: Int = 0,
                                var shielded: Boolean = false
                        )
                    }


                    data class WebUrl(
                            var raw: String = "",
                            var forWeibo: String = ""
                    )
                }
            }


            data class Header(
                    var id: Int = 0,
                    var title: String = "",
                    var font: Any = Any(),
                    var subTitle: Any = Any(),
                    var subTitleFont: Any = Any(),
                    var textAlign: String = "",
                    var cover: Any = Any(),
                    var label: Any = Any(),
                    var actionUrl: String = "",
                    var labelList: Any = Any(),
                    var rightText: Any = Any(),
                    var icon: String = "",
                    var iconType: String = "",
                    var description: String = "",
                    var time: Long = 0,
                    var showHateVideo: Boolean = false
            )
        }
    }
}