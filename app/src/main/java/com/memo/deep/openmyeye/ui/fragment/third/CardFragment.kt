package com.memo.deep.openmyeye.ui.fragment.third

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import kotlinx.android.synthetic.main.fragment_card.view.*

/**
 * viewPager 卡片的具体布局
 */
class CardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val url = arguments?.get("url") ?: ""
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_card, container, false)
        inflate.iv.setImageURI(url as String)
        return inflate
    }
}