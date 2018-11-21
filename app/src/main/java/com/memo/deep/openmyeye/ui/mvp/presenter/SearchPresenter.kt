package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.my.SearchMuti
import com.memo.deep.openmyeye.ui.mvp.contract.ISearchContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.TimeUnit

class SearchPresenter(val view: ISearchContract.View,
                      val provider: LifecycleProvider<ActivityEvent>) : ISearchContract.Presenter {

    var publish = PublishSubject.create<Map<String, String>>()

    init {
        // 200毫秒内有，新输入的词语使用新词语搜索
        publish.debounce(200, TimeUnit.MILLISECONDS)
                .switchMap { t: Map<String, String> ->
                    // 如果删除到最后一个字，取消预搜索
                    if (t.get("query") == "") {
                        return@switchMap Observable.empty<Response<ResponseBody>>()
                    }
                    return@switchMap RetrofitFactory.createInterface()
                            .getPreSearch(t)
                            // 必须添加这句，不然上一次retrofit请求丢弃以后，会抛出HTTP FAILED: java.io.InterruptedIOException: thread
                            // 这里为了保持Observable能继续运行，放回一个空的，从而导致不中断，
                            .onErrorResumeNext(Observable.empty())
                }
                .compose(RetrofitUtils.setBase(provider))
                .subscribe(object : Observer<Response<ResponseBody>?> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: Response<ResponseBody>) {
                        val json = t.body().string()
                        val transformArray = RetrofitUtils.transformArray(json, true) as List<SearchMuti>
                        val htmlColor = "<font color='#4687D7'>%s</font>"
                        val keyWithColor = String.format(htmlColor, key)
                        transformArray.forEach {
                            it.name = it.name.replace(key, keyWithColor)
                        }
                        view.onPreNext(transformArray)
                    }

                    // 可能出现中断线程的异常
                    override fun onError(e: Throwable) {
                    }
                })
    }

    override fun getHotSearch() {
        RetrofitFactory.createInterface()
                .getHotSearch(Constant.URL_MAP)
                .compose(RetrofitUtils.setBase(provider))
                .subscribe(object : BaseObserver<Response<ResponseBody>>() {
                    override fun onNext(t: Response<ResponseBody>) {
                        val json = t.body().string()
                        view.onHotNext(RetrofitUtils.transformArray(json))
                    }

                })
    }

    var key = ""
    override fun getSearch(key: String) {
        this.key = key
        val map = mutableMapOf("query" to key)
        map.putAll(Constant.URL_MAP)
        RetrofitFactory.createInterface()
                .getSearch(map)
                .compose(RetrofitUtils.setBase(provider))
                .subscribe(object : BaseObserver<Response<ResponseBody>>() {
                    override fun onNext(t: Response<ResponseBody>) {
                        val json = t.body().string()
                        view.onNext(RetrofitUtils.transformData(json))
                    }
                })
    }

    override fun getPreSearch(key: String) {
        this.key = key
        val map = mutableMapOf("query" to key)
        map.putAll(Constant.URL_MAP)
        publish.onNext(map)
        publish.distinct()
    }

}