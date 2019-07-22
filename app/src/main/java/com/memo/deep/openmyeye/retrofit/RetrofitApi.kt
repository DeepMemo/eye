package com.bkvito.beikeshequ.retrofit

import com.memo.deep.openmyeye.bean.ItemListBean.AllTypeBean
import com.memo.deep.openmyeye.bean.ItemListBean.NoticePushBean
import com.memo.deep.openmyeye.bean.ItemListBean.StartBean
import com.memo.deep.openmyeye.bean.itemBean.VideoBeanForClient
import com.memo.deep.openmyeye.bean.my.CategoryDeatilTypeBean
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

    // 获取分类
    @GET("/api/v5/category/list")
    fun getCategory(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    // 获取热门搜索
    @GET("/api/v3/queries/hot")
    fun getHotSearch(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    // 获取搜索结果
    @GET("/api/v3/search")
    fun getSearch(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    // 获取预搜索（输入一个字的时候）
    @GET("api/v3/search/preSearch")
    fun getPreSearch(@QueryMap map: Map<String, String>): Observable<Response<ResponseBody>>

    // 获取关注上面的tab数据
    @GET("api/v5/community/tab/list")
    fun getTabList(@QueryMap map: Map<String, String>): Observable<ResponseBody>

    // 获取首次进入背景图
    @GET("api/v2/configs")
    fun getStartPic(@QueryMap map: Map<String, String>): Observable<StartBean>

    // 获取推送消息
    @GET("api/v3/messages")
    fun getPush(@QueryMap map: Map<String, String>): Observable<NoticePushBean>

    // 获取具体分类的tab
    @GET("api/v4/categories/detail/tab")
    fun getCategoryTab(@QueryMap map: Map<String, String>): Observable<CategoryDeatilTypeBean>

    // 获取全部分类
    @GET("api/v4/categories/all")
    fun getAllCategory(@QueryMap map: Map<String, String>): Observable<AllTypeBean>

}
