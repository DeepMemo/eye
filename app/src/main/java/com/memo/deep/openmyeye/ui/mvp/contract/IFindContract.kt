package com.memo.deep.openmyeye.ui.mvp.contract

import com.memo.deep.openmyeye.bean.beanBase.BaseMuti

interface IFindContract {

    interface View {
        fun onNext(t: List<BaseMuti>)
        fun onNextMore(t: List<BaseMuti>)
        fun onError()
        fun onMoreEnd()
    }

    interface Presenter {

    }
}