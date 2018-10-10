package com.memo.deep.openmyeye.bean

//http://baobab.kaiyanapp.com/api/v2/configs?model=Android&udid=8d64d38fe5b34d85932be0c9580294ec5e4696c9&vc=403&vn=4.5.1&deviceModel=LLD-AL00&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=26

data class Bean4(
    var autoCache: AutoCache = AutoCache(),
    var startPageAd: StartPageAd = StartPageAd(),
    var videoAdsConfig: VideoAdsConfig = VideoAdsConfig(),
    var startPage: StartPage = StartPage(),
    var log: Log = Log(),
    var issueCover: IssueCover = IssueCover(),
    var startPageVideo: StartPageVideo = StartPageVideo(),
    var consumption: Consumption = Consumption(),
    var launch: Launch = Launch(),
    var preload: Preload = Preload(),
    var version: String = "",
    var push: Push = Push(),
    var androidPlayer: AndroidPlayer = AndroidPlayer(),
    var profilePageAd: ProfilePageAd = ProfilePageAd(),
    var firstLaunch: FirstLaunch = FirstLaunch(),
    var shareTitle: ShareTitle = ShareTitle(),
    var campaignInDetail: CampaignInDetail = CampaignInDetail(),
    var campaignInFeed: CampaignInFeed = CampaignInFeed(),
    var reply: Reply = Reply(),
    var startPageVideoConfig: StartPageVideoConfig = StartPageVideoConfig(),
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


    data class StartPageVideo(
        var displayTimeDuration: Int = 0,
        var showImageTime: Int = 0,
        var actionUrl: String = "",
        var countdown: Boolean = false,
        var showImage: Boolean = false,
        var canSkip: Boolean = false,
        var version: String = "",
        var url: String = "",
        var loadingMode: Int = 0,
        var displayCount: Int = 0,
        var startTime: Long = 0,
        var endTime: Long = 0,
        var adTrack: List<Any> = listOf(),
        var timeBeforeSkip: Int = 0
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


    data class Consumption(
        var display: Boolean = false,
        var version: String = ""
    )


    data class PushInfo(
        var normal: Any = Any(),
        var version: String = ""
    )


    data class VideoAdsConfig(
        var list: List<X> = listOf(),
        var version: String = ""
    ) {

        data class X(
            var id: Int = 0,
            var icon: String = "",
            var title: String = "",
            var description: String = "",
            var url: String = "",
            var actionUrl: String = "",
            var imageUrl: String = "",
            var displayTimeDuration: Int = 0,
            var displayCount: Int = 0,
            var showImage: Boolean = false,
            var showImageTime: Int = 0,
            var loadingMode: Int = 0,
            var countdown: Boolean = false,
            var canSkip: Boolean = false,
            var timeBeforeSkip: Int = 0,
            var showActionButton: Boolean = false,
            var videoType: String = "",
            var videoAdType: String = "",
            var categoryId: Int = 0,
            var position: Int = 0,
            var adTrack: List<Any> = listOf(),
            var startTime: Long = 0,
            var endTime: Long = 0,
            var status: String = ""
        )
    }


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


    data class Reply(
        var version: String = "",
        var on: Boolean = false
    )


    data class StartPageAd(
        var displayTimeDuration: Int = 0,
        var showBottomBar: Boolean = false,
        var countdown: Boolean = false,
        var actionUrl: String = "",
        var blurredImageUrl: String = "",
        var canSkip: Boolean = false,
        var version: String = "",
        var imageUrl: String = "",
        var displayCount: Int = 0,
        var startTime: Long = 0,
        var endTime: Long = 0,
        var adTrack: List<AdTrack> = listOf(),
        var newImageUrl: String = ""
    ) {

        data class AdTrack(
            var organization: String = "",
            var viewUrl: String = "",
            var clickUrl: String = "",
            var playUrl: String = "",
            var monitorPositions: String = ""
        )
    }


    data class AutoCache(
        var forceOff: Boolean = false,
        var videoNum: Int = 0,
        var version: String = "",
        var offset: Int = 0
    )


    data class StartPageVideoConfig(
        var list: List<Any> = listOf(),
        var version: String = ""
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


    data class ProfilePageAd(
        var imageUrl: String = "",
        var actionUrl: String = "",
        var startTime: Long = 0,
        var endTime: Long = 0,
        var version: String = "",
        var adTrack: List<Any> = listOf()
    )
}