package com.bkvito.beikeshequ.retrofit

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * retrofit 的访问地址,
 * baseurl为"http://baobab.kaiyanapp.com/api/"，
 * 此类是返回对应的子接口
 */
interface RetrofitApi {

    companion object {
        const val BASE_URL: String = "http://baobab.kaiyanapp.com/"
    }

    @GET("api/v5/index/tab/discovery")
    fun getFindContent(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    @GET("api/v5/index/tab/discovery")
    fun getComment(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    @GET("api/v4/video/related")
    fun getPlayDetailContent(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

}
