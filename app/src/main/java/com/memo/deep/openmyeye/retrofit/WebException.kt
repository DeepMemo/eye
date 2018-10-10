package com.bkvito.beikeshequ.retrofit

/**
 * 自定义异常，为处理异常都在retrofit的onError方法
 */
class WebException(val code: Int?, message: String?) : RuntimeException(message)