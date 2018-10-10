package com.memo.deep.openmyeye.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.blankj.utilcode.util.ConvertUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.fragment.first.FocusFragment
import com.memo.deep.openmyeye.ui.fragment.first.HomeFragment
import kotlinx.android.synthetic.main.item_find_square_card_collection.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

    private fun initView() {
        val focusFragment = FocusFragment()
        val homeFragment = HomeFragment()
        vp.adapter = CardAdapter(listOf(focusFragment, homeFragment), supportFragmentManager)
        vp.pageMargin = ConvertUtils.dp2px(5f)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("===>test", "=====-=>onRestart")

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("===>test", "=====-=>onNewIntent")
    }
}
