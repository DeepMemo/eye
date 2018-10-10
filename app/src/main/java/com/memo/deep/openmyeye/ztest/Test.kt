package com.memo.deep.openmyeye.ztest

import com.blankj.utilcode.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

class Test {
    fun addFakeData(str: String): List<String> {
        val list = arrayListOf<String>()
        for (i in 1..10) {
            list.add(str + i)
        }
        return list
    }

    fun time(){
        val string = TimeUtils.millis2String(1537978200000,
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()))
        println(string)
    }
}