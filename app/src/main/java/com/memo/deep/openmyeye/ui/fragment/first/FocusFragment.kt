package com.memo.deep.openmyeye.ui.fragment.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R

class FocusFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(
                R.layout.fragment_focus, container, false)
        return inflate
    }

}