package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
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

}