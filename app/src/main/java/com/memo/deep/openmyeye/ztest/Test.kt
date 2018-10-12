package com.memo.deep.openmyeye.ztest

class Test {
    fun addFakeData(str: String): List<String> {
        val list = arrayListOf<String>()
        for (i in 1..10) {
            list.add(str + i)
        }
        return list
    }

    fun time() {
    }
}