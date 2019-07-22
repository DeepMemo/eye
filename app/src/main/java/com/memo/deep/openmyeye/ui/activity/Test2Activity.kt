package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.my.TestBean
import com.memo.deep.openmyeye.ui.adapter.recycle.Test2Adapter
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment
import com.memo.deep.openmyeye.ui.fragment.second.RecommendFragment
import kotlinx.android.synthetic.main.activity_test2.*

/**
 *     author : deep
 *     time   : 2019/07/17
 *     desc   :
 *     version: 1.0
 */
class Test2Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        initView()
    }

    private fun initView() {
//        toolbar.title = "FloatingButtonAndSnackSimple"
//        setSupportActionBar(toolbar)
        val list = addFakeData()
        val test2Adapter = Test2Adapter(list)
//        rvToDoList.adapter = test2Adapter
//        rvToDoList.layoutManager = LinearLayoutManager(this)
//        rvToDoList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        test2Adapter.setOnItemClickListener { adapter, view, position ->
//            Snackbar.make(main_content, list.get(position).name, Snackbar.LENGTH_LONG)
//                    .setAction(R.string.srl_footer_nothing, null)
//                    .setActionTextColor(resources.getColor(R.color.colorAccent))
//                    .setDuration(3000)
//                    .show()
//
//        }

        stl.setViewPager(
                vp,
                arrayOf("发现", "推荐"),
                this,
                arrayListOf<Fragment>(
                        FindFragment(),
                        RecommendFragment()
                ))
    }



    fun addFakeData(): MutableList<TestBean> {
        val list = mutableListOf<TestBean>()
        for (i in 1..30) {
            list.add(TestBean(i.toString()))
        }
        return list
    }

}