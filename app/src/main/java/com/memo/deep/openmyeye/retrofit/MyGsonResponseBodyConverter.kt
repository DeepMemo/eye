package com.bkvito.beikeshequ.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.memo.deep.openmyeye.bean.VoidBean
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset


/**
 *  把web报的错误码，归纳到onError里进行处理
 */
class MyGsonResponseBodyConverter<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {
    companion object {
        var UTF_8: Charset = Charset.forName("UTF-8")

    }

    override fun convert(value: ResponseBody): T {
        val response = value.string()
        // 这里解析出来的只有basebean的字段而已，
        // 解析成对应子类的字段是在retrofit的gsonFactory中解析的，根据的是retrofitApi中指定的类型
        val jsonBody = gson.fromJson(response, VoidBean::class.java)
        // 把web报的错误，归纳到onError里进行处理
        // TODO  猜测正确的是1
        if (jsonBody.errorCode != 1) {
            value.close()
            throw WebException(jsonBody.errorCode, jsonBody.errorMessage)
        }

        // 为了避免value.string()报错
        val mediaType = value.contentType()
        // 我是没明白，前后都是UTF-8,okhttp也是这么写的
        val charset = if (mediaType != null) mediaType.charset(UTF_8) else UTF_8
        val bis = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(bis, charset)
        val jsonReader = gson.newJsonReader(reader)

        value.use { value ->
            return adapter.read(jsonReader)
        }
    }
}