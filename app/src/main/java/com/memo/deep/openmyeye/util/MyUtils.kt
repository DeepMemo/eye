package com.memo.deep.openmyeye.util

object MyUtils {

    fun addFakeData(str: String): List<String> {
        val list = arrayListOf<String>()
        for (i in 1..10) {
            list.add(str + i)
        }
        return list
    }

    fun getMinute(duration: Int): String {
        val minute = duration / 60
        val second = duration % 60
        val str = "%d:%d"
        return String.format(str, minute, second)
    }
}