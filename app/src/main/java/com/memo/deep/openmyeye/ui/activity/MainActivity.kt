package com.memo.deep.openmyeye.ui.activity.mvp

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.activity.TestActivity
import com.memo.deep.openmyeye.ui.fragment.first.FocusFragment
import com.memo.deep.openmyeye.ui.fragment.first.HomeFragment
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment

class MainActivity : AppCompatActivity() {
    //存储tab下标签的fragment
    private lateinit var listFragment: List<Fragment>
    // 下标签的索引
    private val home = 0
    private val focus = 1
    private val notification = 2
    private val my = 3
    // 当前显示的fragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initData() {
        val homeFragment = HomeFragment()
        val focusFragment = FocusFragment()
        val discoverFragment = FindFragment()
        val focusFragment3 = FocusFragment()
        listFragment = listOf(
                homeFragment, focusFragment,
                discoverFragment, focusFragment3)
        changeFragment(homeFragment, "home")
    }

    private fun initDiscoveryAdapter(view: View) {
//        val list = addFakeData("发现")
//        val adapter = FindAdapter(R.layout.item_find_text_card, list)
//        view.rv.adapter = adapter
//        adapter.notifyDataSetChanged()
    }

    private fun addFakeData(str: String): List<String> {
        val list = arrayListOf<String>()
        for (i in 1..10) {
            list.add(str + i)
        }
        return list
    }

    private fun initView() {

    }

    /**
     * 下面tab标签切换
     */
    private fun changeFragment(fragment: Fragment, tag: String) {
        val beginTransaction = supportFragmentManager
                .beginTransaction()
        // 这里解决一个bug，如果使用replace，切换回来，导致上面的tab不能正常使用，换了一个框架依旧如此
        // 猜测是replace是摧毁了view有关，所以使用hide来代替。
        if (currentFragment != null) {
            beginTransaction.hide(currentFragment!!)
        }
        val findFragmentByTag = supportFragmentManager.findFragmentByTag(tag)
        // 如果添加过就直接显示
        if (findFragmentByTag != null) {
            beginTransaction.show(findFragmentByTag)
        } else {
            beginTransaction
                    .add(R.id.ll_content, fragment, tag)
        }
        beginTransaction.commit()
        currentFragment = fragment
    }


    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_home -> {
                changeFragment(listFragment.get(home), "home")

            }
            R.id.tv_focus -> {
                changeFragment(listFragment.get(focus), "focus")
            }
            R.id.iv_add -> {
            }
            R.id.tv_notification -> {
                changeFragment(listFragment.get(notification), "notification")
            }
            R.id.tv_my -> {
//                changeFragment(listFragment.get(my), "my")
                startActivity(Intent(this, TestActivity::class.java))
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("===>main", "=====-=>onRestart")

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i("===>main", "=====-=>onNewIntent")
    }


}
