package com.memo.deep.openmyeye.ui.mvp.contract

import com.memo.deep.openmyeye.bean.FindBean

interface IFindContract {

    interface View {
        fun onNext(t: FindBean)
    }

    interface Presenter {

    }
}