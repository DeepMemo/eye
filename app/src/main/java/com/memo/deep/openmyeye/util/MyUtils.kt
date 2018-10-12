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

}