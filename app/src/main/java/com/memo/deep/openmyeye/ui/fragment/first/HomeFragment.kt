package com.memo.deep.openmyeye.ui.fragment.first

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.fragment.second.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(
                R.layout.fragment_home, container, false)
        initView(inflate)
        return inflate
    }

    private fun initView(inflate: View) {
        // 初始化tab
        inflate.spl.setViewPager(
                inflate.vp,
                arrayOf("发现", "推荐", "日报", "社区", "广告", "生活", "动画", "搞笑",
                        "开胃", "创意", "运动", "音乐", "萌宠", "剧情", "科技", "旅行",
                        "记录", "游戏", "综艺", "时尚"),
                activity,
                arrayListOf<Fragment>(
                        FindFragment(),
                        RecommendFragment(),
                        DailyReportFragment(),
                        CommunityFragment(),
                        AdFragment(),
                        LiveFragment(),
                        CartoonFragment(),
                        FunnyFragment(),
                        AppetizingFragment(),
                        CreateFragment(),
                        SportsFragment(),
                        MusicFragment(),
                        PetFragment(),
                        PlotFragment(),
                        ITFragment(),
                        TravelFragment(),
                        RecordFragment(),
                        GameFragment(),
                        VarietyFragment(),
                        FashingFragment()
                ))
    }


}