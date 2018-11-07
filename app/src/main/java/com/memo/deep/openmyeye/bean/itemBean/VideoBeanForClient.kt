package com.memo.deep.openmyeye.bean.itemBean

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

/**
 *根据ID查询视频的详情
 */
data class VideoBeanForClient(
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
        var ad: Boolean = false,
        var adTrack: Any = Any(),
        var type: String = "",
        var titlePgc: Any = Any(),
        var descriptionPgc: Any = Any(),
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
) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.videoBeanForClient
    }

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