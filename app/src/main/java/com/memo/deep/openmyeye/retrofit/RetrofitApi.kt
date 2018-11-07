package com.bkvito.beikeshequ.retrofit

import com.memo.deep.openmyeye.bean.itemBean.VideoBeanForClient
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * retrofit 的访问地址,
 * baseurl为"http://baobab.kaiyanapp.com/api/"，
 * 此类是返回对应的子接口
 */
interface RetrofitApi {

    companion object {
        const val BASE_URL: String = "http://baobab.kaiyanapp.com/"
    }

    /**
     * home页面共有的查询
     */
    @GET()
    fun getCommonContent(@Url url: String, @QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    @GET("api/v5/index/tab/discovery")
    fun getFindContent(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    @GET("api/v5/index/tab/discovery")
    fun getComment(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    // 获取播放页面的相关推荐
    @GET("api/v4/video/related")
    fun getPlayDetailContent(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    // 获取播放页面视频详情
    @GET("/api/v2/video/{id}")
    fun getPlayDetailVideo(@Path("id") id: String, @QueryMap map: Map<String, String>): Observable<VideoBeanForClient>

}
