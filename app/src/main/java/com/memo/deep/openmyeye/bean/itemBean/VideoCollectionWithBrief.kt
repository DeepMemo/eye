package com.memo.deep.openmyeye.bean.itemBean

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import java.io.Serializable

/**
 * 点击关注
 */
data class VideoCollectionWithBrief(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.videoCollectionWithBrief
    }

    data class Data(
            var dataType: String = "",
            var header: Header = Header(),
            var itemList: List<Item> = listOf(),
            var count: Int = 0,
            var adTrack: Any = Any()
    ) {

        data class Item(
                var type: String = "",
                var data: Data = Data(),
                var tag: Any = Any(),
                var id: Int = 0,
                var adIndex: Int = 0
        ) : Serializable {

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
            ) : Serializable{

                data class Cover(
                        var feed: String = "",
                        var detail: String = "",
                        var blurred: String = "",
                        var sharing: Any = Any(),
                        var homepage: Any = Any()
                ): Serializable


                data class PlayInfo(
                        var height: Int = 0,
                        var width: Int = 0,
                        var urlList: List<Url> = listOf(),
                        var name: String = "",
                        var type: String = "",
                        var url: String = ""
                ) : Serializable{

                    data class Url(
                            var name: String = "",
                            var url: String = "",
                            var size: Int = 0
                    ): Serializable
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
                ): Serializable


                data class Consumption(
                        var collectionCount: Int = 0,
                        var shareCount: Int = 0,
                        var replyCount: Int = 0
                ): Serializable


                data class Provider(
                        var name: String = "",
                        var alias: String = "",
                        var icon: String = ""
                ): Serializable


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
                ) : Serializable{

                    data class Follow(
                            var itemType: String = "",
                            var itemId: Int = 0,
                            var followed: Boolean = false
                    ): Serializable


                    data class Shield(
                            var itemType: String = "",
                            var itemId: Int = 0,
                            var shielded: Boolean = false
                    ): Serializable
                }


                data class WebUrl(
                        var raw: String = "",
                        var forWeibo: String = ""
                ): Serializable
            }
        }


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
    }
}