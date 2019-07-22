package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.library.flowlayout.FlowLayoutManager
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.ItemListBean.AllTypeBean
import com.memo.deep.openmyeye.ui.adapter.recycle.AllTypeAdapter
import com.memo.deep.openmyeye.ui.view.CustomItemDecoration
import kotlinx.android.synthetic.main.activity_all_category.*

class AllCategoryActivity : BaseActivity() {

    val list = mutableListOf<AllTypeBean.Item>()
    val adapter = AllTypeAdapter(list) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_category)
        initData()
        initView()
    }

    private fun initData() {
        RetrofitFactory.createInterface()
                .getAllCategory(Constant.URL_MAP)
                .compose(RetrofitUtils.setBase(getProvider()))
                .subscribe(object : BaseObserver<AllTypeBean>() {
                    override fun onNext(t: AllTypeBean) {
                        list.addAll(t.itemList)
                        adapter.notifyDataSetChanged()
                    }
                })
    }

    private fun initView() {
        rv.adapter = adapter
        rv.layoutManager = FlowLayoutManager()
        rv.addItemDecoration(CustomItemDecoration(1,0,1,0))

    }

}
