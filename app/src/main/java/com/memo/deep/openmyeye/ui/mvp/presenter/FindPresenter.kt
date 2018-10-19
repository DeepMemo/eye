package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import retrofit2.Response

class FindPresenter(val view: IFindContract.View,
                    private val provider: LifecycleProvider<FragmentEvent>) {


    /**
     * 内容和评论一起查询
     */
    fun getFind() {
        val observableContent = RetrofitFactory.createInterface().getFind()
        val observableComment = RetrofitFactory.createInterface().getComment()
        Observable.zip(observableContent, observableComment,
                object : BiFunction<Response<ResponseBody>, Response<ResponseBody>, List<BaseMuti>> {
                    override fun apply(t1: Response<ResponseBody>, t2: Response<ResponseBody>): List<BaseMuti> {
                        val body1 = t1.body().string()
                        val body2 = t2.body().string()
                        val list1 = RetrofitUtils.transformData(body1)
                        val list2 = RetrofitUtils.transformData(body2)
                        val list = mutableListOf<BaseMuti>()
                        list.addAll(list1)
                        list.addAll(list2)
                        return list
                    }

                })
                .compose(RetrofitUtils.setFragmentBase(provider))
                .subscribe(object : BaseObserver<List<BaseMuti>>() {
                    override fun onNext(t: List<BaseMuti>) {
                        view.onNext(t)
                    }
                })
    }
}