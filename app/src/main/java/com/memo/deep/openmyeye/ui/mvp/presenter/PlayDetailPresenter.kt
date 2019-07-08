package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.VideoBeanForClient
import com.memo.deep.openmyeye.ui.mvp.contract.IPlayDetailContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import retrofit2.Response

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
                    val body = it.body()?.string()
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
     * 获取video的详情
     */
    override fun getPlayDetailVideo(id: Int) {
        val playDetailContent = RetrofitFactory
                .createInterface()
                .getPlayDetailContent(getParamMap(id))
        val playDetailVideo = RetrofitFactory
                .createInterface()
                .getPlayDetailVideo(id.toString(), getParamMap())
        Observable.zip(playDetailVideo, playDetailContent,
                BiFunction<VideoBeanForClient, Response<ResponseBody>, List<BaseMuti>> { t1, t2 ->
                    val list = mutableListOf<BaseMuti>()
                    val body2 = t2.body()?.string()
                    val list2 = RetrofitUtils.transformData(body2)
                    list.add(t1)
                    list.addAll(list2)
                    return@BiFunction list
                })
                .compose(RetrofitUtils.setBase(provider))
                .subscribe(object : BaseObserver<List<BaseMuti>>() {
                    override fun onNext(t: List<BaseMuti>) {
                        view.onNextMore(t)
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
    private fun getParamMap(id: Int? = null): Map<String, String> {
        val mutableMap = mutableMapOf<String, String>()
        if (id != null) {
            mutableMap.put("id", id.toString())
        }
        mutableMap.putAll(Constant.URL_MAP)
        return mutableMap
    }

}