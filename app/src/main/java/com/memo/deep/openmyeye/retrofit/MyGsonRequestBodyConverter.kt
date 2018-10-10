package com.bkvito.beikeshequ.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.nio.charset.Charset

/**
 * 自定义的Requestbody的converter，
 * 供MyGsonFactory使用，为了统一处错误和异常
 * 感觉没任何变化啊，直接使用GsonRequestBodyConverter不就好了吗？？
 * 不好，GsonRequestBodyConverter是protected不能直接引用，所以要复写
 */
class MyGsonRequestBodyConverter<T>(val gson: Gson, val adapter: TypeAdapter<T>) : Converter<T, RequestBody> {

    companion object {
        var MEDIA_TYPE: MediaType = MediaType.parse("application/json;charset=UTF-8")!!
        var UTF_8: Charset = Charset.forName("UTF-8")
    }

    override fun convert(value: T): RequestBody {
        var buffer = Buffer()
        var writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString())
    }
}