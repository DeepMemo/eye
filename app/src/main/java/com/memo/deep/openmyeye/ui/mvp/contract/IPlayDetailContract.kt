package com.memo.deep.openmyeye.ui.mvp.contract

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

interface IPlayDetailContract {

    interface View {
        fun onNext(t: List<BaseMuti>)
        fun onNextMore(t: List<BaseMuti>)
        fun onError()
        fun onMoreEnd()
    }

    interface Presenter {
        fun getContent(id: Int)
        fun getPlayDetailVideo(id: Int)
    }
}