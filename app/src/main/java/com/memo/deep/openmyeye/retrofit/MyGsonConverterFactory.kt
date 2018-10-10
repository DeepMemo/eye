package com.bkvito.beikeshequ.retrofit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MyGsonConverterFactory private constructor(val gson: Gson) : Converter.Factory() {

    companion object {
        fun create(): MyGsonConverterFactory {
            return create(Gson())
        }

        fun create(gson: Gson): MyGsonConverterFactory {
            return MyGsonConverterFactory(gson)
        }
    }

    /**
     * 这不是一个泛型方法
     */
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?
                                       , retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return MyGsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?
                                      , methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return MyGsonRequestBodyConverter(gson, adapter)
    }

}