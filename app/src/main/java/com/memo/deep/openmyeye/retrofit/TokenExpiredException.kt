package com.bkvito.beikeshequ.retrofit

/**
 * 自定义token异常，处理刷新
 */
class TokenExpiredException(val code: Int?, message: String?) : RuntimeException(message)