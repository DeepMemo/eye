package com.bkvito.beikeshequ.retrofit

import com.google.gson.Gson
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.bean.beanItem.*
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


object RetrofitUtils {


    /**
     * 订阅发生，观察处理
     */
    fun <T> setThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun <T> setBase(provider: LifecycleProvider<ActivityEvent>): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
//                    .retryWhen(TokenRetry())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    // 默认的是在stop中取消订阅，然后出现了在弹出系统权限对话框，刷新头部不消失的问题
                    // 为了避免此类问题，就直接在销毁页面才取消订阅
                    .compose(provider.bindUntilEvent(ActivityEvent.DESTROY))

        }
    }

    /**
     * token过期的重试的方法+
     */
//    class TokenRetry : Function<Observable<Throwable>, ObservableSource<*>> {
//        override fun apply(t: Observable<Throwable>): ObservableSource<*> {
//            return t.flatMap(object : Function<Throwable, ObservableSource<*>> {
//                override fun apply(t: Throwable): ObservableSource<*> {
//                    if (t is TokenExpiredException) {
//                        // 这里神坑，因为默认使用的是MyGsonResponse，返回数据会再判断BeiKeApplication.test，还没修改值，
//                        // 结果false又抛出了异常所以不会进行onNext方法，不会触发重试操作，也不会进入doOnNext，所以要单独提出来，
//                        // 其实刷新token不会抛出token过期的异常的
//                        val observable = RetrofitFactory.createInterface(
//                                RetrofitApi::class.java, converterFactory = null)
//                                .searchCommunity("")
//                                .doOnNext {
//                                    // TODO  需要刷新对应的token
//                                }
//                        return observable
//                    }
//                    return Observable.error<Throwable>(t)
//                }
//            })
//        }
//    }


    fun <T> setFragmentBase(provider: LifecycleProvider<FragmentEvent>): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream
//                    .retryWhen(TokenRetry())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(provider.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
        }
    }

    /**
     * 把responseBody的json数据依次转变成对应的bean类
     */
    fun transformData(string: String): List<BaseMuti> {
        val list = ArrayList<BaseMuti>()
        val jsonObject = JSONObject(string)
        val array = jsonObject.getJSONArray("itemList")
        val gson = Gson()
        for (i in 0 until array.length()) {
            val json = array.get(i).toString()
            val type = JSONObject(json).getString("type")
            when (type) {
                "horizontalScrollCard" -> {
                    val element = gson.fromJson(json, HorizontalScrollCard::class.java)
                    list.add(element)
                }
                "textCard" -> {
                    val element = gson.fromJson(json, TextCard::class.java)
                    list.add(element)
                }
                "briefCard" -> {
                    val element = gson.fromJson(json, BriefCard::class.java)
                    list.add(element)
                }
                "followCard" -> {
                    val element = gson.fromJson(json, FollowCard::class.java)
                    list.add(element)
                }
                "videoSmallCard" -> {
                    val element = gson.fromJson(json, VideoSmallCard::class.java)
                    list.add(element)
                }
                "squareCardCollection" -> {
                    val element = gson.fromJson(json, SquareCardCollection::class.java)
                    list.add(element)
                }
                "videoCollectionWithBrief" -> {
                    val element = gson.fromJson(json, VideoCollectionWithBrief::class.java)
                    list.add(element)
                }
                "DynamicInfoCard" -> {
                    val element = gson.fromJson(json, DynamicInfoCard::class.java)
                    list.add(element)
                }
            }
        }
        return list
    }


}