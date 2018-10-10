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
    }

}