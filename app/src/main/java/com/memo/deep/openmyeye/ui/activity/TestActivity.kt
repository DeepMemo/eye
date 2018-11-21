package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.VideoCollectionWithBrief
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    private fun initView() {
        rv.layoutManager = LinearLayoutManager(this)
        val findAdapter = FindAdapter(list = listOf<BaseMuti>(VideoCollectionWithBrief()))
        rv.adapter = findAdapter
        findAdapter.setOnItemChildClickListener({ adapter, view, position ->
            ToastUtils.showShort(position.toString())
        })
    }


}
