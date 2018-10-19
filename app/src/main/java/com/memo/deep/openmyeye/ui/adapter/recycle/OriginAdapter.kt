package com.memo.deep.openmyeye.ui.adapter.recycle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.memo.deep.openmyeye.R
import kotlinx.android.synthetic.main.item_find_text_card.view.*

class OriginAdapter(var list: ArrayList<String>) : RecyclerView.Adapter<OriginAdapter.Holder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        val inflate = LayoutInflater.from(p0.context).inflate(R.layout.item_find_text_card, p0, false)
        return Holder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0.textView.text = list.get(p1)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.tv_header
    }
}