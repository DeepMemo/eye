package com.bkvito.beikeshequ.retrofit

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.memo.deep.openmyeye.`interface`.Constant
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * retrofit 的生成工厂
 */
object RetrofitFactory {

    /**
     * 默认参数gson解析和rxjava call
     */
    fun createInterface(
            t: Class<out RetrofitApi> = RetrofitApi::class.java,
            baseUrl: String = RetrofitApi.BASE_URL,
            converterFactory: GsonConverterFactory? = GsonConverterFactory.create(),
            // 默认都使用jakewharton包的
            callAdapterFactory: RxJava2CallAdapterFactory? = RxJava2CallAdapterFactory.create()
    ): RetrofitApi {

        val builder = Retrofit.Builder()
                .baseUrl(baseUrl)

        // 有些接口不需要统一处理，所以传入GsonConverterFactory，在onNext具体做判断
        if (converterFactory != null) {
            builder.addConverterFactory(converterFactory)
        } else {
            builder.addConverterFactory(GsonConverterFactory.create())
        }

        if (callAdapterFactory != null) {
            builder.addCallAdapterFactory(callAdapterFactory)
        }

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //打印retrofit日志
            Log.i("=-=", "retrofitBack = $message")
        })

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        var client = OkHttpClient.Builder()
                .cache(Cache(File(Constant.RETROFIT_CACHE), 1024 * 1024 * 100))
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()

        return builder
                .client(client)
                .build()
                .create(t)
    }

}