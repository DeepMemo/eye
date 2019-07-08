package com.memo.deep.openmyeye.util

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.blankj.utilcode.util.TimeUtils
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object MyUtils {

    fun addFakeData(str: String): List<String> {
        val list = arrayListOf<String>()
        for (i in 1..10) {
            list.add(str + i)
        }
        return list
    }

    fun getMinute(duration: Long): String {
        val minute = duration / 60
        val second = duration % 60
        val str = "%02d:%02d"
        return String.format(str, minute, second)
    }

    fun setRecycleMargin(parent: ViewGroup, itemView: View, margin: Int) {
        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        lp.width = parent.width - 2 * margin
        itemView.layoutParams = lp
    }

    /**
     * 动态设置margin
     */
    fun setMargins(v: View?, l: Int, t: Int, r: Int, b: Int) {
        if (v != null && v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }

    /**
     * 把string变成等量的空格
     */
    fun convertEmptyString(text: String): String {
        val sb = StringBuilder()
        for (c in text) {
            sb.append("  ")
        }
        return sb.toString()
    }

    /**
     * web传输的时候，有些特殊字符必须经过urlEncode编码
     */
    fun urlEncoded(paramString: String): String {
//        if (TextUtils.isEmpty(paramString)) {
//            return ""
//        }
        try {
            var str = String(paramString.toByteArray(), charset("UTF-8"))
            str = URLEncoder.encode(str, "UTF-8")
            return str
        } catch (e: UnsupportedEncodingException) {
            return ""
        }

    }

    /**
     * web传输的时候，有些特殊字符必须经过urlEncode编码
     */
    fun urlDecode(paramString: String): String {
//        if (TextUtils.isEmpty(paramString)) {
//            return ""
//        }
        try {
            var str = String(paramString.toByteArray(), charset("UTF-8"))
            str = URLDecoder.decode(str, "UTF-8")
            return str
        } catch (e: UnsupportedEncodingException) {
            return ""
        }

    }

    fun changeTime(time: Long): String {
        var timeString = ""
        // 今天之内
        if (TimeUtils.isToday(time)) {
            timeString = TimeUtils.millis2String(time, SimpleDateFormat("mm:ss", Locale.getDefault()))
        } else {
            timeString = TimeUtils.millis2String(time, SimpleDateFormat("yy/MM/dd", Locale.getDefault()))
        }

        return timeString
    }

    fun waitSecond(millSecond: Long = 1000, method: () -> Unit) {
        io.reactivex.Observable.just("")
                .delay(millSecond, TimeUnit.MILLISECONDS)
                .compose(RetrofitUtils.setThread())
                .subscribe {
                    method()
                }
    }

}