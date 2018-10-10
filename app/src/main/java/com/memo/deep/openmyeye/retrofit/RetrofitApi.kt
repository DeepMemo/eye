package com.bkvito.beikeshequ.retrofit

import com.memo.deep.openmyeye.bean.FindBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * retrofit 的访问地址,
 * baseurl为"http://baobab.kaiyanapp.com/api/"，
 * 此类是返回对应的子接口
 */
interface RetrofitApi {

    companion object {
        const val BASE_URL: String = "http://baobab.kaiyanapp.com/api/"
    }

    @GET("v5/index/tab/discovery?udid=a754f397b32441feabdafd94c5833118cbd76441&vc=403&vn=4.5.1&deviceModel=LLD-AL00&first_channel=eyepetizer_zhihuiyun_market&last_channel=eyepetizer_zhihuiyun_market&system_version_code=26")
    fun getFind(): Observable<FindBean>

}
