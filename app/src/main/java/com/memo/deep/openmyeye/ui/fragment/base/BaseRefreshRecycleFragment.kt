package com.memo.deep.openmyeye.ui.fragment.base

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * 包装了下拉和上拉加载的处理，继承此类，实现对应的方法即可完成一个Fragment的上拉下拉页面
 */
abstract class BaseRefreshRecycleFragment : BaseFragment() {
    var isRefreshEnable = true
    var isLazy = true
    var isLoadMore = true
    var isFirst = true
    val checked = 1
    var page = 1
    var rows = 10
    var emptyViewLayoutId = 0
    lateinit var srl: SmartRefreshLayout
    lateinit var rv: RecyclerView

//    override fun getLayoutId(): Int {
//        return R.layout.fragment_refresh_recycle
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        srl = view.findViewById(R.id.srl)
        rv = view.findViewById(R.id.rv)
        initSetting()
        initView()
        initData()
    }

    open fun initView() {
        initSrl()
        initRecycle()
        initAdapter()
        initListener()
    }

    /**
     * 设置对外扩展，是否懒加载，是否可刷新，是否可加载更多
     */
    abstract fun initSetting()

    var category: String = ""
    open fun initData() {
        category = arguments?.getString(Constant.DATA) ?: ""
        if (!isLazy) {
            callDiffRefresh()
        } else {
//            onFragmentVisibeListener(userVisibleHint)
        }
    }

    /**
     * 统一调用刷新方式
     */
    private fun callDiffRefresh() {
        if (isRefreshEnable) {
            srl.autoRefresh()
        } else {
            callRefresh()
        }
    }

    private fun initSrl() {
        // 默认300，减少动画，让用户感觉更快点
        srl.setReboundDuration(200)
        srl.setEnableRefresh(isRefreshEnable)
        if (isRefreshEnable) {
            srl.setOnRefreshListener {
                callRefresh()
            }
        }
    }

    /**
     * 抽取出来，刷新时使用，不能刷新的时候还能使用
     */
    fun callRefresh() {
        page = 1
        refresh({
            list.clear()
            list.addAll(it)
            handleQueryResult()
        }, {
            handleFailResult()
        })
    }

    val list = mutableListOf<MultiItemEntity>()
    lateinit var baseRefreshAdapter: BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>
    private fun initRecycle() {
        baseRefreshAdapter = getAdapter()
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = baseRefreshAdapter
    }


    /**
     * adapter对外扩展
     */
    abstract fun getAdapter(): BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>

    private fun initAdapter(preNum: Int = -1) {
    }

    /**
     * 点击事件对外扩展
     */
    abstract fun initListener()

    /**
     * 刷新接口对外扩展
     * @param onNext 在rxjava的onNext中调用
     * @param onError 在rxjava的onError中调用
     */
    abstract fun refresh(onNext: (t: List<MultiItemEntity>) -> Unit, onError: () -> Unit)

    /**
     * 加载更多对外扩展
     * @param onNext 在rxjava的onNext中调用
     * @param onError 在rxjava的onError中调用
     */
    abstract fun queryMore(onNext: (t: List<MultiItemEntity>) -> Unit, onError: () -> Unit)

    /**
     * 懒加载的回调
     */
//    override fun onFragmentVisibeListener(isVisible: Boolean) {
//        if (isVisible && isLazy) {
//            callDiffRefresh()
//            // 网络请求过一次，就不再是第一次了
//            isFirst = false
//        }
//    }

    /**
     * 处理空数据，处理刷新以后可以继续加载更多
     * 刷新一次以后，得到数据，再判断是否能加载更多以及空数据显示
     */
    open fun handleQueryResult() {
        srl.finishRefresh()
        if (list.size == rows) {
            setLoadMore()
        }
//        else if (list.isNullOrEmpty()) {
//            baseRefreshAdapter.emptyView = LayoutInflater
//                    .from(activity).inflate(emptyViewLayoutId, null)
//        }
        baseRefreshAdapter.notifyDataSetChanged()
    }

    /**
     * 失败的处理
     */
    open fun handleFailResult() {
        srl.finishRefresh()
        baseRefreshAdapter.emptyView = LayoutInflater
                .from(activity).inflate(emptyViewLayoutId, null)
    }

    /**
     * 设置是否可加载更多，以及加载更多的样式
     */
    private fun setLoadMore() {
        if (isLoadMore) {
            baseRefreshAdapter.setEnableLoadMore(true)
//            baseRefreshAdapter.setLoadMoreView(CustomLoadMoreWhiteView())
            // bug 登录以后推出登录，进行刷新操作，这里会触发加载更多，神奇的是，id居然保留着
            // 增加一个判断，未登录不能触发自动加载，解决bug
            baseRefreshAdapter.setOnLoadMoreListener({
                page++
                queryMore({
                    list.addAll(it)
//                    if (MyUtils.isListEmpty(it)) {
//                        baseRefreshAdapter.loadMoreEnd()
//                    } else {
                        baseRefreshAdapter.loadMoreComplete()
//                    }
                }, {
                    baseRefreshAdapter.loadMoreEnd()
                })
            }, rv)
        }
    }


}
