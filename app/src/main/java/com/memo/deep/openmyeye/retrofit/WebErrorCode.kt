package com.bkvito.beikeshequ.retrofit

/**
 * web 返回的默认错误
 */
interface WebErrorCode {
    // 这个伴生对象是属于类的
    companion object {

        const val ERROR_NO_INTERNET:Int = -1
        // 服务器默认成功为1
        const val HTTP_OK:Int = 1
    }
}