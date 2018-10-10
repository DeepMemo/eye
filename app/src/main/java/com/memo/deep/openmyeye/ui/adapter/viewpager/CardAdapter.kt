package com.memo.deep.openmyeye.ui.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 卡片viewpager公用的adapter
 */
class CardAdapter(
        val list: List<Fragment>,
        fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(p0: Int): Fragment {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

}