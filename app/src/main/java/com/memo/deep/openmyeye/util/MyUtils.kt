package com.memo.deep.openmyeye.util

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

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

}