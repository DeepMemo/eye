package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.bean.FindBean
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent

class FindPresenter(val view: IFindContract.View,
                    private val provider: LifecycleProvider<FragmentEvent>) {

    fun getFind() {
        RetrofitFactory.createInterface()
                .getFind()
                .compose(RetrofitUtils.setFragmentBase(provider))
                .subscribe(object : BaseObserver<FindBean>() {
                    override fun onNext(t: FindBean) {
                        view.onNext(t)
                    }
                })
    }
}