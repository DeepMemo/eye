package com.memo.deep.openmyeye.ui.fragment.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.cache.FontCache
import com.memo.deep.openmyeye.ui.activity.SearchActivity
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.fragment.base.BaseFragment
import com.memo.deep.openmyeye.ui.fragment.second.CommunityFragment
import com.memo.deep.openmyeye.ui.fragment.second.NoticePushFragment
import kotlinx.android.synthetic.main.fragment_focus.*
import kotlinx.android.synthetic.main.toolbar.*

class NoticeFragment : BaseFragment() {

    lateinit var inflate: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inflate = LayoutInflater.from(activity).inflate(
                R.layout.fragment_notice, container, false)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(arrayOf("推送", "互动"))
        initData()
    }

    private fun initData() {
        // 不查询固定到两个tab
//        RetrofitFactory.createInterface()
//                .getTabList(Constant.URL_MAP)
//                .compose(RetrofitUtils.setFragmentBase(getProvider()))
//                .subscribe(object : BaseObserver<ResponseBody>() {
//                    override fun onNext(t: ResponseBody) {
//                        val tabBean = Gson().fromJson(t.string(), TabBean::class.java)
//                        val array = Array<String>(tabBean.tabInfo.tabList.size) {
//                            tabBean.tabInfo.tabList[it].name
//                        }
//                    }
//                })
    }

    private fun initView(array: Array<String>) {
        setToolbar("Notification", R.drawable.search_72px)
        val textView = inflate.findViewById<TextView>(R.id.tv_toolbar_title)
        tv_toolbar_title.typeface = FontCache.getTypeface("fonts/Lobster-1.4.otf", activity!!)
        inflate.findViewById<View>(R.id.gap).visibility = View.INVISIBLE
        inflate.findViewById<View>(R.id.iv_back).visibility = View.INVISIBLE
        textView.textSize = 22f
        vp.adapter = CardAdapter(arrayListOf(
                NoticePushFragment(),
                CommunityFragment()
        ), childFragmentManager)
        stl.setViewPager(vp, array)
    }

    override fun onMenuClick() {
        startAc(SearchActivity::class.java)
    }

}
