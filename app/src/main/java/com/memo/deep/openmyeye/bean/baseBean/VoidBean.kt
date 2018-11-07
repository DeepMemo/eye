package com.memo.deep.openmyeye.bean.baseBean


/**
 * 最外层的数据结构，暂时未使用
 */
data class VoidBean(
        var count: Int = 0,
        var total: Int = 0,
        var nextPageUrl: Any? = Any(),
        var adExist: Boolean = false,
        var errorCode: Int = 0,
        var errorMessage: String = ""
)