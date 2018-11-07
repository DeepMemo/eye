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

    fun time() {
        val oneDay = 24 * 60 * 60 * 1000
        val millis2String = TimeUtils.millis2String(1541379600000 - oneDay, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()))
        print(millis2String)
    }
}