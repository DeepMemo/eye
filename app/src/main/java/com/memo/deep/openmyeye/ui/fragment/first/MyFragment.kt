package com.memo.deep.openmyeye.ui.fragment.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.fragment.base.BaseFragment

class MyFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val inflate = LayoutInflater.from(activity).inflate(
                R.layout.fragment_my, container, false)
        return inflate
    }

}