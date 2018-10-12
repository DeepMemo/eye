package com.memo.deep.openmyeye.ui.fragment.first

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment
import com.memo.deep.openmyeye.ui.fragment.second.RecommendFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(
                R.layout.fragment_home, container, false)
        initView(inflate)
        return inflate
    }

    private fun initView(inflate: View) {
        val discoverFragment = FindFragment()
        val recommendFragment = RecommendFragment()
        // 初始化tab
        inflate.spl.setViewPager(inflate.vp,
                arrayOf("发现", "推荐"),
                activity,
                arrayListOf<Fragment>(discoverFragment, recommendFragment))
    }


}