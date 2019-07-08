package com.bkvito.beikeshequ.retrofit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.*
import com.memo.deep.openmyeye.bean.my.SearchMuti
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
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

    fun <T> setFlowableBase(provider: LifecycleProvider<ActivityEvent>): FlowableTransformer<T, T> {
        return FlowableTransformer { upstream ->
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
    fun transformData(string: String?): List<BaseMuti> {
        val list = ArrayList<BaseMuti>()
        if (string ==null)
            return list
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
                // 大类下，还有其他小类，就是有其他的数据结构
                "squareCardCollection" -> {
                    val itemJson = JSONObject(json)
                            .getJSONObject("data")
                            .getJSONArray("itemList")
                            .get(0)
                            .toString()

                    val itemType = JSONObject(itemJson).getString("type")
                    if (itemType == "followCard") {
                        val type = object : TypeToken<SquareCardCollection<FollowCard>>() {}.type
                        val element = gson.fromJson<SquareCardCollection<FollowCard>>(json, type)
                        list.add(element)
                    } else if (itemType == "banner") {
                        val type = object : TypeToken<SquareCardCollection<Banner>>() {}.type
                        val element = gson.fromJson<SquareCardCollection<Banner>>(json, type)
                        // 之前默认的结构，只是单独提出了类
                        list.add(element)
                    }
                }
                "videoCollectionWithBrief" -> {
                    val element = gson.fromJson(json, VideoCollectionWithBrief::class.java)
                    list.add(element)
                }
                "DynamicInfoCard" -> {
                    val element = gson.fromJson(json, DynamicInfoCard::class.java)
                    list.add(element)
                }
                "VideoBeanForClient" -> {
                    val element = gson.fromJson(json, VideoBeanForClient::class.java)
                    list.add(element)
                }
                "autoPlayFollowCard" -> {
                    val element = gson.fromJson(json, AutoPlayFollowCard::class.java)
                    list.add(element)
                }
                "pictureFollowCard" -> {
                    val element = gson.fromJson(json, PictureFollowCard::class.java)
                    list.add(element)
                }
            }
        }
        return list
    }

    fun <T : BaseMuti> setType(gson: Gson, json: String): T {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson<T>(json, type)
    }

    fun transformArray(json: String, isColorMore: Boolean = false): List<BaseMuti> {
        val list = mutableListOf<SearchMuti>()
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            val name = jsonArray.get(i).toString()
            list.add(SearchMuti(name, isColorMore))
        }

        return list
    }


}