package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.bean.beanItem.FollowCard
import com.memo.deep.openmyeye.bean.beanItem.VideoCollectionWithBrief
import com.memo.deep.openmyeye.bean.beanItem.VideoSmallCard
import com.memo.deep.openmyeye.bean.my.PlayDetail
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent

class FindPresenter(val view: IFindContract.View,
                    private val provider: LifecycleProvider<FragmentEvent>) {
    // 评论的起始数据
    var start = 0

    /**
     * 内容
     */
    fun getFindContent() {
        RetrofitFactory
                .createInterface()
                .getFindContent(Constant.URL_MAP)
                .map {
                    val body = it.body().string()
                    return@map RetrofitUtils.transformData(body)
                }
                .compose(RetrofitUtils.setFragmentBase(provider))
                .subscribe(object : BaseObserver<List<BaseMuti>>() {
                    override fun onNext(t: List<BaseMuti>) {
                        view.onNext(t)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view.onError()
                    }
                })

        start = 0
    }

    /**
     * 获取更多评论
     */
    fun getMoreComment() {
        RetrofitFactory
                .createInterface()
                .getComment(getParamMap())
                .map {
                    val body = it.body().string()
                    return@map RetrofitUtils.transformData(body)
                }
                .compose(RetrofitUtils.setFragmentBase(provider))
                .subscribe(object : BaseObserver<List<BaseMuti>>() {
                    override fun onNext(t: List<BaseMuti>) {
                        if (t.isEmpty()) {
                            view.onMoreEnd()
                        } else {
                            view.onNextMore(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        view.onError()
                    }
                })
    }

    /**
     * 获取添加参数过后的map
     */
    private fun getParamMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["start"] = start.toString()
        map.put("num", 5.toString())
        map.putAll(Constant.URL_MAP)
        // 每次增加5个数据
        start += 5
        return map
    }

    public fun setFollowCardData(item: FollowCard): PlayDetail {
        val playDetail = PlayDetail()
        playDetail.id = item.data.header.id
        playDetail.playUrl = item.data.content.data.playUrl
        playDetail.coverUrl = item.data.content.data.cover.detail
        playDetail.bgUrl = item.data.content.data.cover.blurred
        playDetail.title = item.data.content.data.title
        playDetail.type = item.data.header.description
        playDetail.description = item.data.content.data.description
        playDetail.collectionCount = item.data.content.data.consumption.collectionCount
        playDetail.shareCount = item.data.content.data.consumption.shareCount
        playDetail.replyCount = item.data.content.data.consumption.replyCount
        playDetail.pic1 = item.data.content.data.tags.get(0).headerImage
        playDetail.pic2 = item.data.content.data.tags.get(1).headerImage
        playDetail.pic3 = item.data.content.data.tags.get(2).headerImage
        playDetail.name1 = item.data.content.data.tags.get(0).name
        playDetail.name2 = item.data.content.data.tags.get(1).name
        playDetail.name3 = item.data.content.data.tags.get(2).name
        playDetail.author = item.data.content.data.author.name
        playDetail.authorPicUrl = item.data.content.data.author.icon
        playDetail.authorType = item.data.content.data.category

        return playDetail
    }

    fun setVideoSmallCardData(item: VideoSmallCard): PlayDetail {
        val playDetail = PlayDetail()
        playDetail.id = item.data.id
        playDetail.playUrl = item.data.playUrl
        playDetail.coverUrl = item.data.cover.detail
        playDetail.bgUrl = item.data.cover.blurred
        playDetail.title = item.data.title
        playDetail.type = item.data.category
        playDetail.description = item.data.descriptionEditor
        playDetail.collectionCount = item.data.consumption.collectionCount
        playDetail.shareCount = item.data.consumption.shareCount
        playDetail.replyCount = item.data.consumption.replyCount
        playDetail.pic1 = item.data.tags.get(0).headerImage
        playDetail.pic2 = item.data.tags.get(1).headerImage
        playDetail.pic3 = item.data.tags.get(2).headerImage
        playDetail.name1 = item.data.tags.get(0).name
        playDetail.name2 = item.data.tags.get(1).name
        playDetail.name3 = item.data.tags.get(2).name
        playDetail.author = item.data.author.name
        playDetail.authorPicUrl = item.data.author.icon
        playDetail.authorType = item.data.category

        return playDetail
    }

    fun setVideoCollectionWithBriefData(item: VideoCollectionWithBrief.Data.Item): PlayDetail {
        val playDetail = PlayDetail()
        playDetail.id = item.data.id
        playDetail.playUrl = item.data.playUrl
        playDetail.coverUrl = item.data.cover.detail
        playDetail.bgUrl = item.data.cover.blurred
        playDetail.title = item.data.title
        playDetail.type = item.data.category
        playDetail.description = item.data.descriptionEditor
        playDetail.collectionCount = item.data.consumption.collectionCount
        playDetail.shareCount = item.data.consumption.shareCount
        playDetail.replyCount = item.data.consumption.replyCount
        playDetail.pic1 = item.data.tags.get(0).headerImage
        playDetail.pic2 = item.data.tags.get(1).headerImage
        playDetail.pic3 = item.data.tags.get(2).headerImage
        playDetail.name1 = item.data.tags.get(0).name
        playDetail.name2 = item.data.tags.get(1).name
        playDetail.name3 = item.data.tags.get(2).name
        playDetail.author = item.data.author.name
        playDetail.authorPicUrl = item.data.author.icon
        playDetail.authorType = item.data.category

        return playDetail
    }


}