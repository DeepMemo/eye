package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.ToastUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment
import com.memo.deep.openmyeye.ui.fragment.second.RecommendFragment
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    private fun initView() {
        val discoverFragment = FindFragment()
        val recommendFragment = RecommendFragment()
        // 初始化tab
        spl.setViewPager(vp,
                arrayOf("发现", "推荐"),
                this,
                arrayListOf<Fragment>(discoverFragment, recommendFragment))

        vp.setOnClickListener {
            ToastUtils.showShort(vp.currentItem)
        }
    }


}
