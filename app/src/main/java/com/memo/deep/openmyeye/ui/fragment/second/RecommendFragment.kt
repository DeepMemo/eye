package com.memo.deep.openmyeye.ui.fragment.second

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_second.view.*

class RecommendFragment : SecondFragment<String>() {

    override fun initView(inflate: View) {
        inflate.rv.layoutManager = LinearLayoutManager(activity)
//        inflate.rv.adapter = DiscoveryAdapter(R.layout.item_find_text_card, MyUtils.addFakeData("推荐"))
    }
}