package com.memo.deep.openmyeye.ui.mvp.contract

import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.bean.beanItem.FollowCard
import com.memo.deep.openmyeye.bean.beanItem.VideoCollectionWithBrief
import com.memo.deep.openmyeye.bean.beanItem.VideoSmallCard
import com.memo.deep.openmyeye.bean.my.PlayDetail

interface IFindContract {

    interface View {
        fun onNext(t: List<BaseMuti>)
        fun onNextMore(t: List<BaseMuti>)
        fun onError()
        fun onMoreEnd()
    }

    interface Presenter {
        fun getCommonContent(path: String = "", map: Map<String, String>)
        fun getCommonMoreContent(path: String = "", map: Map<String, String>)
        fun getFindContent()
        fun getMoreComment()
        fun setFollowCardData(item: FollowCard): PlayDetail
        fun setVideoSmallCardData(item: VideoSmallCard): PlayDetail
        fun setVideoCollectionWithBriefData(item: VideoCollectionWithBrief.Data.Item): PlayDetail

    }
}