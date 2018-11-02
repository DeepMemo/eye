package com.memo.deep.openmyeye

import com.memo.deep.openmyeye.util.MyUtils
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun inTest() {
//        val list = com.memo.deep.openmyeye.ztest.Test().time()
        var str = "fafda"
//        http://baobab.kaiyanapp.com/api%5C%2Fv5%5C%2Findex%5C%2Ftab%5C%2FallRec
        println( MyUtils.urlDecode("api%5C%2Fv5%5C%2Findex%5C%2Ftab%5C%2FallRec"))

    }
}
