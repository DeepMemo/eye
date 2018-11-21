package com.memo.deep.openmyeye.ui.mvp.contract

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.ui.mvp.base.IBasePresenter
import com.memo.deep.openmyeye.ui.mvp.base.IBaseView

interface ICategoryContract {

    interface View : IBaseView {
        fun onNext(list: List<BaseMuti>)

    }

    interface Presenter : IBasePresenter {
        fun getCategory()
    }
}