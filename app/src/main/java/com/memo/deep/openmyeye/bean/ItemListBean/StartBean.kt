package com.memo.deep.openmyeye.bean.ItemListBean

/**
 *     author : deep
 *     time   : 2019/07/08
 *     desc   :
 *     version: 1.0
 */

data class StartBean(
        var startPageAd: StartPageAd = StartPageAd(),
        var videoAdsConfig: VideoAdsConfig = VideoAdsConfig(),
        var startPage: StartPage = StartPage(),
        var log: Log = Log(),
        var privateMessageRefresh: PrivateMessageRefresh = PrivateMessageRefresh(),
        var preloadVideos: PreloadVideos = PreloadVideos(),
        var preload: Preload = Preload(),
        var profilePageAd: ProfilePageAd = ProfilePageAd(),
        var campaignInDetail: CampaignInDetail = CampaignInDetail(),
        var campaignInFeed: CampaignInFeed = CampaignInFeed(),
        var reply: Reply = Reply(),
        var startPageVideoConfig: StartPageVideoConfig = StartPageVideoConfig(),
        var autoCache: AutoCache = AutoCache(),
        var issueCover: IssueCover = IssueCover(),
        var startPageVideo: StartPageVideo = StartPageVideo(),
        var thirdPartyAd: ThirdPartyAd = ThirdPartyAd(),
        var consumption: Consumption = Consumption(),
        var launch: Launch = Launch(),
        var version: String = "",
        var push: Push = Push(),
        var androidPlayer: AndroidPlayer = AndroidPlayer(),
        var firstLaunch: FirstLaunch = FirstLaunch(),
        var shareTitle: ShareTitle = ShareTitle(),
        var pushInfo: PushInfo = PushInfo(),
        var homepage: Homepage = Homepage()
) {

    data class Log(
            var playLog: Boolean = false,
            var version: String = ""
    )


    data class Launch(
            var version: String = "",
            var adTrack: List<Any> = listOf()
    )


    data class FirstLaunch(
            var showFirstDetail: Boolean = false,
            var version: String = ""
    )


    data class CampaignInDetail(
            var imageUrl: String = "",
            var available: Boolean = false,
            var actionUrl: String = "",
            var showType: String = "",
            var version: String = ""
    )


    data class IssueCover(
            var issueLogo: IssueLogo = IssueLogo(),
            var version: String = ""
    ) {

        data class IssueLogo(
                var weekendExtra: String = "",
                var adapter: Boolean = false
        )
    }


    data class StartPageVideo(
            var displayTimeDuration: Int = 0,
            var showImageTime: Int = 0,
            var actionUrl: String = "",
            var countdown: Boolean = false,
            var showImage: Boolean = false,
            var canSkip: Boolean = false,
            var version: String = "",
            var url: String = "",
            var adaptiveUrls: String = "",
            var loadingMode: Int = 0,
            var displayCount: Int = 0,
            var startTime: Long = 0,
            var endTime: Long = 0,
            var adTrack: List<Any> = listOf(),
            var timeBeforeSkip: Int = 0
    )


    data class Consumption(
            var display: Boolean = false,
            var version: String = ""
    )


    data class StartPageAd(
            var displayTimeDuration: Int = 0,
            var showBottomBar: Boolean = false,
            var countdown: Boolean = false,
            var actionUrl: String = "",
            var blurredImageUrl: String = "",
            var canSkip: Boolean = false,
            var version: String = "",
            var adaptiveUrls: String = "",
            var buttonPosition: Int = 0,
            var imageUrl: String = "",
            var displayCount: Int = 0,
            var startTime: Long = 0,
            var endTime: Long = 0,
            var adTrack: List<Any> = listOf(),
            var newImageUrl: String = ""
    )


    data class PushInfo(
            var normal: Any = Any(),
            var version: String = ""
    )


    data class PreloadVideos(
            var list: List<Any> = listOf(),
            var version: String = ""
    )


    data class VideoAdsConfig(
            var list: List<Any> = listOf(),
            var version: String = ""
    )


    data class Reply(
            var version: String = "",
            var on: Boolean = false
    )


    data class AndroidPlayer(
            var playerList: List<String> = listOf(),
            var version: String = ""
    )


    data class ShareTitle(
            var weibo: String = "",
            var wechatMoments: String = "",
            var qzone: String = "",
            var version: String = ""
    )


    data class StartPageVideoConfig(
            var list: List<Any> = listOf(),
            var version: String = ""
    )


    data class AutoCache(
            var forceOff: Boolean = false,
            var videoNum: Int = 0,
            var version: String = "",
            var offset: Int = 0
    )


    data class Homepage(
            var cover: Any = Any(),
            var version: String = ""
    )


    data class Push(
            var startTime: Int = 0,
            var endTime: Int = 0,
            var message: String = "",
            var version: String = ""
    )


    data class Preload(
            var version: String = "",
            var on: Boolean = false
    )


    data class CampaignInFeed(
            var imageUrl: String = "",
            var available: Boolean = false,
            var actionUrl: String = "",
            var version: String = ""
    )


    data class StartPage(
            var imageUrl: String = "",
            var actionUrl: String = "",
            var version: String = ""
    )


    data class PrivateMessageRefresh(
            var controlMsgDetail: Int = 0,
            var controlList: Int = 0,
            var version: String = ""
    )


    data class ProfilePageAd(
            var imageUrl: String = "",
            var actionUrl: String = "",
            var startTime: Long = 0,
            var endTime: Long = 0,
            var version: String = "",
            var adTrack: List<Any> = listOf()
    )


    data class ThirdPartyAd(
            var controlSwitch: Boolean = false,
            var version: String = ""
    )
}