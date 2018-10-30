package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.ui.mvp.contract.IPlayDetailContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

class PlayDetailPresenter(val view: IPlayDetailContract.View,
                          private val provider: LifecycleProvider<ActivityEvent>) : IPlayDetailContract.Presenter {
    // 评论的起始数据
    var start = 0

    /**
     * 获取列表内容
     */
    override fun getContent(id: Int) {
        RetrofitFactory
                .createInterface()
                .getPlayDetailContent(getParamMap(id))
                .map {
                    val body = it.body().string()
                    return@map RetrofitUtils.transformData(body)
                }
                .compose(RetrofitUtils.setBase(provider))
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
                .getComment(getParamMap(0))
                .map {
                    val body = it.body().string()
                    return@map RetrofitUtils.transformData(body)
                }
                .compose(RetrofitUtils.setBase(provider))
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
    private fun getParamMap(id: Int): Map<String, String> {
        val mutableMap = mutableMapOf<String, String>()
        mutableMap.put("id", id.toString())
        mutableMap.putAll(Constant.URL_MAP)
        return mutableMap
    }

}