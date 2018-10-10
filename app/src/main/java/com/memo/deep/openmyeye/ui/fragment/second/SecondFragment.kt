package com.memo.deep.openmyeye.ui.fragment.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.trello.navi2.component.support.NaviFragment


/**
 * 第二层次的通用fragment
 */
abstract class SecondFragment<T> : NaviFragment() {

    protected var list = ArrayList<T>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val inflate = inflater.inflate(R.layout.fragment_second, container, false)
        initView(inflate)
        return inflate
    }

    /**
     * 考虑使用不同的presenter和使用不同的adapter
     */
    abstract fun initView(inflate: View)


}
