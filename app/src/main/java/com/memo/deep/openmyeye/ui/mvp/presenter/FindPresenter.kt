package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable

class FindPresenter(val view: IFindContract.View,
                    private val provider: LifecycleProvider<FragmentEvent>) {


    fun getFind() {
        RetrofitFactory.createInterface()
                .getFind()
                .compose(RetrofitUtils.setFragmentBase(provider))
//                .doOnNext {
//                    val body = it.body().toString()
//                    val list = RetrofitUtils.transformData(body)
//                }
                // 变成另一种格式
                .flatMap {
                    val body = it.body().string()
                    val list = RetrofitUtils.transformData(body)
                    return@flatMap Observable.just(list)
                }
                .subscribe(object : BaseObserver<List<Any>>() {
                    override fun onNext(t: List<Any>) {
                        view.onNext(t)
                    }
                })
    }
}