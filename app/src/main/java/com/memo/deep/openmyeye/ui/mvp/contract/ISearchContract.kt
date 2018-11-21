package com.memo.deep.openmyeye.ui.mvp.contract

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti

interface ISearchContract {

    interface View {
        fun onHotNext(transformArray: List<BaseMuti>)
        fun onPreNext(transformArray: List<BaseMuti>)
        fun onNext(transformArray: List<BaseMuti>)
    }

    interface Presenter {
        fun getHotSearch()
        fun getPreSearch(key: String)
        fun getSearch(key: String)
    }
}