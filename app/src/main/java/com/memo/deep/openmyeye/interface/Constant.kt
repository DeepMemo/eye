package com.memo.deep.openmyeye.`interface`

import android.os.Environment

/**
 * 存放常量接口
 */
interface Constant {
    companion object {
        // 基础文件夹
        val BASE_PATH = Environment.getExternalStorageDirectory().absolutePath + "/eye/"
        val RETROFIT_CACHE = BASE_PATH + "recache/"
        val URL_MAP = mapOf(
                "udid" to "a754f397b32441feabdafd94c5833118cbd76441",
                "vc" to "403",
                "vn" to "4.5.1",
                "deviceModel" to "LLD-AL00",
                "first_channel" to "eyepetizer_zhihuiyun_market",
                "last_channel" to "eyepetizer_zhihuiyun_market",
                "system_version_code" to "26")
        val INTENT_DATA = "data"
        val INTENT_ID = "id"
        val INTENT_URL = "url"
    }

}