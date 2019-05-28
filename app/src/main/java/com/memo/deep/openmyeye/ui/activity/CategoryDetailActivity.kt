package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ConvertUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.adapter.viewpager.CardAdapter
import com.memo.deep.openmyeye.ui.fragment.second.DailyReportFragment
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment
import com.memo.deep.openmyeye.ui.fragment.second.RecommendFragment
import kotlinx.android.synthetic.main.activity_category_detal.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.android.synthetic.main.toolbar.*

class CategoryDetailActivity : AppCompatActivity() {

    companion object {
        // 整个图片的高度只有200dp,还有一个toolbar的高度，所以要减去56
        val moveLength = ConvertUtils.dp2px(200 - 56f)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detal)
        initView()
    }

    var currentY = 0
    private fun initView() {
        Log.i("===>", "moveLength=====-=>" + moveLength)
        // 解决位置自动滑动到RecycleView的bug，是为了确保mNoHorizontalScrollView他的子孙不能获得焦点
        ns.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
        status.alpha = 0f
        // 初始化tab
        val findFragment = FindFragment()
        val list = arrayListOf<Fragment>(
                findFragment,
                RecommendFragment(),
                DailyReportFragment()
        )
        val titleList = arrayOf("发现", "推荐", "日报")
        vp.adapter = CardAdapter(list, supportFragmentManager)
        stl.setViewPager(vp, titleList)
        stl_hold.setViewPager(vp, titleList)

        // 到底先滚动还是先监听？如果先滚动，点击的是在Recycle里，这里还没有设置打断，不应该是Rcycle滚动么
        // 如果先监听，感觉又不符合滚动监听，大概猜测因为第一次是父类获取的焦点，并没有传递给子View。
        // 监听scrollView,
        ns.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, x: Int, y: Int, oldX: Int, oldY: Int ->
            currentY = y
            Log.i("===>", "currentY=====-=>" + currentY)
            status.alpha = Math.min(y / moveLength.toFloat(), 1f)
            if (currentY >= moveLength) {
                stl_hold.visibility = View.VISIBLE
                // 如果移动到下面，不进行事件拦截，让Recycleview处理滑动
                ns.setIntercept(false)

            } else {
                // scrollView在顶部了，可以进行下拉刷新，不能打断事件分发，等于0 就不拦截事件
                ns.setIntercept(y != 0)
                if (currentY == 0) {
                    view.srl.setEnableRefresh(true)
                }
                stl_hold.visibility = View.INVISIBLE
            }
        }

        findFragment.onRecycle = fun(view: View, persent: Float) {
            this.view = view
            view.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    //onScrollStateChanged 方法
                    val layoutManager = recyclerView.getLayoutManager()
                    //判断是当前layoutManager是否为LinearLayoutManager
                    //只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    if (layoutManager is LinearLayoutManager) {
                        //获取最后一个可见view的位置
                        val lastItemPosition = layoutManager.findLastVisibleItemPosition()
                        //获取第一个可见view的位置
                        val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
                        if (firstItemPosition == 0) {
                            // 如果有偏移量，就不能刷新
                            if (currentY > 0) {
//                                view.srl.setEnableRefresh(false)
                            }
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                }
            })

        }

    }

    lateinit var view: View

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                // scrollView在顶部了，可以进行下拉刷新，不能打断事件分发，等于0 就不拦截事件
                if (currentY == 0) {
                    // 让其可刷新
//                    ns.setIntercept(true)

                }
//                if (ns.scrollY >= moveLength) {
//                    ns.setIntercept(false)
//                } else {
//                    ns.setIntercept(true)
//                }
//                if (currentY == 0) {
//                    view.srl.setEnableRefresh(true)
//                } else {
//                    view.srl.setEnableRefresh(true)
//                }

//                if (currentY >= moveLength) {
//                    // 如果移动到下面，不进行事件拦截，让Recycleview处理滑动
//                    ns.setIntercept(false)
//
//                } else {
//                    // scrollView在顶部了，可以进行下拉刷新，不能打断事件分发，等于0 就不拦截事件
//                    ns.setIntercept(y != 0)
//                    stl_hold.visibility = View.INVISIBLE
//                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}
