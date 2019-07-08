package com.memo.deep.openmyeye.ui.activity.mvp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.activity.CategoryActivity
import com.memo.deep.openmyeye.ui.activity.CategoryDetailActivity
import com.memo.deep.openmyeye.ui.activity.SearchActivity
import com.memo.deep.openmyeye.ui.activity.TestActivity
import com.memo.deep.openmyeye.ui.fragment.first.FocusFragment
import com.memo.deep.openmyeye.ui.fragment.first.HomeFragment
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment
import com.memo.deep.openmyeye.util.MyUtils
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {
    //存储tab下标签的fragment
    private lateinit var listFragment: List<Fragment>
    // 下标签的索引
    private val home = 0
    private val focus = 1
    private val add = 2
    private val notification = 3
    private val my = 4
    // 当前显示的fragment
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        Log.i("===>", "=====-=>" + resources.displayMetrics.density)
    }

    private fun initData() {
        val homeFragment = HomeFragment()
        val focusFragment = FocusFragment()
        val discoverFragment = FindFragment()
        val focusFragment3 = FocusFragment()
        listFragment = listOf(
                homeFragment, focusFragment,
                discoverFragment, focusFragment3)
        changeFragment(homeFragment, home)
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

    override fun onResume() {
        super.onResume()
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, getString(R.string.srl_content_empty), 0, *permissions)
    }


    /**
     * 下面tab标签切换
     */
    private fun changeFragment(fragment: Fragment, tag: Int) {
        val beginTransaction = supportFragmentManager
                .beginTransaction()
        // 这里解决一个bug，如果使用replace，切换回来，导致上面的tab不能正常使用，换了一个框架依旧如此
        // 猜测是replace是摧毁了view有关，所以使用hide来代替。
        if (currentFragment != null) {
            beginTransaction.hide(currentFragment!!)
        }
        val findFragmentByTag = supportFragmentManager.findFragmentByTag(tag.toString())
        // 如果添加过就直接显示
        if (findFragmentByTag != null) {
            beginTransaction.show(findFragmentByTag)
        } else {
            beginTransaction
                    .add(R.id.ll_content, fragment, tag.toString())
        }
        beginTransaction.commit()
        currentFragment = fragment
        changeColor(tag)
    }


    fun onClick(v: View) {
        when (v.id) {
            R.id.ll_home -> {
                changeFragment(listFragment.get(home), home)
            }
            R.id.ll_focus -> {
                changeFragment(listFragment.get(focus), focus)
            }
            R.id.iv_add -> {
                startActivity(Intent(this, TestActivity::class.java))
            }
            R.id.ll_notification -> {
                changeFragment(listFragment.get(notification), notification)
            }
            R.id.ll_my -> {
//                changeFragment(listFragment.get(my), "my")
                startActivity(Intent(this, CategoryDetailActivity::class.java))
                changeColor(my)
            }
            // 这是fragment 的view
            R.id.iv_fun -> {
                startActivity(Intent(this, CategoryActivity::class.java))
            }
            // 这是fragment 的view
            R.id.ll_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                overridePendingTransition(R.anim.item_top_to_bottom, 0)
            }
        }
    }

    fun changeColor(currentIndex: Int) {
        val ivList = listOf(iv_home, iv_focus, iv_add, iv_notification, iv_my)
        //  因add没有text，所以故意多加了一个tv——focus
        val tvList = listOf(tv_home, tv_focus, tv_focus, tv_notification, tv_my)
        for (i in 0..4) {
            val imageView = ivList.get(i)
            val textView = tvList.get(i)
            if (currentIndex == i) {
                imageView.isSelected = true
                textView.isSelected = true
            } else {
                imageView.isSelected = false
                textView.isSelected = false
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

    // 是否是第二次点击退出
    private var is2BackPress = false

    override fun onBackPressed() {
        // 第一次点击为false，就设置为第二次退出状态了，
        // 第二次再点击就进入else，如果超时了，就重新为了false
        if (!is2BackPress) {
            is2BackPress = true
            ToastUtils.showShort("再点击一次退出")
            //  等待两秒
            MyUtils.waitSecond(2000) {
                is2BackPress = false
            }
        } else {
            super.onBackPressed()
        }
    }


}
