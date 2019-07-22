package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ConvertUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.my.CategoryDeatilTypeBean
import com.memo.deep.openmyeye.ui.fragment.second.CommonFragment
import com.memo.deep.openmyeye.util.MyUtils
import kotlinx.android.synthetic.main.activity_category_detal.*

class CategoryDetailActivity : BaseActivity() {

    companion object {
        // 整个图片的高度只有200dp,还有一个toolbar的高度，所以要减去56
        val moveLength = ConvertUtils.dp2px(200 - 56f)
//        val urlList = mutableListOf<String>(
//                "http://baobab.kaiyanapp.com/api/v4/categories/detail/index",
//                "http://baobab.kaiyanapp.com/api/v4/categories/videoList",
//                "http://baobab.kaiyanapp.com/api/v4/categories/detail/pgcs",
//                "http://baobab.kaiyanapp.com/api/v4/categories/detail/playlist"
//        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detal)
        BarUtils.setStatusBarColor(this, resources.getColor(R.color.transparent))
        initData()
    }

    var id = ""
    private fun initData() {
        id = MyUtils.getIntentMap(intent)[Constant.INTENT_ID].orEmpty()
        val map = mutableMapOf<String, String>(
                "id" to id
        )
        RetrofitFactory.createInterface()
                .getCategoryTab(map)
                .compose(RetrofitUtils.setBase(getProvider()))
                .subscribe(object : BaseObserver<CategoryDeatilTypeBean>() {
                    override fun onNext(t: CategoryDeatilTypeBean) {
                        initView(t.tabInfo.tabList)
                        iv.setImageURI(t.categoryInfo.headerImage)
                        tv_type.text = t.categoryInfo.name
                        tv_description.text = t.categoryInfo.description
                    }
                })
    }

    fun initView(tabList: List<CategoryDeatilTypeBean.TabInfo.Tab>) {
        val array = Array<String>(tabList.size) {
            return@Array tabList[it].name
        }
        stl.setViewPager(
                vp,
                array,
                this,
                getFragment(tabList))
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getFragment(tabList: List<CategoryDeatilTypeBean.TabInfo.Tab>): ArrayList<Fragment> {
        var list = ArrayList<Fragment>()
        tabList.forEach {
            val commonFragment = CommonFragment()
            commonFragment.id = id
            commonFragment.url = it.apiUrl
            list.add(commonFragment)
        }
        return list
    }
}
